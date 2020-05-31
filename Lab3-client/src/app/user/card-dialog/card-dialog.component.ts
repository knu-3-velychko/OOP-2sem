import { CardService } from './../../service/cardService/card.service';
import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from 'src/app/models/user.model';
import { getEmptyCard } from 'src/app/models/card.model';
import { CardListComponent } from '../card-list/card-list.component';

@Component({
  selector: 'app-card-dialog',
  templateUrl: './card-dialog.component.html',
  styleUrls: ['./card-dialog.component.css']
})
export class CardDialogComponent implements OnInit {
  cardNumber: FormControl;

  constructor(public dialogRef: MatDialogRef<CardListComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User,
    private cardService: CardService
  ) { }

  ngOnInit(): void {
    this.cardNumber = new FormControl("", [Validators.required]);
  }

  submit(): void {
    console.log(this.cardNumber.value);
    console.log(this.cardNumber.valid);
    if (this.cardNumber.valid) {
      const card = getEmptyCard();
      card.cardNumber = this.cardNumber.value;
      card.userEmail = this.data.email;
      this.cardService.addCard(card).subscribe(
        _ => this.dialogRef.close(),
        err => {
          console.log(err);
          alert(err.message);
        }
      );
    }
  }

}
