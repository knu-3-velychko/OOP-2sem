export interface Card {
    cardNumber: string;
    balance: number;
    blocked: boolean;
    userEmail: string;
}

export function getEmptyCard(): Card {
    return {
        cardNumber: null,
        balance: 0,
        blocked: false,
        userEmail: null
    };
}

export function getCard(cardNumber: string, balance: number, blocked: boolean, userEmail: string): Card {
    return {
        cardNumber,
        balance,
        blocked,
        userEmail
    };
}
