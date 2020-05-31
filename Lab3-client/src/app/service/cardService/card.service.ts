import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/card.model';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  constructor(private http: HttpClient) { }

  getCards(email: string): Observable<Card[]> {
    return this.http.get<Card[]>(environment.cardService + '/?email=' + email);
  }

  addCard(card: Card): Observable<Card> {
    return this.http.post<Card>(environment.cardService, card);
  }
}
