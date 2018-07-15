import { Injectable } from '@angular/core';

declare const gapi: any;

@Injectable({
  providedIn: 'root'
})
export class SignInService {

  constructor() {
    
  }

  private clientId:string = '322558399464-cjdske3cg04b0o6v2p17k98pp00vn8pl.apps.googleusercontent.com';
  public auth2: any;




  public attachSignin(element) {
    this.auth2.attachClickHandler(element, {},
      (googleUser) => {
        this.printUserDetails(googleUser);
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


  public googleInit(googleButton: any) {
    gapi.load('auth2', () => {
      gapi.auth2.init({
        client_id: this.clientId,
        cookiepolicy: 'single_host_origin',
        scope: this.scope
      }).then(() => {
        this.auth2 = gapi.auth2.getAuthInstance();
        console.log('tamiradler',this.auth2.isSignedIn.get()); //now this always returns correctly
        if (this.auth2.isSignedIn.get()) {
          var googleUser = this.auth2.currentUser.get();
          this.printUserDetails(googleUser);
        }
        this.attachSignin(googleButton);   
      });
      gapi.signin2.render(googleButton);
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


  public disconnect() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect();
  }
}


