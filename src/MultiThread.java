import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
This class generate a thread dealing with a client.
It receives messages from client, checking their validity and send probe messages back.
 */
class Multithreading extends Thread
{
    private Socket socket;
    private int portNumber;
    private Integer prob_num;
    private Integer size;
    private Integer delay;

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
            // First read the CSP message sent from client.
            String input = in.readLine();
            String [] inputs = input.split(" ");
            // Check message validity.
            if (inputs.length == 5 && inputs[0].equals("s") && (inputs[1].equals("rtt") || inputs[1].equals("tput"))){
                try {
                    prob_num = Integer.parseInt(inputs[2]);
                    size = Integer.parseInt(inputs[3]);
                    delay = Integer.parseInt(inputs[4]);
                // Return 404 if the three parts are not numeric.
                } catch (NumberFormatException e){
                    System.out.println("404 ERROR: Invalid Connection Setup Message");
                    out.println("404 ERROR: Invalid Connection Setup Message");
                    System.exit(1);
                }
                out.println("200 OK: Ready");
            }
            else{
                System.out.println("404 ERROR: Invalid Connection Setup Message");
                out.println("404 ERROR: Invalid Connection Setup Message");
                System.exit(1);
            }
            // Receive MP message from client for prob_num times.
            for (int i = 0; i < prob_num; i++){
                input = in.readLine();
                inputs = input.split(" ");
                System.out.println("Receive: " + input);
                // Check validity of the message.
                if (inputs.length == 3 && inputs[0].equals("m") && Integer.parseInt(inputs[1]) == i+1 && inputs[2].length() == size){
                    // Apply delay which simulates the propagation delay.
                    Thread.sleep(delay);
                    out.println(input);
                }
                else{
                    System.out.println("404 ERROR: Invalid Measurement Message");
                    out.println("404 ERROR: Invalid Measurement Message");
                    System.exit(1);
                }
            }

            // Receive CTP message from client and check its validity.
            if (!in.readLine().equals("t")){
                System.out.println( "404 ERROR: Invalid Connection Termination Message");
                out.println("404 ERROR: Invalid Connection Termination Message");
                System.exit(1);
            }
            else{
                out.println("200 OK: Closing Connection");
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    } 
}