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

/**
 * Expands shortened URL and redirects to this URL. <br/>
 * Response: <br/>
 * <ul>
 *     <li>301, Location = "longUrl": when original URL was found</li>
 *     <li>404: when URL was not found</li>
 * </ul>
 */
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