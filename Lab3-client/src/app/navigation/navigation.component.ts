import {UserService} from './../service/userService/user.service';
import {Router} from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {User} from '../models/user.model';
import {KeycloakService} from 'keycloak-angular';
import {RegistrationService} from '../service/registrationService/registration.service';

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
    currentUser: User;
    login: Promise<boolean>;

    constructor(private router: Router,
                private userService: UserService,
                private registrationService: RegistrationService,
                private keycloakAngular: KeycloakService) {
    }

    ngOnInit(): void {
        this.currentUser = this.userService.getCurrentUser();
        this.login = this.keycloakAngular.isLoggedIn();
    }

    toProfile() {
        const roles = this.keycloakAngular.getUserRoles();
        if (roles.includes('admin')) {
            this.router.navigateByUrl('/admin_profile');
        } else if (roles.includes('client')) {
            this.router.navigateByUrl('/user_profile');
        }
    }

    logout() {
        this.registrationService.logout(this.currentUser);
        this.keycloakAngular.logout();
    }

}
