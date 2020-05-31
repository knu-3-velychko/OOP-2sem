import { AdminProfileComponent } from './admin/admin-profile/admin-profile.component';
import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { PaymentComponent } from './user/payment/payment.component';
import { ReplenishComponent } from './user/replenish/replenish.component';
import { AppAuthGuard } from './service/AppAuthGuard';


const routes: Routes = [
  {
    path: 'user_profile',
    canActivate: [AppAuthGuard],
    component: UserProfileComponent,
    data: { roles: ['client'] }
  },
  {
    path: 'admin_profile',
    canActivate: [AppAuthGuard],
    component: AdminProfileComponent,
    data: { roles: ['admin'] }
  },
  {
    path: 'payment',
    canActivate: [AppAuthGuard],
    component: PaymentComponent,
    data: { roles: ['client'] }
  }, {
    path: 'replenish',
    canActivate: [AppAuthGuard],
    component: ReplenishComponent,
    data: { roles: ['client'] }
  },
  {
    path: '',
    canActivate: [AppAuthGuard],
    component: AppComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
