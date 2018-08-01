import { Component, OnInit, AfterViewInit, ViewChild, ViewChildren } from '@angular/core';
import { StringsService } from '../strings.service';
import { Synagogue } from '../Synagogue';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { CheckSynagogueAddressComponent } from '../check-synagogue-address/check-synagogue-address.component';
import { AddSynagogueInput } from '../AddSynagogueInput';
import { Minyan } from '../Minyan';
import { AddMinyanComponent } from '../add-minyan/add-minyan.component';

@Component({
  selector: 'app-add-synagogue',
  templateUrl: './add-synagogue.component.html',
  styleUrls: ['./add-synagogue.component.css']
})
export class AddSynagogueComponent implements OnInit {
  synagogue: Synagogue = new Synagogue();
  minyanIdCounter: number = 0;
  latLng: string = '32.086718,34.789760';
  errors: String[] = [];
  @ViewChild(CheckSynagogueAddressComponent) checkSynagogueAddressComponent: CheckSynagogueAddressComponent;
  @ViewChildren(AddMinyanComponent) addMinyanComponents: AddMinyanComponent[];


  constructor(public stringService: StringsService, private http: HttpClient) { }

  ngOnInit() {
    
  }

  

  addSynagogue() {
    var addSynagogueInput: AddSynagogueInput = new AddSynagogueInput();
    addSynagogueInput.synagogue = this.synagogue;
    this.http.post(environment.hostUrl+'addSynagogue', addSynagogueInput).subscribe(res=>{
      if (res['errors']['synagogue'] == undefined) {
        this.errors = [];
      } else {
        this.errors = res['errors']['synagogue'];
      }
      console.log(this.addMinyanComponents.length);
      this.addMinyanComponents.forEach(value => {
        var addMinyanComponent: AddMinyanComponent = value;
        addMinyanComponent.setErrors(res['errors']);
      });
    });
  }


  checkAddress() {
    this.checkSynagogueAddressComponent.checkAddress();
  }


  addMinyan() {
    var minyan: Minyan = new Minyan();
    minyan.minyanId = this.minyanIdCounter + '';
    this.minyanIdCounter++;
    this.synagogue.minyans.push(minyan);
  }
}
