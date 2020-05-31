export interface Card {
    cardNumber: string;
    balance: number;
    blocked: boolean;
    userEmail: string;
}

export function getEmptyCard(): Card {
    const card: Card = {
        cardNumber: null,
        balance: 0,
        blocked: false,
        userEmail: null
    }

    return card;
}

export function getCard(cardNumber: string, balance: number, blocked: boolean, userEmail: string): Card {
    const card: Card = {
        cardNumber: cardNumber,
        balance: balance,
        blocked: blocked,
        userEmail: userEmail
    }

    return card;
}