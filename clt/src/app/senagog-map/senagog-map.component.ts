
import { Component, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GetAllSynagoguesOutput } from '../GetAllSynagoguesOutput';
import { Synagogue } from '../Synagogue';
import { } from '@types/googlemaps';

@Component({
  selector: 'app-senagog-map',
  templateUrl: './senagog-map.component.html',
  styleUrls: ['./senagog-map.component.css']
})
export class SenagogMapComponent implements AfterViewInit   {
  map: google.maps.Map;

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

    this.drowSynagoguesOnMap();
  }


  getAllSynagogues() {
    return this.http.get<GetAllSynagoguesOutput>(environment.hostUrl+'getAllSynagogues');
  }
  
  
  drowSynagoguesOnMap() {
    this.getAllSynagogues().subscribe(res => 
      {
        res.synagogues.forEach(synagogue => {
          this.drowSynagogueOnMap(synagogue);
        });
      });
  }


  drowSynagogueOnMap(synagogue: Synagogue) {
    console.log(synagogue.synagogueName);
    var coordinate: string = synagogue.coordinate;
    if (coordinate === undefined || coordinate === null || coordinate.trim() === '')
    {
      return;
    }
    var latLan: string[] = coordinate.split(':');
    var telAviv = new google.maps.LatLng(parseFloat(latLan[0]), parseFloat(latLan[1]));
    var marker = new google.maps.Marker({position: telAviv, map: this.map});
  }
}
