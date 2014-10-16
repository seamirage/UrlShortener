package servlets;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.ShortenerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

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
            } catch (SQLException e) {
                logger.error(e.getMessage());
                response.sendError(500);
            }
            response.getOutputStream().print("<html><h1>Link:"+ shortLink + " </h1></html>");
        } else {
            //TODO: redirect to login page
            response.getOutputStream().print("<html><h1>You are not authorized</h1></html>");
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

    //@Inject
    private ShortenerService shortenerService;
    final Logger logger = LoggerFactory.getLogger(ShortenerServlet.class);
}
