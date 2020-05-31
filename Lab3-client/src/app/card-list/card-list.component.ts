import { Component, OnInit } from '@angular/core';
import { Card } from '../models/card.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-card-list',
  templateUrl: './card-list.component.html',
  styleUrls: ['./card-list.component.css']
})
export class CardListComponent implements OnInit {
  cards: Observable<Card>;

  constructor() { }

  ngOnInit(): void {
  }

}
