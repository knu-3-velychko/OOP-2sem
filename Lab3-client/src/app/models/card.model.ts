export interface Card {
    cardNumber: string;
    balance: number;
    blocked: boolean
}

export function getEmptyCard(): Card {
    const card: Card = {
        cardNumber: null,
        balance: null,
        blocked: null
    }

    return card;
}

export function getCard(cardNumber: string, balance: number, blocked: boolean): Card {
    const card: Card = {
        cardNumber: cardNumber,
        balance: balance,
        blocked: blocked
    }

    return card;
}