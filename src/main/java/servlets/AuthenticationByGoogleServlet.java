package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import configuration.UrlShortenerConfiguration;
import org.expressme.openid.*;
import org.slf4j.LoggerFactory;
import storages.DatabaseException;
import storages.dto.UserInfo;
import storages.repositories.UsersRepository;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Authenticates user using Google.
 * Used sample code from:<a href="https://code.google.com/p/jopenid/source/browse/trunk/src/test/java/org/expressme/openid/MainServlet.java">here</a>
 */
@Singleton
public class AuthenticationByGoogleServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        manager = new OpenIdManager();
        manager.setRealm(configuration.getBaseUrl());
        manager.setReturnTo(configuration.getBaseUrl() + "authenticateByGoogle");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if (op==null) {
            checkNonce(request.getParameter("openid.response_nonce"));
            byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
            String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
            Authentication authentication = manager.getAuthentication(request, mac_key, alias);
            String identity = authentication.getIdentity();
            UserInfo user = null;
            try {
                user = usersRepository.addUserIfNotExists(identity);
            } catch (DatabaseException e) {
                logger.error("Could not store or retrieve user", e);
                response.sendError(500);
                return;
            }
            putUserIdInCookies(request, response, user);
            redirectToMainPage(response);
        }
        else if ("Google".equals(op)) {
            Endpoint endpoint = manager.lookupEndpoint("Google");
            Association association = manager.lookupAssociation(endpoint);
            request.getSession().setAttribute(ATTR_MAC, association.getRawMacKey());
            request.getSession().setAttribute(ATTR_ALIAS, endpoint.getAlias());
            String url = manager.getAuthenticationUrl(endpoint, association);
            response.sendRedirect(url);
        }
        else {
            throw new ServletException("Bad parameter op=" + op);
        }
    }

    private void redirectToMainPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("");
    }

    private void putUserIdInCookies(HttpServletRequest request, HttpServletResponse response, UserInfo user) {
        Cookie userIdCookie = Cookies.getCookieByName("userId", request.getCookies());
        if (null != userIdCookie) {
            userIdCookie.setValue(user.getUserId());
            response.addCookie(userIdCookie);
        } else {
            response.addCookie(new Cookie("userId", user.getUserId()));
        }
    }

    void showAuthentication(PrintWriter pw, String identity) {
        pw.print("<html><body><h1>Identity</h1><p>");
        pw.print(identity);
        pw.print("</p></body></html>");
        pw.flush();
    }

    void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20)
            throw new OpenIdException("Verify failed.");
        long nonceTime = getNonceTime(nonce);
        long diff = System.currentTimeMillis() - nonceTime;
        if (diff < 0)
            diff = (-diff);
        if (diff > ONE_HOUR)
            throw new OpenIdException("Bad nonce time.");
        if (isNonceExist(nonce))
            throw new OpenIdException("Verify nonce failed.");
        storeNonce(nonce, nonceTime + TWO_HOUR);
    }

    boolean isNonceExist(String nonce) {
        // TODO: check if nonce is exist in database:
        return false;
    }

    void storeNonce(String nonce, long expires) {
        // TODO: store nonce in database:
    }

    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }

    static final long ONE_HOUR = 3600000L;
    static final long TWO_HOUR = ONE_HOUR * 2L;
    static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
    final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthenticationByGoogleServlet.class);

    OpenIdManager manager;
    @Inject UrlShortenerConfiguration configuration;
    @Inject UsersRepository usersRepository;
}