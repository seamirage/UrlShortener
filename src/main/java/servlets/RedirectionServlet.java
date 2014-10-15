package servlets;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.RedirectionService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Singleton
public class RedirectionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String shortLinkId = request.getRequestURI();
        try {
            String originalUri = redirectionService.GetRedirectionUrl(shortLinkId);
            if (null != originalUri) {
                response.setStatus(302);
                response.setHeader("Location", originalUri);
                response.setHeader("Connection", "close");
            } else {
                response.setStatus(404);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            response.setStatus(500);
        }
    }

    /*@Inject*/ private RedirectionService redirectionService;
    final Logger logger = LoggerFactory.getLogger(TestServlet.class);
}
