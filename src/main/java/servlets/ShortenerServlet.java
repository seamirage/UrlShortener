package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.ShortenerService;
import storages.DatabaseException;
import validation.UriValidator;

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
        shortenUri(originalUri, userId, response);
    }

    private void shortenUri(String originalUri, String userId, HttpServletResponse response) throws IOException {
        String decodedUri = URLDecoder.decode(originalUri, "UTF-8");
        if (!validator.isValid(decodedUri)) {
            response.sendError(500, "Uri is incorrect.");
            return;
        }

        String shortLink;
        try {
            shortLink = shortenerService.shortenAndStore(decodedUri, userId);
        } catch (DatabaseException e) {
            logger.error("Could not shorten uri", e);
            response.sendError(500);
            return;
        }
        response.getOutputStream().print("<html><h2><a href=\""+ shortLink +"\">"+ shortLink + " </a></h2></html>");
    }

    private String getUserIdFromCookies(Cookie[] cookies) {
        Cookie userIdCookie = Cookies.getCookieByName("userId", cookies);
        if (null != userIdCookie) {
           return userIdCookie.getValue();
        } else {
            return null;
        }
    }

    @Inject private ShortenerService shortenerService;
    @Inject private UriValidator validator;
    final Logger logger = LoggerFactory.getLogger(ShortenerServlet.class);
}
