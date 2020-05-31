import { UserService } from '../../service/userService/user.service';
import { CardService } from '../../service/cardService/card.service';
import { Component, OnInit } from '@angular/core';
import { Card } from '../../models/card.model';
import { Observable } from 'rxjs';
import { User } from '../../models/user.model';
import { MatDialog } from '@angular/material/dialog';
import { CardDialogComponent } from '../card-dialog/card-dialog.component';

@Component({
  selector: 'app-card-list',
  templateUrl: './card-list.component.html',
  styleUrls: ['./card-list.component.css']
})
export class CardListComponent implements OnInit {
  currentUser: User;
  cards: Observable<Card[]>;

  constructor(private cardService: CardService,
    private userService: UserService,
    public dialog: MatDialog) { }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();
    this.loadCards();
  }

  loadCards() {
    this.cards = this.cardService.getCards(this.currentUser.email);
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(
      CardDialogComponent,
      {
        width: '30em',
        data: this.currentUser
      }
    );

    dialogRef.afterClosed().subscribe(
      _ => this.loadCards()
    )
  }

}
