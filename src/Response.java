import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility.getBytes;

//
/**
 * 
 * Represents a Response type of HTTP message.</br>
 * A Response message has a status line comprising HTTP version, status code
 * and status description. It may contain header fields and may contain a message body.
 * 
 * 
 * @author Stephan Jamieson
 * @version 16/02/2016
 */
public class Response extends Message {
    
    private HTTPStatus status;
    private String httpVersion;
    private InputStream bodyInput;
    
    /**
     * Create a Response message that adheres to the given version of the HTTP.
     */
    public Response(final String httpVersion) {
        this.status = status;
        this.httpVersion = httpVersion;
    }
    
    /**
     * Set the response status.
     */
    public void setStatus(HTTPStatus status) { this.status = status; }
    
    /**
     * Obtain the response status.
     */
    public HTTPStatus getStatus() { return status; }
    
    /**
     * Obtain the HTTP version.
     */
    public String getHTTPVersion() { return httpVersion; }
    
    /**
     * Obtain the response message status line i.e. <code>this.getHTTPVersion()+" "+this.getStatus</code>.
     */
    public String getStartLine() { 
        return this.httpVersion+" "+this.status;
    }
    
    /**
     * Set the message body.</br>
     * (The effect of the method is <code>this.setBody(new ByteArrayInputStream(data))</code>.)
     * 
     */
    public void setBody(byte[] data) {
        this.setBody(new ByteArrayInputStream(data));
    }
    
    /**
     * Set the input stream from which the message body can be read.</br>
     * For use when the body of the message is a file. When the message is sent 
     * (see the <code>send()</code> method) the body may be read a chunk at a time 
     * from the given input stream and written to the output stream.
     */
    public void setBody(InputStream bodyInput) {
        this.bodyInput = bodyInput;
    }
        
    /**
     * Send the given Response via the given output stream.</br>
     * The method writes the start line, followed by the header 
     * fields (one per line), followed by a blank line and then 
     * the message body.</br>
     * NOTE That lines are terminated with a carriage return and line feed. 
     * (The <code>Message</code> class defines the constant <code>CRLF</code> for this purpose.)
     */
    public static void send(final OutputStream output, final Response response) throws IOException   {
        // Code here.
        System.out.println ("send 0");
        Date dNow = new Date( );
        System.out.println ("send 1");
        SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

        System.out.println ("send 2");
        String httpResponse = response.getHTTPVersion() + " " + response.getStatus() +"\n" +
                "Date: " + ft.format(dNow) + "\n" +
                "Connection: Closed\n";
        System.out.println ("send 3");
        if(response.getStatus()==HTTPStatus.NOT_FOUND){
            httpResponse+="Content-Length: 230\n" +
                    "Connection: Closed\n" +
                    "Content-Type: text/html; charset=iso-8859-1\n" +
                    "\n" +
                    "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "   <title>404 Not Found</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "   <h1>Not Found</h1>\n" +
                    "   <p>The requested URL /t.html was not found on this server.</p>\n" +
                    "</body>\n" +
                    "</html>";
        }else if(response.getStatus()==HTTPStatus.BAD_REQUEST){
            httpResponse += "Content-Length: 230\n" +
                    "Content-Type: text/html; charset=iso-8859-1\n" +
                    "Connection: Closed\n" +
                    "\n" +
                    "  \n" +
                    "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "   <title>400 Bad Request</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "   <h1>Bad Request</h1>\n" +
                    "   <p>Your browser sent a request that this server could not understand.</p>\n" +
                    "   <p>The request line contained invalid characters following the protocol string.</p>\n" +
                    "</body>\n" +
                    "</html>";
        }else{
            httpResponse += "Content-Type: text/html\n" +
                    "Content-Length: " + getBytes(response.bodyInput) + "\r\n"+
                    "\n" +
                    "<html>\n" +
                    "<body>\n" +
                    "<h1>" + response.bodyInput + "</h1>\n" +
                    "</body>\n" +
                    "</html>";
        }


        output.write(httpResponse.getBytes("UTF-8"));
    }

}