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
    private Socket socket;
    private int portNumber;
    private Integer prob_num;
    private Integer delay;
    private Integer count;
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
            String [] inputs = in.readLine().split(" ");
            if (inputs.length == 5 && inputs[0].equals("s") && (inputs[1].equals("rtt") || inputs[1].equals("tput"))){
                prob_num = Integer.parseInt(inputs[2]);
                delay = Integer.parseInt(inputs[4]);
                out.println("200 OK: Ready");
            }
            else{
                out.println("404 ERROR: Invalid Connection Setup Message");
                System.exit(1);
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    } 
}