package servlets;

import entity.User;
import services.UserService;
import lombok.RequiredArgsConstructor;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final UserService userService;

    public RegistrationServlet() {
        super();
        userService = (UserService) BeanFactory.getBean(UserService.class);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(firstName, secondName, username, password, new ArrayList<>());
        boolean correct = userService.addUser(user);
        if (correct) {
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
