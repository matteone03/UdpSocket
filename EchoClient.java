import java.net.*;
import java.io.*; 

public class EchoClient {
	private DatagramSocket socket; 
	private byte[] buf = new byte[256];	//array di byte per unire i messaggi
	
	 public EchoClient() throws SocketException{	// costruttore, stabilisce la porta di comunicazione
	        socket = new DatagramSocket(4445);
	    }
	 
	 public String SendAndReceive(String request, String host, int port) throws UnknownHostException, IOException, SocketTimeoutException{
		 //metodo che serve per la richiesta e la ricezione del messaggio effettuata dal client verso il server
		 String answer;
		 DatagramPacket datagram; //datagram da creare per effettuare la richiesta
		 
		InetAddress address = InetAddress.getByName(host); //funzione che serve per sapere l'indirizzo IP del destinatario
		//verifica corretta chiusura della socket 
		if(socket.isClosed()) {
			throw new IOException();
		}
		
		//trasformare l'array in byte
		buf = request.getBytes("UTF-8"); //UTF-8 è un formato di trasformazione a 8 bit, ma non accetta i caratteri con l'accento
		
		//creare il datagram di richiesta al server
		datagram = new DatagramPacket (buf, buf.length, address, port);
		socket.send(datagram); // richiesta del datagram
		
		//attesa richiesta messaggio
		
		socket.receive(datagram); //il datagram è stato ricevuto
		
		//verificare l'indirizzo e la porta del datagram di risposta 
		if (datagram.getAddress().equals(address) && datagram.getPort() == 4445) {
			answer = new String (datagram.getData(), 0, datagram.getLength(), "ISO-8859-1"); //ISO-8859-1 è un formaqto di trasformazione e accetta i caratteri con l'accento
		}
		else {
			throw new SocketTimeoutException(); //la richiesta non è andata a buon fine, è passato troppo tempo
		}
		return answer; 
	 }
	 
	 public static void main(String args[]) {
		 
	 }
	 }