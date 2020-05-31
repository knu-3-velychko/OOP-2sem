import { Component, OnInit } from '@angular/core';
import { CardService } from 'src/app/service/cardService/card.service';
import { PaymentService } from 'src/app/service/paymentService/payment.service';
import { UserService } from 'src/app/service/userService/user.service';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/card.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { getPayment } from 'src/app/models/payment.model';

@Component({
  selector: 'app-replenish',
  templateUrl: './replenish.component.html',
  styleUrls: ['./replenish.component.css']
})
export class ReplenishComponent implements OnInit {
  user: User;
  cards: Observable<Card[]>;
  form: FormGroup = new FormGroup({
    cardTo: new FormControl(null, Validators.required),
    amount: new FormControl(0, [Validators.required, Validators.min(1)])
  });

  constructor(private cardService: CardService,
    private paymentService: PaymentService,
    private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
    this.user = this.userService.getCurrentUser();
    this.cards = this.cardService.getCards(this.user.email);
  }

  submit() {
    if (this.form.valid) {
      const payment = getPayment('', this.form.get('cardTo').value.cardNumber, this.form.get('amount').value);
      this.paymentService.replenish(payment).subscribe(
        _ => this.router.navigateByUrl('/submitted'),
        err => {
          console.log(err);
          alert(err.message);
        }
      );
    }
  }

}
