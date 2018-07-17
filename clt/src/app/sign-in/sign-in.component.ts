import { Component, OnInit, AfterViewInit, ElementRef, NgZone } from '@angular/core';
import { StringsService } from '../strings.service';
import { SignInService } from '../sign-in.service';
import { SignInIfc } from '../sign-in-ifc';
import { PsotUserOutput } from '../post-user-output';



@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit, AfterViewInit, SignInIfc {
  isSignedIn: boolean = false;

  
  userDisconnected(): void {
    this.ngZone.run(
      () => {
        this.isSignedIn = false;
      })
  }


  userSignedIn(psotUserOutput: PsotUserOutput): void {
    this.ngZone.run(
      () => {
        this.isSignedIn = true;
        this.signInService.googleInit(this);
      })
  }


  getGoogleButton() {
    return document.getElementById("google-button");
  }


  constructor(private stringService: StringsService, private signInService: SignInService,private ngZone: NgZone) {
    signInService.subscribe(this);
  }


  ngOnInit() {
  }





  ngAfterViewInit(): void {
    this.signInService.googleInit(this);
  }














  disconnect() {
    this.signInService.disconnect();
  }


}
