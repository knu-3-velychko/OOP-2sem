package mapper;

import entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper {
    public static final String PAYMENT_PAY = "pay";
    public static final String PAYMENT_COMMENT = "comment";

    public Payment map(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setPay(resultSet.getFloat(PAYMENT_PAY));
        payment.setComment(resultSet.getString(PAYMENT_COMMENT));
        return payment;
    }
}
