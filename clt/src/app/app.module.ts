import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { APP_INITIALIZER } from '@angular/core';

import { AppComponent } from './app.component';
import { SenagogMapComponent } from './senagog-map/senagog-map.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './/app-routing.module';
import { AddSynagogueComponent } from './add-synagogue/add-synagogue.component';
import { StringsService } from './strings.service';

import { FormsModule } from '@angular/forms';
import { CheckSynagogueAddressComponent } from './check-synagogue-address/check-synagogue-address.component';
import { MinyanNavComponent } from './minyan-nav/minyan-nav.component';
import { AddMinyanComponent } from './add-minyan/add-minyan.component';
import { GetStringConfigComponent } from './get-string-config/get-string-config.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SynagoguePageComponent } from './synagogue-page/synagogue-page.component';

@NgModule({
  declarations: [
    AppComponent,
    SenagogMapComponent,
    AddSynagogueComponent,
    CheckSynagogueAddressComponent,
    MinyanNavComponent,
    AddMinyanComponent,
    GetStringConfigComponent,
    SignInComponent,
    SynagoguePageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [
    StringsService,
    { provide: APP_INITIALIZER, useFactory: (config: StringsService) => () => config.getSettings(), deps: [StringsService], multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
