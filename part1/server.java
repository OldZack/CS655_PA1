import java.net.*;
import java.io.*;
/*
This class generates an echo server that accepts port number as command line argument.
It sets up connection with clients and process client sockets to multiple threads.
 */
public class server{

	public static void main(String [] args) throws IOException{
		// Check whether the command line argument is the right format.
		// Print out usage if it is not.
		if (args.length != 1){
			System.err.println("Usage: java server <port number>");
			System.exit(1);
		}

		// Assign the port number.
		int port = Integer.parseInt(args[0]);
		// Generate the server socket.
		ServerSocket serverSocket = new ServerSocket(port);
		// Repeatedly listen to cilent connection.
		while (true) {
				// Generate the client socket once a client is connected.
				Socket socket = serverSocket.accept();
				// Deliver the client socket to a new thread.
				new Multithreading(socket, port).run();
		}

	}
}
