import { Component, OnInit } from '@angular/core';
import { StringsService } from '../strings.service';
import { HttpClient } from '@angular/common/http';
import { GetZmanimOutput } from '../get-zmanim-output';
import { environment } from '../../environments/environment';
import { ZmanimOutput } from '../zmanim-output';

@Component({
  selector: 'app-zmanim',
  templateUrl: './zmanim.component.html',
  styleUrls: ['./zmanim.component.css']
})
export class ZmanimComponent implements OnInit {
  date: Date = new Date();
  zmanimOutput: ZmanimOutput;
  self: ZmanimComponent = this;

  constructor(
    private http: HttpClient,
    public stringsService: StringsService) { }

  ngOnInit() {
    navigator.geolocation.getCurrentPosition(position => this.setCurrentLocation(position, this), error=>this.handleError(error,this));
  }


///getZmanim/latLng/{latLng}/year/{year}/day/{day}/month/{month}
  getZmanim(latLng: string) {
    var today = this.date;
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();

    var getZmanimUrl: string = '/getZmanim'
                                + '/latLng/' + latLng
                                + '/year/' + yyyy
                                + '/day/' + dd
                                + '/month/' + mm;

    this.http.get<GetZmanimOutput>(environment.hostUrl+getZmanimUrl)
      .subscribe(res => {
        this.zmanimOutput = res.zmanimOutput;
      });
  }


  setCurrentLocation(position, self: ZmanimComponent) {
    console.log(position);
    self.getZmanim(position.coords.latitude + "," + position.coords.longitude);
  }


  handleError(error, self: ZmanimComponent) {
    self.getZmanim('32.086718,34.789760');
  }


  dateChange() {
    this.zmanimOutput = undefined;
    this.date = new Date(this.date);
    console.log(this.date);
    this.ngOnInit();
  }

}
