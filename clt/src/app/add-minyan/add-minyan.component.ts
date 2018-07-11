import { Component, OnInit, Input } from '@angular/core';
import { StringsService } from '../strings.service';
import { Minyan } from '../Minyan';
import { Synagogue } from '../Synagogue';

@Component({
  selector: 'app-add-minyan',
  templateUrl: './add-minyan.component.html',
  styleUrls: ['./add-minyan.component.css']
})
export class AddMinyanComponent implements OnInit {
  
  @Input() minyan: Minyan;
  @Input() synagogue: Synagogue;
  @Input() index: number;
  language: string = 'he';
  errors: String[] = [];

  constructor(private stringService: StringsService) { }

  ngOnInit() {
  }

  removeMinyan(ind: number) {
    this.synagogue.minyans.splice(ind, 1);
    console.log(ind);
    console.log(this.synagogue.minyans);
  }


  setErrors(errors: any) {
    if (errors[this.index] == undefined) {
      this.errors = [];
    } else {
      this.errors = errors[this.index];
    }
    console.log('minyan errors:' , this.errors);
  }
}
