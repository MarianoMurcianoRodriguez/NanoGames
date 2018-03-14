package es.um.redes.nanoGames.broker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Cliente SNMP sin dependencias con otras clases y con funciones de consulta
 * especificas. En la actual version si lo soporta una funcion de consulta sobre
 * el UPTIME del host.
 */
public class BrokerClient {
	 private static final int PACKET_MAX_SIZE = 484;
	    private static final int DEFAULT_PORT = 161;
	    private static final String OID_UPTIME = "1.3.6.1.2.1.1.3.0";
	 
	    private DatagramSocket socket; // socket UDP
	    private InetSocketAddress agentAddress; // direcciodel agente SNMP
	    private long token;
	 
	    /**
	     * Constructor usando parametros por defecto
	     * @throws IOException 
	     */
	    //servername -> localhost
	    public BrokerClient(String agentName) {
	        //Registrar dirección del servidor
	        //Crear socket de cliente
	        try {
	            try {
	                this.agentAddress = new InetSocketAddress(InetAddress.getByName(agentName), DEFAULT_PORT);
	            } catch (UnknownHostException e) {
	                System.out.println("No se ha reconocido el host");
	            }                                                                                                                            //la direccion del server (nombre,puerto)
	            socket = new DatagramSocket();                                                                                                //creo el socket para enviar-transmitir
	        } catch (SocketException e) {
	            System.out.println("No se ha podido crear el socket.");
	        }
	         
	    }
	 
	    private byte[] buildRequest() throws IOException {
	        // mensaje GetRequest
	        ByteArrayOutputStream request = new ByteArrayOutputStream();
	        request.write(new byte[] { 0x30, 0x26 }); // Message (SEQUENCE)
	        request.write(new byte[] { 0x02, 0x01, 0x00 }); // Version
	        request.write(new byte[] { 0x04, 0x06 }); // Community
	        request.write("public".getBytes());
	        request.write(new byte[] { (byte) 0xa0, 0x19 }); // GetRequest
	        request.write(new byte[] { (byte) 0x02, 0x01, 0x00 }); // RequestId
	        request.write(new byte[] { (byte) 0x02, 0x01, 0x00 }); // ErrorStatus
	        request.write(new byte[] { (byte) 0x02, 0x01, 0x00 }); // ErrorIndex
	        request.write(new byte[] { (byte) 0x30, 0x0e }); // Bindings (SEQUENCE)
	        request.write(new byte[] { (byte) 0x30, 0x0c }); // Bindings Child (SEQUENCE)
	        request.write(new byte[] { (byte) 0x06 }); // OID
	        byte[] oidArray = encodeOID(OID_UPTIME);
	        request.write((byte) oidArray.length);
	        request.write(oidArray);
	        request.write(new byte[] { (byte) 0x05, 0x00 }); // Value (NULL)
	 
	        return request.toByteArray();
	 
	    }
	     
	    private long getTimeTicks(byte[] data) {
	        ByteArrayInputStream response = new ByteArrayInputStream(data);
	 
	        // recuperamos timeTicks a partir de la respuesta
	        int ch;
	        while ((ch = response.read()) != -1) {
	            if (ch == 0x43) { // TimeTicks
	                int len = response.read();
	                byte[] value = new byte[len];
	                response.read(value, 0, len);
	                return new BigInteger(value).longValue();
	            }
	        }
	        return 0;
	    }
	 
	    /**
	     * Env�a un solicitud GET al agente para el objeto UPTIME
	     * 
	     * @return long
	     * @throws IOException
	     */
	    public long getToken() throws IOException {
	 
	        //Construir solicitud
	        //Enviar solicitud
	        //Recibir respuesta
	        //Extraer TimeTicks (Token)
	        //Devolver token
	        byte[] paqueteEnvio = new byte[PACKET_MAX_SIZE];                                                                                        //creo el paquete de tamaño maximo
	        paqueteEnvio = buildRequest();                                                                                                            //me construye el paquete a enviar
	        DatagramPacket dpEnvio = new DatagramPacket(paqueteEnvio, paqueteEnvio.length, this.agentAddress);                                        //paquete -> paquete envio vacio, tam, direccion_dest
	        this.socket.send(dpEnvio);                                                                                                                    //transmito el mensaje
	        this.socket.setSoTimeout(1000);                                                                                                                //timeout de 1 segundo
	        byte[] paqueteRecepcion = new byte[PACKET_MAX_SIZE];                                                                                    //creo el buffer del paquete recepcion
	        DatagramPacket dpRecepcion = new DatagramPacket(paqueteRecepcion, paqueteRecepcion.length);                                                //paquete -> paquete recepcion vacio
	        this.socket.receive(dpRecepcion);                                                                                                            //tengo el paquete
	        return getTimeTicks(dpRecepcion.getData());
	         
	    }
	 
	    /**
	     * Codifica un OID según la especifacion SNMP Nota: solo soporta OIDs con
	     * números de uno o dos dígitos
	     * 
	     * @param oid
	     * @return
	     */
	    private byte[] encodeOID(String oid) {
	        // parsea OID
	        String digits[] = oid.split("\\.");
	        byte[] value = new byte[digits.length];
	        for (int i = 0; i < digits.length; i++)
	            value[i] = (byte) Byte.parseByte(digits[i]);
	 
	        // codifica OID
	        byte[] ret = new byte[value.length - 1];
	        byte x = value[0];
	        byte y = value.length <= 1 ? 0 : value[1];
	        for (int i = 1; i < value.length; i++) {
	            ret[i - 1] = (byte) ((i != 1) ? value[i] : x * 40 + y);
	        }
	        return ret;
	    }
	 
	    public void close() {
	        socket.close();
	    }
	     
	     
	     
	    public static void main(String[] args) {
	        BrokerClient bc = new BrokerClient("localhost");
	        try {
	            long prueba = bc.getToken();
	            System.out.println("TOken: "+prueba);
	        } catch (IOException e) {
	            System.out.println("Token no encontrado");
	        }
	         
	    }
	}

