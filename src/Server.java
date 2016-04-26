import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Andreas Fischer
 * Peer2Peer
 * 26.04.2016
 * Server.java
 */

/**
 * @author Andreas
 *
 */
public class Server {
	public static void main(String[] args) {
		Socket srvSocket = null ;
		InetAddress localAddress = null;
		ServerSocket mySkServer;
		PrintWriter pout;
		Scanner sc; 
		int i =0;
		String interfaceName = "eth1";

		try {

			NetworkInterface ni = NetworkInterface.getByName(interfaceName);
	        Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
			while(inetAddresses.hasMoreElements()) {
	            InetAddress ia = inetAddresses.nextElement();
	            
	            if(!ia.isLinkLocalAddress()) {
	               if(!ia.isLoopbackAddress()) {
	            	   System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
	            	   localAddress = ia;
	               }
	            }   
            }
			
			//Warning : the backlog value (2nd parameter is handled by the implementation
			mySkServer = new ServerSocket(45000,10,localAddress);

			//set 3min timeout
			mySkServer.setSoTimeout(180000);

			System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
			System.out.println("Usedd IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());

			//wait for client connection		
			srvSocket = mySkServer.accept(); 			
			System.out.println("A client is connected :"+ i++);

			//open the output data stream to write on the client
			pout = new PrintWriter(srvSocket.getOutputStream());

			//wait for an input from the console 
			sc = new Scanner(System.in);			  
			
			String message = "";
			String message_distant ="";
			BufferedReader buffin = new BufferedReader (new InputStreamReader (srvSocket.getInputStream()));
			while(!message_distant.equals("quit")){
				System.out.println("Your message :");
				message = sc.nextLine();

				//write the message on the output stream
				pout.println(message);
				pout.flush();		
				
				
		        message_distant = buffin.readLine();

		        System.out.println("Response: "+message_distant);
		        
			}
			
			

			//Then die
			System.out.println("Now dying");
			srvSocket.close();
			mySkServer.close();
			pout.close();
			

		}catch (SocketException e) {

			System.out.println("Connection Timed out");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}