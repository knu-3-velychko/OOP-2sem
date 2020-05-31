import { Card } from './card.model';

export interface Payment {
    cardFrom: Card;
    cardTo: Card;
    amount: number;
}

export function getPayment(cardFrom: Card, cardTo: Card, amount: number): Payment {
    const payment: Payment = {
        cardFrom: cardFrom,
        cardTo: cardTo,
        amount: amount
    };

    return payment;
}