//

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A GetRequestProcessor contains the logic necessary for handling HTTP GET requests.
 * 
 * @author Stephan Jamieson
 * @version 16/02/2016
 */

public class GetRequestProcessor extends RequestProcessor {

    /**
     * Create a new GetRequestProcessor</br>
     * Calling <code>getRequestMethod()</code> on this object returns <code>HTTPMethodType.GET</code>.
     */
    public GetRequestProcessor() {
        super(HTTPMethodType.GET);
    }
    
    /**
     * Process a given HTTP GET Request message, returning the result in an HTTP Response message.</br>
     */
    public Response process(final Request request) throws Exception {
        Response response;
        if(request!=null) {
            // Code here
            response = new Response(request.getHTTPVersion());
            System.out.println ("GR 0");
            assert (this.canProcess(request.getMethodType()));
            //Generate body for response
            System.out.println ("GR 1 " + request.getURI());
            Path path = Paths.get(request.getURI());
            System.out.println ("GR 2");
            if (Files.exists(path)) {
                System.out.println ("GR 3");
                byte[] content = Files.readAllBytes(path);
                System.out.println ("GR 4");
                response.setBody(content);
                System.out.println ("GR 5");
                response.setStatus(HTTPStatus.OK);
                System.out.println ("GR 6");
            } else {
                response.setStatus(HTTPStatus.NOT_FOUND);
            }
        }else{
            response = new Response("HTTP/1.1");
            response.setStatus(HTTPStatus.BAD_REQUEST);
        }
        return response;
    }
}