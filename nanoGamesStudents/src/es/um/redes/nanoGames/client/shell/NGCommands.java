package es.um.redes.nanoGames.client.shell;

public class NGCommands {
	/**
	 * Codes for all commands supported by this shell.
	 */
	public static final byte COM_INVALID = 0;
	public static final byte COM_ROOMLIST = 1;
	public static final byte COM_ENTER = 2;
	public static final byte COM_NICK = 3;
	public static final byte COM_ANSWER = 4;
	public static final byte COM_EXIT = 5;
	public static final byte COM_RULES = 7;
	public static final byte COM_STATUS = 8;
	public static final byte COM_QUIT = 9;
	public static final byte COM_HELP = 10;
	public static final byte COM_TOKEN = 101;
	public static final byte COM_SOCKET_IN = 102;
	
	/**
	 * The list of valid commands that can be entered
	 * by the user of this shell, with their corresponding
	 * string and help message. Note the matching order!
	 */
	private static final Byte[] _valid_user_commands = { 
		COM_ROOMLIST, 
		COM_ENTER,
		COM_NICK,
		COM_ANSWER,
		COM_EXIT, 
		COM_RULES,
		COM_STATUS,
		COM_QUIT,
		COM_HELP
		};

	/**
	 * Accepted string for each command
	 */
	private static final String[] _valid_user_commands_str = {
		"roomlist",
		"enter",
		"nick",
		"a",
		"exit",
		"rules",
		"status",
		"quit",
		"help" };

	/**
	 * Help message for each command
	 */
	private static final String[] _valid_user_commands_help = {
		"provides a list of available rooms to play",
		"enter a particular <room>",
		"to set the <nickname> in the game",
		"<answer> to the challenge",
		"to leave the current room", 
		"gives information about the rules of the game",
		"shows the status of the game",
		"to quit the application",
		"shows this information"};

	/**
	 * Translates a string to its corresponding command.
	 * @param str The string entered by the user of this shell.
	 * @return The corresponding command code (COM_xxx), or
	 *         COM_INVALID if not a valid keyword
	 *         any valid user command in _valid_user_commands_str.
	 */
	public static byte stringToCommand(String comStr) {
		for (int i = 0;
		i < _valid_user_commands_str.length; i++) {
			if (_valid_user_commands_str[i].equalsIgnoreCase(comStr)) {
				return _valid_user_commands[i];
			}
		}
		return COM_INVALID;
	}

	/**
	 * Prints the list of valid commands and brief help message for each command.
	 */
	public static void printCommandsHelp() {
		System.out.println("List of commands:");
		for (int i = 0; i < _valid_user_commands_str.length; i++) {
			System.out.println(_valid_user_commands_str[i] + " -- "
					+ _valid_user_commands_help[i]);
		}		
	}
}	

