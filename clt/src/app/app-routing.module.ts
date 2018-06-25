import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SenagogMapComponent } from './senagog-map/senagog-map.component';
import { AddSynagogueComponent } from './add-synagogue/add-synagogue.component';


const routes: Routes = [
  { path: '', redirectTo: '/SenagogMap', pathMatch: 'full' },
  { path: 'SenagogMap', component: SenagogMapComponent },
  { path: 'addSenagog', component: AddSynagogueComponent }
];



@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
