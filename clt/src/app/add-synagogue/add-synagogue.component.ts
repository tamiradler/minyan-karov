import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { StringsService } from '../strings.service';
import { Synagogue } from '../Synagogue';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { CheckSynagogueAddressComponent } from '../check-synagogue-address/check-synagogue-address.component';
import { AddSynagogueInput } from '../AddSynagogueInput';

@Component({
  selector: 'app-add-synagogue',
  templateUrl: './add-synagogue.component.html',
  styleUrls: ['./add-synagogue.component.css']
})
export class AddSynagogueComponent implements OnInit {
  synagogue: Synagogue = new Synagogue();
  @ViewChild(CheckSynagogueAddressComponent) checkSynagogueAddressComponent: CheckSynagogueAddressComponent;

  constructor(private stringService: StringsService, private http: HttpClient) { }

  ngOnInit() {
    
  }

  

  addSynagogue() {
    var addSynagogueInput: AddSynagogueInput = new AddSynagogueInput();
    addSynagogueInput.synagogue = this.synagogue;
    this.http.post(environment.hostUrl+'addSynagogue', addSynagogueInput).subscribe(res=>{});
  }


  checkAddress() {
    this.checkSynagogueAddressComponent.checkAddress();
  }
}
