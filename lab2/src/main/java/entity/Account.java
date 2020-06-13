package entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Account {

    private Long id;
    private Float balance;
    private List<Payment> payments;
}
