package es.um.redes.nanoGames.client.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import es.um.redes.nanoGames.client.comm.NGGameClient;

public class NGShell {
	/**
	 * Scanner used to get user's commands from standard input.
	 */
	private Scanner reader;

	byte command = NGCommands.COM_INVALID;
	String[] commandArgs = new String[0];

	public NGShell() {
		reader = new Scanner(System.in); // Reading from System.in

		System.out.println("NanoGames shell");
		System.out.println("For help, type 'help'");
	}

	//Returns the current command entered by the user
	public byte getCommand() {
		return command;
	}

	//Returns the parameters provided by the user for the current command
	public String[] getCommandArguments() {
		return commandArgs;	
	}

	//Waits for a valid command among the general commands (not in a room)
	public void readGeneralCommand() {
		boolean validArgs;
		do {
			commandArgs = readGeneralCommandFromStdIn();
			//in case there are parameters, we validate them
			validArgs = validateCommandArguments(commandArgs);
		} while(!validArgs);
	}

	//Uses the standard input to obtain the command typed by the user
	private String[] readGeneralCommandFromStdIn() {
		String[] args = new String[0];
		Vector<String> vargs = new Vector<String>();
		while (true) {
			System.out.print("(nanoGames) ");
			//we obtain the lined typed by the user
			String input = reader.nextLine();
			StringTokenizer st = new StringTokenizer(input);
			//if there is no command then we start again the loop
			if (st.hasMoreTokens() == false) {
				continue;
			}
			//we translate the string provided by the user to a byte representing the command
			command = NGCommands.stringToCommand(st.nextToken());
			//Depending on the command...
			switch (command) {
			case NGCommands.COM_INVALID:
				//The command provided by the user is not valid
				System.out.println("Invalid command");
				continue;
			case NGCommands.COM_HELP:
				//We display help
				NGCommands.printCommandsHelp();
				continue;
			case NGCommands.COM_QUIT:
			case NGCommands.COM_ROOMLIST:
				//These commands are valid and they do not have parameters (if provided are ignored)
				break;
			case NGCommands.COM_ENTER:
			case NGCommands.COM_NICK:
				//These commands require one parameter
				while (st.hasMoreTokens()) {
					vargs.add(st.nextToken());
				}
				break;
			default:
				System.out.println("That command is only valid if you are in a room");;
			}
			break;
		}
		return vargs.toArray(args);
	}

	//Waits for a valid command among the game commands (in a room)
	public void readGameCommand(NGGameClient ngclient) {
		boolean validArgs;
		do {
			commandArgs = readGameCommandFromStdIn(ngclient);
			//in case there are parameters, we validate them
			validArgs = validateCommandArguments(commandArgs);
		} while(!validArgs);
	}

	//Uses the standard input to obtain the command typed by the user and checks if there is data available in the socket
	private String[] readGameCommandFromStdIn(NGGameClient ngclient) {
		String[] args = new String[0];
		Vector<String> vargs = new Vector<String>();
		while (true) {
			System.out.print("(nanoGames-room) ");
			BufferedReader standardInput = new BufferedReader(new InputStreamReader(System.in));
			boolean blocked = true;
			String input ="";
			//this loop runs until the user provides a command or the socket has incoming data
			while (blocked) {
				try {
					if (ngclient.isDataAvailable()) {
						//if the socket has data then we set the current command as SOCKET_IN and return
						System.out.println("* Message received from server...");
						command = NGCommands.COM_SOCKET_IN;
						return null;
					}
					else 		
					if (standardInput.ready()) {
						//if the user type one line the we read the line and exit the loop
						input = standardInput.readLine();			
						blocked = false;
					}
					//This sleep is to avoid a CPU-consuming busy wait
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (IOException | InterruptedException e) {
					command = NGCommands.COM_INVALID;
					return null;
				}				
			}
			//In case the user typed a command, we check it as we did previously in readGeneralCommandFromStdIn()
			StringTokenizer st = new StringTokenizer(input);
			if (st.hasMoreTokens() == false) {
				continue;
			}
			command = NGCommands.stringToCommand(st.nextToken());
			switch (command) {
			case NGCommands.COM_INVALID:
				System.out.println("Invalid command ("+input+")");
				continue;
			case NGCommands.COM_HELP:
				NGCommands.printCommandsHelp();
				continue;
			case NGCommands.COM_RULES:
				break;
			case NGCommands.COM_EXIT:
			case NGCommands.COM_STATUS:
				break;
			case NGCommands.COM_ANSWER:
				while (st.hasMoreTokens()) {
					vargs.add(st.nextToken());
				}
				break;
			default:
				System.out.println("That command is only valid if you are not in a room");;
			}
			break;
		}
		return vargs.toArray(args);
	}


	//There are some commands that require a parameter
	//This method checks if the parameter was provided for those commands
	private boolean validateCommandArguments(String[] args) {
		switch(this.command) {
		//enter requires parameter <room> and it must be one-character long
		case NGCommands.COM_ENTER:
			if (args.length == 0 || args.length > 1) {
				System.out
						.println("Correct use: enter <room>");
				return false;
			}
			if (args[0].length() > 1) {
				System.out.println("The room must be only one-character long");
				return false;
			}
			break;
		//nick requires parameter <nickname>
		case NGCommands.COM_NICK:
			if (args.length == 0 || args.length > 1) {
				System.out
						.println("Correct use: nick <nickname>");
				return false;
			}
			break;
		//answer requires parameter <answer>
		case NGCommands.COM_ANSWER:
			if (args.length == 0) {
				System.out
						.println("Correct use: a <answer>");
				return false;
			}
			break;
		default:
		}
		//The rest of commands do not require a parameter
		return true;
	}
}
