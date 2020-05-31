import { Router } from '@angular/router';
import { UserService } from 'src/app/service/userService/user.service';
import { FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {
  email: FormControl = new FormControl('', [Validators.required, Validators.email]);

  constructor(private userService: UserService,
    private router: Router) { }

  ngOnInit(): void {
  }

  submit() {
    if (this.email.valid) {
      this.userService.getUserByEmail(this.email.value).subscribe(
        resp => {
          console.log(resp.length);
          if (resp.length > 0) {
            this.router.navigate(['/manage_cards'], {
              state: {
                user: resp[0]
              }
            });
          }
        }
      );
    }
  }

}
