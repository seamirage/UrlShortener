package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.RedirectionService;
import storages.DatabaseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RedirectionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUri = request.getRequestURI();

        try {
            String originalUri = redirectionService.GetRedirectionUrl(requestUri);
            if (null != originalUri) {
                response.setStatus(302);
                response.setHeader("Location", originalUri);
                response.setHeader("Connection", "close");
            } else {
                response.sendError(404);
            }
        } catch (DatabaseException e) {
            logger.error("Could not find original uri", e);
            response.sendError(500);
        }
    }

    @Inject RedirectionService redirectionService;
    final Logger logger = LoggerFactory.getLogger(ShortenerServlet.class);
}
