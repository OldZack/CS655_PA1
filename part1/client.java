import java.net.*;
import java.io.*;
/*
This class generates a client that accepts host name and port number as
command line argument.
It gets connected to an echo server's prot, sending message and printing message
it receives.
 */
public class client{

	public static void main(String [] args) throws IOException{

		// Check whether the command line argument is the right format.
		// Print out usage if it is not.
		if (args.length != 2) {
			System.err.println(
					"Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		// Assign host name and port number.
		String hostName = args[0];
		int port = Integer.parseInt(args[1]);

		try (
				// Generate the socket.
				Socket s = new Socket(hostName, port);
				PrintWriter out = new PrintWriter(s.getOutputStream(), true);
				// Generate reader and writer of socket.
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				// Generate a reader of the user input.
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			System.out.println("Please put in your message: ")
			String userInput = stdIn.readLine();
			// Send out the message user inputs to the server.
			out.println(userInput);
			// Print out the message get backed from the server.
			System.out.println("Message returned: " + in.readLine());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}
