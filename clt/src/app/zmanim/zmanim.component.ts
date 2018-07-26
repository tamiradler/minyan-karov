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

  zmanimOutput: ZmanimOutput;

  constructor(
    private http: HttpClient,
    public stringsService: StringsService) { }

  ngOnInit() {
    this.getZmanim();
  }



  getZmanim() {
    this.http.get<GetZmanimOutput>(environment.hostUrl+'/getZmanim/latLng/{latLng}/year/{year}/day/{day}/month/{month}/gmtOffset/{gmtOffset}')
      .subscribe(res => {
        this.zmanimOutput = res.zmanimOutput;
      });
  }

}
