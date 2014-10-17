package servlets;

import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookies.removeCookie("userId", request.getCookies(), response);
        //TODO: redirect to login page
        response.getOutputStream().print("<html><h2>Successfully logged out</h2></html>");
    }
}
