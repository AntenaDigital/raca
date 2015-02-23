package raca.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
*
* @author ANTENA DIGITAL
*/
public class RacaMediatorProxy extends HttpServlet implements ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	public static String WRONG_INPUT_ERROR_MSG = "WRONG INPUT PARAMETERS...";
    public static String RECEIVER_INITIALIZATION_ERROR_MSG = "RECEIVER NOT INITIALIZED...";
    public static String QUEUECONNECTION_CLOSE_ERROR_MSG = "WAS UNABLE TO CLOSE QUEUE CONNECTION...";


    protected void responseErrorMsg(HttpServletResponse response,String errMessage) {        

            response.setContentType("text/html");

            try {

                PrintWriter writer = response.getWriter();

                writer.println("<html>");
                writer.println("<head>");
                writer.println("<title>JMS Servlet</title>");
                writer.println("</head>");
                writer.println("<body bgcolor=white>");
              

                writer.println("<h1>Response Error... " + errMessage + "</h1>");

                writer.println("</body>");
                writer.println("</html>");

            } catch (IOException exc) {
                
                exc.printStackTrace();
            }
    }

    protected String ex2str(Throwable t){

    	try {

    		ByteArrayOutputStream os = new ByteArrayOutputStream();
    		PrintWriter pw = new PrintWriter(os);
    		t.printStackTrace(pw);
    		pw.flush();

    		return new String(os.toByteArray());

    	} catch (Throwable e) {
    		return t.toString();
    	}
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
     
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {


    }
}