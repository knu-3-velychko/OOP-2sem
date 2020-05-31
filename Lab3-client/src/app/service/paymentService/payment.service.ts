import { Payment } from './../../models/payment.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/card.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  pay(payment: Payment): Observable<Card> {
    return this.http.post<Card>(environment.paymentService, payment);
  }

  replenish(payment: Payment): Observable<Card> {
    return this.http.post<Card>(environment.replenishService, payment);
  }
}
