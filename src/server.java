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

		try (
				ServerSocket serverSocket = new ServerSocket(port);
				Socket socket = serverSocket.accept();
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		) {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				out.println(inputLine);
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
					+ port + " or listening for a connection");
			System.out.println(e.getMessage());
		}

		ServerSocket ss = new ServerSocket(port);
		Socket s = ss.accept();
		System.out.println("A client joined");

		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader br = new BufferedReader(in);

		String str = br.readLine();
		System.out.println(str);

	}
}
