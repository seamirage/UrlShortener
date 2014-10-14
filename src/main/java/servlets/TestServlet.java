package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestUri = request.getRequestURI();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("Hello");

     //   logger.debug("Received request from URI: " + requestUri);
    }

    //final Logger logger = LoggerFactory.getLogger(TestServlet.class);
}
