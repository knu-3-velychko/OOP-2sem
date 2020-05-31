export interface Payment {
    cardFrom: string;
    cardTo: string;
    amount: number;
}

export function getPayment(cardFrom: string, cardTo: string, amount: number): Payment {
    const payment: Payment = {
        cardFrom: cardFrom,
        cardTo: cardTo,
        amount: amount
    };

    return payment;
}