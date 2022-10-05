import java.net.*;
import java.io.*;

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
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput = stdIn.readLine();
			// Send out the message user inputs to the server.
			out.println(userInput);
			// Print out the message got backed from the server.
			System.out.println("Echo message: " + in.readLine());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}
