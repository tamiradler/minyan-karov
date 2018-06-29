import { Component, OnInit } from '@angular/core';
import { StringsService } from '../strings.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-minyan-nav',
  templateUrl: './minyan-nav.component.html',
  styleUrls: ['./minyan-nav.component.css']
})
export class MinyanNavComponent implements OnInit {

  constructor(private stringService: StringsService, private router: Router) { }

  ngOnInit() {
  }

  isItemActive(str: string) {
    return this.router.url.includes(str);
  }

}
