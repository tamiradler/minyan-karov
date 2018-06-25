import { Component, AfterViewInit, Input } from '@angular/core';
import { Synagogue } from '../Synagogue';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-check-synagogue-address',
  templateUrl: './check-synagogue-address.component.html',
  styleUrls: ['./check-synagogue-address.component.css']
})
export class CheckSynagogueAddressComponent implements AfterViewInit {
  map: google.maps.Map;
  marker: any;
  @Input() synagogue: Synagogue;

  constructor(private http: HttpClient) { }

  ngAfterViewInit() {
    //Tel Aviv - 32.086718, 34.789760
    var telAviv = new google.maps.LatLng(32.086718, 34.789760);

    var mapProp = { 
      center: telAviv, 
      zoom: 9, 
      mapTypeId: google.maps.MapTypeId.ROADMAP 
    };
    
    var divMap = document.getElementById('gmap');
    this.map = new google.maps.Map(divMap, mapProp);
    this.map.addListener('click',e=>{
      this.placeMarker(e.latLng, this.map);
    });
  }


  checkAddress() {
    this.http.get('https://script.google.com/macros/s/AKfycbxWJgCP0uIg5AgVxORyPWbZw4p7IJotc1aIcIdNFFqjmzIpUog/exec?fun=fullLocation&addre='
      +this.synagogue.address).subscribe(res=>{
        if (res['status'] === 'OK') {
          var coordinate = res['results'][0]['geometry']['location'];
          this.map.setCenter(coordinate);
          this.map.setZoom(15);
          
          this.placeMarker(coordinate, this.map);
        }
      })    
  }



  placeMarker(latLng, imap: google.maps.Map) {
    if (this.marker !== undefined) {
      this.marker.setMap(null);
    }
    this.marker = new google.maps.Marker({position: latLng, map: imap});
    this.synagogue.coordinate = this.marker.getPosition().lat() + ":" + this.marker.getPosition().lng();
    debugger;
  }


}
