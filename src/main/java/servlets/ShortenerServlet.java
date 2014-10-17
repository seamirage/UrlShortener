package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.ShortenerService;
import storages.DatabaseException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

@Singleton
public class ShortenerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String originalUri = request.getParameter("url");
        Cookie[] cookies = request.getCookies();

        //Used very simple authentication for focusing on domain problems
        String userId = getUserIdFromCookies(cookies);
        if (null != userId) {
            String shortLink = null;
            try {
                shortLink = shortenerService.shortenAndStore(URLDecoder.decode(originalUri, "UTF-8"), userId);
            } catch (DatabaseException e) {
                logger.error("Could not shorten uri", e);
                response.sendError(500);
            }
            response.getOutputStream().print("<html><h2><a href=\""+ shortLink +"\">"+ shortLink + " </a></h2></html>");
        } else {
            //TODO: redirect to login page
            response.getOutputStream().print("<html><h2>Authorization required</h2></html>");
        }
    }

    private String getUserIdFromCookies(Cookie[] cookies) {
        String userId = null;
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId = cookie.getValue();
                    break;
                }
            }
        }

        return userId;
    }

    @Inject private ShortenerService shortenerService;
    final Logger logger = LoggerFactory.getLogger(ShortenerServlet.class);
}
