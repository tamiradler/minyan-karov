
import { Component, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { GetAllSynagoguesOutput } from '../GetAllSynagoguesOutput';
import { Synagogue } from '../Synagogue';
import { } from '@types/googlemaps';
import { StringsService } from '../strings.service';

@Component({
  selector: 'app-senagog-map',
  templateUrl: './senagog-map.component.html',
  styleUrls: ['./senagog-map.component.css']
})
export class SenagogMapComponent implements AfterViewInit   {
  map: google.maps.Map;

  constructor(private http: HttpClient, public stringService: StringsService) { } 


  ngAfterViewInit() {
    //Tel Aviv - 32.086718,34.789760
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


    var contentString: string = this.retrieveInfowindowContent(synagogue);

    var infowindow = new google.maps.InfoWindow({
      content: contentString
    });

    var marker = new google.maps.Marker({position: telAviv, map: this.map});

    marker.addListener('click', function() {
      infowindow.open(this.map, marker);
    });
  }


  retrieveInfowindowContent(synagogue: Synagogue): string {
    var next_minyan: string = this.stringService.getString('next_minyan','he');
    var more_information: string = this.stringService.getString('more_information','he');
    var minyanType: string = this.stringService.getString(synagogue.minyans[0].type,'he');
    var time: string = synagogue.minyans[0].time;
    var at: string = this.stringService.getString('at','he');
    var synagogueUrl: string = '/synagoguePage/' + synagogue.synagogueId;
    var contentString: string = '<div id="content">'+
            '<div id="siteNotice">'+
            '</div>'+
            '<h4>' + synagogue.synagogueName + '</h4>'+
            '<div id="bodyContent">'+
            '<p>' + next_minyan + '</p>'+
            '<p><b>' + minyanType + '</b> ' + at + time +'</p>'+
            '<a href="' + synagogueUrl + '">'+
            more_information + '</a> '+
            '</div>'+
            '</div>';
    return contentString;
  }
}
