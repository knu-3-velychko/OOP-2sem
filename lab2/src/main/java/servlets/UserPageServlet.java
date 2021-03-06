package servlets;

import entity.Card;
import services.CardService;
import services.PaymentService;
import util.BeanFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userPage")
public class UserPageServlet extends HttpServlet {

    private final CardService cardService;
    private final PaymentService paymentService;

    public UserPageServlet() {
        super();
        cardService = (CardService) BeanFactory.getBean(CardService.class);
        paymentService = (PaymentService) BeanFactory.getBean(PaymentService.class);
    }

    public static Long getId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie auth = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("SESSION"))
                auth = cookie;
        }
        if (auth == null || auth.getValue() == null) {
            resp.sendRedirect("login");
            return -1L;
        }
        String decrypted = auth.getValue();

        auth.setMaxAge(0);
        auth.setValue("");

        resp.addCookie(auth);
        resp.sendRedirect("login");

        return Long.parseLong(decrypted.substring(10));
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeParameter = req.getParameter("type");
        if (typeParameter == null || !(typeParameter.equals("payment") || typeParameter.equals("cards") || typeParameter.equals("blockCard") || typeParameter.equals("replenishment"))) {
            req.setAttribute("type", 0);
            resp.sendRedirect("userPage?type=cards");
        } else {
            switch (req.getParameter("type")) {
                case "payment":
                    req.setAttribute("type", -1);
                    break;
                case "cards":
                    req.setAttribute("type", 0);
                    break;
                case "blockCard":
                    req.setAttribute("type", 1);
                    break;
                case "replenishment":
                    req.setAttribute("type", 2);
                    break;
            }


            Long id = getId(req, resp);
            if (id == -1) return;
            List<Card> cards = cardService.getCards(id);
            req.setAttribute("cardList", cards);
            getServletContext().getRequestDispatcher("/userPage.jsp").forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        if (type == null)
            return;

        Long userId = UserPageServlet.getId(req, resp);
        if (userId == -1) return;


        if (!type.equals("cards") && !type.equals("blockCard")) {
            Long cardNo = Long.parseLong(req.getParameter("cards"));
            Float sum = Float.parseFloat(req.getParameter("sum"));
            String comment = req.getParameter("comment");

            switch (type) {
                case "replenishment":
                    paymentService.addPayment(userId, cardNo, sum, comment);
                    resp.sendRedirect("userPage?type=replenishment");
                    break;
                case "payment":
                    paymentService.addPayment(userId, cardNo, -sum, comment);
                    resp.sendRedirect("userPage?type=payment");
                    break;
            }
        } else if (type.equals("cards")) {
            String cardName = req.getParameter("cardName");
            cardService.addCard(userId, cardName);
            resp.sendRedirect("userPage?type=cardName");
        } else {
            Long cardNo = Long.parseLong(req.getParameter("cards"));
            cardService.setBlocked(userId, cardNo, true);
            resp.sendRedirect("userPage?type=blockCard");
        }
    }

}

