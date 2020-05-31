import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/card.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BlockService {

  constructor(private http: HttpClient) { }

  blockCard(card: Card): Observable<Card> {
    return this.http.patch<Card>(environment.blockService + card.cardNumber, {});
  }

  unblockCard(card: Card): Observable<Card> {
    return this.http.delete<Card>(environment.blockService + card.cardNumber, {});
  }
}
