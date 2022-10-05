import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
This class generate a thread dealing with a client.
It receives message from client and send the same message back.
 */
class Multithreading extends Thread
{
    Socket socket;
    int portNumber;
    public Multithreading(Socket s, int port){
        socket = s;
        portNumber = port;
    }
    public void run() 
    {
        try (
                // Generate reader and writer of the client socket.
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            // Send out the same message received from the client.
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    } 
}