import { AppAuthGuard } from './service/AppAuthGuard';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, APP_INITIALIZER } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { KeycloakAngularModule, KeycloakService, KeycloakBearerInterceptor } from 'keycloak-angular';
import { FlexModule, FlexLayoutModule } from "@angular/flex-layout";

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';

import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { PaymentComponent } from './user/payment/payment.component';
import { ReplenishComponent } from './user/replenish/replenish.component';
import { AdminProfileComponent } from './admin/admin-profile/admin-profile.component';
import { ManageCardsComponent } from './admin/manage-cards/manage-cards.component';
import { RegisterComponent } from './register/register.component';
import { initializer } from 'src/utils/app-init';
import { NavigationComponent } from './navigation/navigation.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CardListComponent } from './user/card-list/card-list.component';
import { CardDialogComponent } from './user/card-dialog/card-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PaymentSubmittedComponent } from './user/payment-submitted/payment-submitted.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AdminCardComponent } from './admin/admin-card/admin-card.component';
import { CardComponent } from './user/card/card.component';

@NgModule({
  declarations: [
    AppComponent,
    UserProfileComponent,
    PaymentComponent,
    ReplenishComponent,
    AdminProfileComponent,
    ManageCardsComponent,
    RegisterComponent,
    NavigationComponent,
    CardComponent,
    CardListComponent,
    CardDialogComponent,
    PaymentSubmittedComponent,
    AdminCardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    KeycloakAngularModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatPaginatorModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatDialogModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    FlexModule,
    FlexLayoutModule,
    RouterModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializer,
      deps: [KeycloakService],
      multi: true
    },
    {
      provide: AppAuthGuard
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: KeycloakBearerInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
