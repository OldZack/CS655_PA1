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

		// Initialize variables that defines message size, probe number, measurement type, server delay
		// and counter for probe sequence.
		Integer size;
		Integer prob_num;
		String measure_type;

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
			System.out.println("Usage: <MEASUREMENT TYPE> <NUMBER OF PROBES> <MESSAGE SIZE> <SERVER DELAY>");
			String userInput = stdIn.readLine();
			String [] inputs = userInput.split(" ");
			if (inputs.length == 4) {
				measure_type = inputs[0];
				prob_num = Integer.parseInt(inputs[1]);
				size = Integer.parseInt(inputs[2]);
			}
			else{
				throw new Exception();
			}
			out.println("s " + userInput);

			if (in.readLine() != "200 OK: Ready"){

			}
			for (Integer i = 1; i < prob_num+1; i++){
				String payload = new String(new char[size]);
				out.println("m " + i + " " + payload);
			}


			out.println();
			System.out.println("Echo message: " + in.readLine());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		} catch (Exception e) {
			System.err.println("Wrong input format");
			System.exit(1);
		}
	}
}
