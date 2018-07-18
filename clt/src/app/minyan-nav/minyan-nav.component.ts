import { Component, OnInit, AfterViewInit, ApplicationRef, NgZone } from '@angular/core';
import { StringsService } from '../strings.service';
import { Router } from '@angular/router';
import { SignInIfc } from '../sign-in-ifc';
import { SignInService } from '../sign-in.service';
import { PsotUserOutput } from '../post-user-output';
import { User } from '../user';

@Component({
  selector: 'app-minyan-nav',
  templateUrl: './minyan-nav.component.html',
  styleUrls: ['./minyan-nav.component.css']
})
export class MinyanNavComponent implements OnInit, SignInIfc, AfterViewInit {
  
  userDisconnected(): void {
    this.ngZone.run(
      () => {
        this.signInStr = this.stringService.getString('signIn','he');
      }
    )
  }
  
  
  signInStr: String;

  
  
  
  ngAfterViewInit(): void {
    this.signInService.googleInit(this);
  }




  userSignedIn(psotUserOutput: PsotUserOutput): void {
    this.ngZone.run(
      () => {
        this.signInStr = this.stringService.getString('hellow','he') 
          + ', ' + psotUserOutput.user.firstName;
      }
    )
  }




  getGoogleButton() {
    return null;
  }




  constructor(public stringService: StringsService, private router: Router, private signInService: SignInService, private ngZone: NgZone) { 
    signInService.subscribe(this);
  }




  ngOnInit() {
    this.signInStr = this.stringService.getString('signIn','he');
  }




  isItemActive(str: string) {
    return this.router.url.includes(str);
  }

}
