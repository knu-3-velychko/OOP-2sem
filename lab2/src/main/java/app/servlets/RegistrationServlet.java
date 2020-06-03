package app.servlets;

import app.entities.User;

import app.dao.DBConnection;
import app.services.UserService;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/registration")
@RequiredArgsConstructor
public class RegistrationServlet extends HttpServlet {
    private final UserService userService;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(firstName, secondName, username, password);
        boolean correct = userService.addUser(user);
        if (correct) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}