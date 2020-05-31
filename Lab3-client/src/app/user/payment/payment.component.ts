import { Router } from '@angular/router';
import { Payment, getPayment } from './../../models/payment.model';
import { PaymentService } from './../../service/paymentService/payment.service';
import { UserService } from './../../service/userService/user.service';
import { CardService } from './../../service/cardService/card.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/card.model';
import { User } from 'src/app/models/user.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {
  user: User;
  cards: Observable<Card[]>;
  form: FormGroup = new FormGroup({
    cardFrom: new FormControl(null, Validators.required),
    cardTo: new FormControl('', [Validators.required, Validators.maxLength(30)]),
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
      const payment = getPayment(this.form.get('cardFrom').value.cardNumber, this.form.get('cardTo').value, this.form.get('amount').value);
      this.paymentService.pay(payment).subscribe(
        _ => this.router.navigateByUrl('/submitted'),
        err => {
          console.log(err);
          alert(err.message);
        }
      );
    }
  }
}
