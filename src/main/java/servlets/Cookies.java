package servlets;

import javax.servlet.http.Cookie;

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
}
