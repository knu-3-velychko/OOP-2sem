package servlets;

import entity.User;
import services.UserService;
import lombok.RequiredArgsConstructor;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService;

    public LoginServlet() {
        super();
        userService = (UserService) BeanFactory.getBean(UserService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request);
        System.out.println(response);
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("SESSION") && cookie.getValue() != null) {
                response.sendRedirect("userPage");
                return;
            }
        }

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);
        if (userService.isAdmin(username, password)) {

            response.addCookie(new Cookie("SESSION", "AdminAdmin"));
            response.sendRedirect("adminPage");
            return;
        }
        Long id = userService.checkUser(username, password);
        if (id == -1L) {
            response.addCookie(new Cookie("success", "no"));
            response.sendRedirect("login");
        } else {
            String text = "HelloWorld" + Long.toString(id, 10);
            byte[] encrypted = text.getBytes();
            response.addCookie(new Cookie("SESSION", new String(encrypted)));
            response.sendRedirect("userPage");
        }
    }
}


