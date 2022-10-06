import java.net.*;
import java.io.*;
/*
This class generates a client that accepts host name and port number as
command line argument.
It sends message to the server and calculate average RTT/throughput.
 */
public class client{

	public static String read(BufferedReader in) throws IOException {
		String input = in.readLine();
		while (! in.readLine().endsWith("\n")){
			input += in.readLine();
		}
		return input;
	}

	public static void main(String [] args) throws IOException{
		// Check whether the command line argument is the right format.
		// Print out usage if it is not.
		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		// Initialize variables that defines message size, probe number and measurement type.
		Integer size;
		Integer prob_num;
		String measure_type;
		// Initialize the sum of rtts.
		long rtt_sum = 0;
		long tput_sum = 0;

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
			// Let user define basic parameters.
			System.out.println("Usage: <MEASUREMENT TYPE> <NUMBER OF PROBES> <MESSAGE SIZE> <SERVER DELAY>");
			String input = stdIn.readLine();
			String [] inputs = input.split(" ");

			// Send the CSP message to server.
			out.println("s " + input);

			// Receive message from server.
			if ((input = in.readLine()).startsWith("404")){
				System.out.println("Invalid set up message sent.");
				System.exit(1);
			}
			System.out.println("Receive: " + input);

			// Assign each value.
			measure_type = inputs[0];
			prob_num = Integer.parseInt(inputs[1]);
			size = Integer.parseInt(inputs[2]);

			// Initialize start time and end time variable that calculates RTT.
			long start;
			long end;
			for (int i = 1; i < prob_num+1; i++){
				// Generate a string with the size specified by user.
	 			String payload = new String(new char[size]);
				start = System.currentTimeMillis();
				// Send MP message to server.
				out.println("m " + i + " " + payload);
				System.out.println("Probe message " + i + " was sent.");
				input = in.readLine();
				System.out.println("Receive: " + input);
				end = System.currentTimeMillis();
				if (input.startsWith("404")){
					System.out.println("Invalid measurement message sent.");
					System.exit(1);
				};
				System.out.println("Echo message " + i + " was received.");
				// Add RTT together to calculate average RTT.
				rtt_sum += end - start;
				tput_sum += size/(end - start);
			}

			// Calculate the average RTT/throughput.
			System.out.print("Data transmission finishes, ");
			if (measure_type.equals("rtt")){
				System.out.println("average RTT: " + rtt_sum/prob_num +"ms.");
			}
			else if (measure_type.equals("tput")){
				System.out.println("average throughput: " + tput_sum/prob_num*0.008 +"Mbps");
			}

			// Send CTP message to server.
			out.println("t");
			if ((input = in.readLine()).startsWith("404")){
				System.out.println( "404 ERROR: Invalid Connection Termination Message");
				System.exit(1);
			}
			System.out.println("Receive: " + input);


		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}
