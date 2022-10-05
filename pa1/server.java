import java.net.*;
import java.io.*;

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

		// Using infinite loops to make server repeatdly check connections from clients.
		while (true) {
			try (
					// Generate the server socket.
					ServerSocket serverSocket = new ServerSocket(port);
					// Generate the client socket once a client is connected.
					Socket socket = serverSocket.accept();
					// Generate reader and writer of the client socket.
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			) {
				// Send out the same message received from the client.
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					out.println(inputLine);
				}
			} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port "
						+ port + " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}

	}
}
