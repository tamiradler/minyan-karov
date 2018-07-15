import { Component, OnInit, AfterViewInit } from '@angular/core';
import { StringsService } from '../strings.service';
import { Router } from '@angular/router';
import { SignInIfc } from '../sign-in-ifc';
import { SignInService } from '../sign-in.service';

@Component({
  selector: 'app-minyan-nav',
  templateUrl: './minyan-nav.component.html',
  styleUrls: ['./minyan-nav.component.css']
})
export class MinyanNavComponent implements OnInit, SignInIfc, AfterViewInit {
  ngAfterViewInit(): void {
    this.signInService.googleInit(this);
  }
  userSignedIn(): void {
    
  }
  getGoogleButton() {
    return null;
  }

  constructor(private stringService: StringsService, private router: Router, private signInService: SignInService) { }

  ngOnInit() {
  }

  isItemActive(str: string) {
    return this.router.url.includes(str);
  }

}
