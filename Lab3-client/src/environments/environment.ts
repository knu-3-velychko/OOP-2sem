import { UserService } from 'src/app/service/userService/user.service';
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import { KeycloakConfig } from 'keycloak-angular';

let keycloakConfig: KeycloakConfig = {
  url: 'http://localhost:8080/auth',
  realm: 'Lab-3',
  clientId: 'Lab3-frontend',
  "credentials": {
    "secret": "540a7e1f-6380-4bce-bb10-abf004c2fff0"
  }
}


export const environment = {
  production: true,
  envName: 'local',
  keycloak: keycloakConfig,
  registerService: 'http://localhost:8180/registration',
  logoutService: 'http://localhost:8180/sso/logout',
  cardService: 'http://localhost:8180/card',
  blockService: 'http://localhost:8180/block/',
  paymentService: 'http://localhost:8180/payment',
  replenishService: 'http://localhost:8180/replenish',
  userService: 'http://localhost:8180/user/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
