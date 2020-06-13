package entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Card {
    private String name;
    private boolean blocked;
    private Account account;
}
