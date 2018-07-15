import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SenagogMapComponent } from './senagog-map/senagog-map.component';
import { AddSynagogueComponent } from './add-synagogue/add-synagogue.component';
import { SignInComponent } from './sign-in/sign-in.component';


const routes: Routes = [
  { path: '', redirectTo: '/SenagogMap', pathMatch: 'full' },
  { path: 'SenagogMap', component: SenagogMapComponent },
  { path: 'addSenagog', component: AddSynagogueComponent },
  { path: 'signIn', component: SignInComponent }
];



@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
