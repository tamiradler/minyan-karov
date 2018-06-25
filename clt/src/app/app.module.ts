import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { SenagogMapComponent } from './senagog-map/senagog-map.component';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './/app-routing.module';
import { AddSynagogueComponent } from './add-synagogue/add-synagogue.component';
import { StringsService } from './strings.service';

import { FormsModule } from '@angular/forms';
import { CheckSynagogueAddressComponent } from './check-synagogue-address/check-synagogue-address.component';

@NgModule({
  declarations: [
    AppComponent,
    SenagogMapComponent,
    AddSynagogueComponent,
    CheckSynagogueAddressComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [StringsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
