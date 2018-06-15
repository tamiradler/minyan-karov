import { BrowserModule } from '@angular/platform-browser';
import { Component, OnInit, NgModule, ViewChild, AfterViewInit } from '@angular/core';
import { } from '@types/googlemaps';
@Component({
  selector: 'app-senagog-map',
  templateUrl: './senagog-map.component.html',
  styleUrls: ['./senagog-map.component.css']
})
export class SenagogMapComponent implements AfterViewInit   {
  map: google.maps.Map;

  constructor() { }


  ngAfterViewInit() {
    //Tel Aviv - 32.086718, 34.789760
    var telAviv = new google.maps.LatLng(32.086718, 34.789760);

    var mapProp = { 
      center: telAviv, 
      zoom: 9, 
      mapTypeId: google.maps.MapTypeId.ROADMAP 
    };

    this.map = new google.maps.Map(document.getElementById('gmap'), mapProp);
  }
    
}
