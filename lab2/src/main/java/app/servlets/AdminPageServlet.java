package app.servlets;

import app.entities.User;
import app.services.CardService;
import app.services.UserService;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/adminPage")
@RequiredArgsConstructor
public class AdminPageServlet extends HttpServlet {
    private final UserService userService;
    private final CardService cardService;

    public static boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie auth = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("SESSION"))
                auth = cookie;
        }
        if (auth == null || auth.getValue() == null) {
            resp.sendRedirect("login");
            return true;
        }
        String decrypted = auth.getValue();

        if (!decrypted.equals("AdminAdmin")) {
            resp.sendRedirect("login");
            return true;
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkAdmin(req, resp)) return;

        List<User> users = userService.getUsers();
        req.setAttribute("userList", users);
        getServletContext().getRequestDispatcher("/adminPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkAdmin(req, resp)) return;

        int userId = Integer.parseInt(req.getParameter("users"));
        int cardNo = Integer.parseInt(req.getParameter("cards"));
        List<User> users = userService.getUsers();

        cardService.setBlocked(userId, cardNo, false);
        resp.sendRedirect("adminPage");
    }
}