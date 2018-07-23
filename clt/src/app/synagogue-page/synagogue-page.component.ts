import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { StringsService } from '../strings.service';
import { GetAllSynagoguesOutput } from '../GetAllSynagoguesOutput';
import { environment } from '../../environments/environment';
import { Synagogue } from '../Synagogue';

@Component({
  selector: 'app-synagogue-page',
  templateUrl: './synagogue-page.component.html',
  styleUrls: ['./synagogue-page.component.css']
})
export class SynagoguePageComponent implements OnInit {

  constructor(
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    public stringService: StringsService
  ) { }

  synagogueId: string;
  synagogue: Synagogue;

  ngOnInit() {
    this.synagogueId = this.activatedRoute.snapshot.paramMap.get('synagogueId');
    this.getSynagogue();
  }

  getSynagogue() {
    return this.http.get<GetAllSynagoguesOutput>(environment.hostUrl+'getSynagogue/'+this.synagogueId).subscribe(res => 
      {
        res.synagogues.forEach(synagogue => {
          this.synagogue = synagogue;
        });
      });;
  }

}
