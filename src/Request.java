import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//
/**
* Represents an HTTP Request type of message.
* A Request message has a start line comprising HTTP method type, universal 
* resource identifier (URI), and HTTP version.</br>
* It may contain contain header fields, and may have a body.
* 
* 
* 
*/
public class Request extends Message {

    private HTTPMethodType method;
    private String uri;
    private String HTTP_version;
    private byte[] body;
    
    public Request() { super(); }
    
    /**
     * Create a Request message with a request-line composed of the given method type, URI and HTTP version.
     */
    public Request(final HTTPMethodType method, final String uri, final String HTTP_version) {
        super();
        System.out.println ("super");
        this.method=method;
        System.out.println ("method");
        this.uri=uri;
        System.out.println ("uri");
        this.HTTP_version=HTTP_version;
        System.out.println ("http");
    }
        
    /**
     * Determine whether this request has a message body.
     */
    public boolean hasMessageBody() { return body!=null; }
    
    /**
     * Obtain the message body.</br>
     * Requires that <code>this.hasMessageBody()</code>.
     */
    public byte[] getBody() { return body; }

    /**
     * Obtain the request method type.
     */
    public HTTPMethodType getMethodType() { return this.method; }
    
    /**
     * Obtain the requested URI.
     */
    public String getURI() { return this.uri; }
    
    /**
     * Obtain the message http version.
     */
    public String getHTTPVersion() { return this.HTTP_version; }
    
    /**
     * Obtain the message request line i.e. <code>this.getMethodType()+" "+this.getURI()+" "+this.getHTTPVersion()</code>
     */
    public String getStartLine() {   
        return this.getMethodType()+" "+this.getURI()+" "+this.getHTTPVersion(); 
    }
        
    /**
     * Read an HTTP request from the given input stream and return it as a Request object.
     */
    public static Request parse(final InputStream input) throws IOException {
        //Input example "GET /files/fruit.txt HTTP/1.1"
        // Code here.
        try{
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            System.out.println("BufferedReader created");
            //read line by line until the line is NULL (this means end of text/data)
            String line = bufferedReader.readLine();
            System.out.println("Read line");
            while (line != null){
                String[] inputArray = line.split(" ");
                System.out.println("Split successful");
                if(inputArray[0].compareToIgnoreCase("GET")==0){
                    System.out.println("It's a GET");
                    System.out.println(inputArray.length);
                    String uri = inputArray[1];
                    System.out.println("URI: " + uri);
                    uri = uri.replace("%5C","\\");
                    System.out.println("URI: " + uri);
                    uri = uri.substring(1);
                    System.out.println("URI: " + uri);
                    String version = inputArray[2];
                    System.out.println("Version: " + version);
                    Request temp = new Request(HTTPMethodType.GET, uri, version);
                    System.out.println("Request created");
                    return temp;
                }
                line = bufferedReader.readLine();
            }
        }catch (Exception e){
            System.out.println ("AAAAaaargh....something went wrong in Request parse\n" + e);
        }
        return null;
    }
//localhost:8080/C:\Users\Jacques\Documents\UCT\CSC3002F\Coding\Networking\src\response.txt
}