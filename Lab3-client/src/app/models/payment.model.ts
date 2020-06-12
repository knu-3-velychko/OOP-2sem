export interface Payment {
    cardFrom: string;
    cardTo: string;
    amount: number;
}

export function getPayment(cardFrom: string, cardTo: string, amount: number): Payment {
    return {
        cardFrom,
        cardTo,
        amount
    };
}
