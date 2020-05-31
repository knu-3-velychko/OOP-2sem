import { User } from 'src/app/models/user.model';
import { Observable } from 'rxjs';
import { CardService } from './../../service/cardService/card.service';
import { Component, OnInit } from '@angular/core';
import { Card } from 'src/app/models/card.model';

@Component({
  selector: 'app-manage-cards',
  templateUrl: './manage-cards.component.html',
  styleUrls: ['./manage-cards.component.css']
})
export class ManageCardsComponent implements OnInit {
  client: User;
  cards: Observable<Card[]>

  constructor(private cardService: CardService) { }

  ngOnInit(): void {
    this.client = history.state.user;
    this.cards = this.cardService.getCards(this.client.email);
  }
}
