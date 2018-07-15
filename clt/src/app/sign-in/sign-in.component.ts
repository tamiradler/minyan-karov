import { Component, OnInit, AfterViewInit, ElementRef } from '@angular/core';


declare const gapi: any;

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit, AfterViewInit {

  constructor(private element: ElementRef) {
    console.log('ElementRef: ', this.element);
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.googleInit();
  }

  private clientId:string = '322558399464-cjdske3cg04b0o6v2p17k98pp00vn8pl.apps.googleusercontent.com';

  private scope = [
    //'profile',
    //'email',
    //'https://www.googleapis.com/auth/plus.me',
    //'https://www.googleapis.com/auth/contacts.readonly',
    //'https://www.googleapis.com/auth/admin.directory.user.readonly'
  ].join(' ');

  public auth2: any;

  public googleInit() {        
    gapi.load('auth2', () => {
      this.auth2 = gapi.auth2.init({
        client_id: this.clientId,
        cookiepolicy: 'single_host_origin',
        scope: this.scope
      });
      var googleButton = document.getElementById("google-button");
      this.attachSignin(googleButton);
      gapi.signin2.render(googleButton);
    });
  }


  public attachSignin(element) {
    this.auth2.attachClickHandler(element, {},
      (googleUser) => {

        let profile = googleUser.getBasicProfile();
        console.log('Token || ' + googleUser.getAuthResponse().id_token);
        console.log('ID: ' + profile.getId());
        console.log('Name: ' + profile.getName());
        console.log('Image URL: ' + profile.getImageUrl());
        console.log('Email: ' + profile.getEmail());

        console.log('googleUser',googleUser);
        console.log('AuthResponse',googleUser.getAuthResponse());
        console.log('profile',profile);
        

      }, (error) => {
        alert(JSON.stringify(error, undefined, 2));
      });
  }



  disconnect() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.disconnect();
  }


}
