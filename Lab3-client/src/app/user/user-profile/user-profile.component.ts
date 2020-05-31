import { UserService } from './../../service/userService/user.service';
import { KeycloakService } from 'keycloak-angular';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { KeycloakProfile } from 'keycloak-js';
import { getUser } from 'src/app/models/user.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  userData: Promise<KeycloakProfile>;

  constructor(private router: Router,
    private keycloakAngular: KeycloakService,
    private userService: UserService) { }

  ngOnInit(): void {
    try {
      this.keycloakAngular.loadUserProfile().then(
        data => {
          const user = getUser(Number(data.id), data.email, data.firstName, data.lastName);
          this.userService.setCurrentUsr(user);
        }
      );
    } catch (e) {
      console.log('Failed to load user details');
    }
  }

  replenish(): void {
    this.router.navigateByUrl('replenish');
  }

  makePayment(): void {
    this.router.navigateByUrl('payment');
  }

}
