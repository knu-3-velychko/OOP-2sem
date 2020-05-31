import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-payment-submitted',
  templateUrl: './payment-submitted.component.html',
  styleUrls: ['./payment-submitted.component.css']
})
export class PaymentSubmittedComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  toProfile() {
    this.router.navigateByUrl('/user_profile');
  }
}
