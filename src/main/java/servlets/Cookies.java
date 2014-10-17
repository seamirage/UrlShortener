package servlets;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class Cookies {
    public static Cookie getCookieByName(String name, Cookie[] cookies) {
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    public static void removeCookie(String name, Cookie[] cookies, HttpServletResponse response) {
        Cookie removingCookie = getCookieByName(name, cookies);
        if (null != removingCookie) {
            removingCookie.setMaxAge(0);
            response.addCookie(removingCookie);
        }
    }
}
