import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-synagogue-page',
  templateUrl: './synagogue-page.component.html',
  styleUrls: ['./synagogue-page.component.css']
})
export class SynagoguePageComponent implements OnInit {

  constructor(private activatedRoute: ActivatedRoute,
    private http: HttpClient
  ) { }

  ngOnInit() {
    var synagogueId:string = this.activatedRoute.snapshot.paramMap.get('synagogueId');
  }

}
