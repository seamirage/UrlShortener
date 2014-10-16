package servlets;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Singleton
public class ShortenerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String requestUri = request.getRequestURI();
        //request.
        //response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        logger.debug("Received request from URI: " + requestUri);
    }

    final Logger logger = LoggerFactory.getLogger(ShortenerServlet.class);
}
