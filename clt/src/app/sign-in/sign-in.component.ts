import { Component, OnInit, AfterViewInit, ElementRef, NgZone } from '@angular/core';
import { StringsService } from '../strings.service';
import { SignInService } from '../sign-in.service';
import { SignInIfc } from '../sign-in-ifc';
import { PsotUserOutput } from '../post-user-output';
import { User } from '../user';



@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit, AfterViewInit, SignInIfc {
  isSignedIn: boolean = false;

  title: string;

  user: User;
  
  userDisconnected(): void {
    this.ngZone.run(
      () => {
        this.isSignedIn = false;
        this.user = undefined;
      })
  }


  userSignedIn(psotUserOutput: PsotUserOutput): void {
    this.ngZone.run(
      () => {
        this.title = this.stringService.getString('hellow','he') + ', ' + psotUserOutput.user.firstName;
        this.isSignedIn = true;
        this.user = psotUserOutput.user;
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
