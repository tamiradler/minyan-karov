import { Injectable } from '@angular/core';
import { SignInIfc } from './sign-in-ifc';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';
import { PsotUserInput } from './psot-user-input';
import { PsotUserOutput } from './post-user-output';

declare const gapi: any;

@Injectable({
  providedIn: 'root'
})
export class SignInService {

  constructor(private http: HttpClient) {
    
  }


  subscribe(signInIfc: SignInIfc) {
    this.signInIfcs.push(signInIfc);
  }


  private clientId:string = '322558399464-cjdske3cg04b0o6v2p17k98pp00vn8pl.apps.googleusercontent.com';
  public auth2: any;

  signInIfcs: SignInIfc[] = [];



  public attachSignin(signInIfc: SignInIfc) {
    this.auth2.attachClickHandler(signInIfc.getGoogleButton(), {},
      (googleUser) => {
        this.userSignedIn(googleUser);
      }, (error) => {
        alert(JSON.stringify(error, undefined, 2));
      });
  }




  private scope = [
    //'profile',
    //'email',
    //'https://www.googleapis.com/auth/plus.me',
    //'https://www.googleapis.com/auth/contacts.readonly',
    //'https://www.googleapis.com/auth/admin.directory.user.readonly'
  ].join(' ');


  public googleInit(signInIfc: SignInIfc) {
    gapi.load('auth2', () => {
      gapi.auth2.init({
        client_id: this.clientId,
        cookiepolicy: 'single_host_origin',
        scope: this.scope
      }).then(() => {
        this.auth2 = gapi.auth2.getAuthInstance();
        console.log('tamiradler',this.auth2.isSignedIn.get());
        
        if (signInIfc.getGoogleButton() != undefined && signInIfc.getGoogleButton() != null) {
          this.attachSignin(signInIfc);   
          gapi.signin2.render(signInIfc.getGoogleButton());
        } else if (this.auth2.isSignedIn.get()) {
          var googleUser = this.auth2.currentUser.get();
          this.userSignedIn(googleUser);
        }
      });
    });
  }



  printUserDetails(googleUser: any) {
    let profile = googleUser.getBasicProfile();
    console.log('Token || ' + googleUser.getAuthResponse().id_token);
    console.log('ID: ' + profile.getId());
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());

    console.log('googleUser',googleUser);
    console.log('AuthResponse',googleUser.getAuthResponse());
    console.log('profile',profile);
  }




  userSignedIn(googleUser: any) {
    this.printUserDetails(googleUser);
    let psotUserInput: PsotUserInput = new PsotUserInput();
    psotUserInput.tokenId = googleUser.getAuthResponse().id_token;
    this.http.post(environment.hostUrl+'psotUser', psotUserInput).subscribe(
      res=>{
        console.log('userSignedIn',res);
        this.signInIfcs.forEach(element=>{
          let PsotUserOutput: PsotUserOutput = res as PsotUserOutput;
          console.log('userSignedIn',PsotUserOutput);
          element.userSignedIn(PsotUserOutput);
        })
      }
    )
  }



  public disconnect() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect();
    this.signInIfcs.forEach(element=>{
      element.userDisconnected();
    })
  }
}


