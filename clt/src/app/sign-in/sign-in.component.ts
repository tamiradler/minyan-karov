import { Component, OnInit, AfterViewInit, ElementRef } from '@angular/core';
import { StringsService } from '../strings.service';
import { SignInService } from '../sign-in.service';



@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit, AfterViewInit {

  constructor(private stringService: StringsService, private signInService: SignInService) {
    
  }


  ngOnInit() {
  }





  ngAfterViewInit(): void {
    var googleButton = document.getElementById("google-button");
    this.signInService.googleInit(googleButton);
  }














  disconnect() {
    this.signInService.disconnect();
  }


}
