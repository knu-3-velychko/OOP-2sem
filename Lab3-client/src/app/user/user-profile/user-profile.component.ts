import { Observable } from 'rxjs';
import { RegistrationService } from '../../service/registrationService/registration.service';
import { UserService } from './../../service/userService/user.service';
import { KeycloakService } from 'keycloak-angular';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { getUser, User } from 'src/app/models/user.model';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  userData: Observable<User>;

  constructor(private router: Router,
    private keycloakAngular: KeycloakService,
    private userService: UserService,
    private registrationService: RegistrationService) { }

  ngOnInit(): void {
    try {
      this.keycloakAngular.loadUserProfile(true).then(
        data => {
          console.log("here");
          const user = getUser(Number(data.id), data.email, data.firstName, data.lastName);
          this.userData = this.registrationService.registerUser(user).pipe(
            map(_ => {
              this.userService.setCurrentUsr(user);
              return user;
            },
              err => {
                console.log(err);
                alert(err.message);
              }),
          )
        },
        reason => {
          console.log(reason);
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
