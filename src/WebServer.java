import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple Web Server.
 * 
 * The server opens a TCP port and listens on that port for HTTP requests.
 * The server accepts a port number as an optional parameter.</br>
 * If no parameter is given then it requests one be randomly assigned when
 * opening the TCP server socket.</br>
 * In all cases, the server prints out the port that it is using.
 * 
 * 
 * @author Stephan Jamieson
 * @version 16/02/2016
 */
public class WebServer {

    private WebServer() {}
    /**
     * Run the web server. The server accepts a port number as an optional parameter.</br>
     * If no parameter is given then it requests one be randomly assigned when opening the TCP server socket.</br>
     * In all cases, the server prints out the port that it is using.
     */
    public static void main(String argv[]) throws Exception {
		// Get the port number from the command line.
		int port = argv.length>0 ?(new Integer(argv[0])).intValue():0;      //defaults to 0..ie makes port whatever is open
        //Start listening to the port
        System.out.println ("Waiting");
        //ServerSocket serverSocket = new ServerSocket(port);
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Socket Accept");
                GetRequestProcessor getRequestProcessor = new GetRequestProcessor();
                System.out.println("getRequestProcessor");
                Response response = getRequestProcessor.process(Request.parse(socket.getInputStream()));
                System.out.println("Response\nArgv size: " + argv.length);
                //response.send(new FileOutputStream(argv[1]), response);
                response.send(socket.getOutputStream(), response);
                System.out.println("Send Response");
            } catch (Exception e) {
                System.out.println("Something went seriously wrong in WebServer\n" + e);
            }
        }
        //localhost:8080/C:\Users\Jacques\Documents\UCT\CSC3002F\Coding\Networking\src\response.txt
	}

}
