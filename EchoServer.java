import java.net.*;
import java.io.*;
public class EchoServer extends Thread{
    private DatagramSocket socket; // attributo che specifica l'indirizzo della socket
    private boolean running;	  // attributo che specifica lo stato del process
    private byte[] buf = new byte[256	];	//array di byte per unire i messaggi

    public EchoServer() throws SocketException{	// costruttore, stabilisce la porta di comunicazione
        socket = new DatagramSocket(4445);
    }

    /*metodo che aspetta l'arrivo di un messaggio e lo rimanda indietro
    alla variabile running viene assegnato true fino a quando non
    non arriva un errore o il messaggio "end" dal client*/
	
    public void run() {
        running = true; 
        while (running) {
        	try {
	            DatagramPacket packet = new DatagramPacket(buf, buf.length); // variabile istanziata per ricevere i messaggi
	            socket.receive(packet); //ricezione del messaggio
	            InetAddress address = packet.getAddress(); 	// variabile che memorizza l'ip del client
	            int port = packet.getPort(); 				// numero porta del client
	            packet = new DatagramPacket(buf, buf.length, address, port);	//messaggio di risposta
	            String received = new String(packet.getData(), 0, packet.getLength());	//Stringa uguale a quella ricevuta
	            if (received.equals("end")) {
	                running = false;
	                continue;
	            }
	            socket.send(packet);
	        }
        	catch(IOException ecc) {
        		System.out.print(ecc);
        	}
	        socket.close();
        }
    }
    public static void main(String[] args) {
    	try {
    		EchoServer eco = new EchoServer();
    		eco.run();
    	}
    	catch(SocketException ecc) {
    		System.out.print(ecc);	
    	}
    	catch(IOException ecc) {
    		System.out.print(ecc);
    	}
    }
}
