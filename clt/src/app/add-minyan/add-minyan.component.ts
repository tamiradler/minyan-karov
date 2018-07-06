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


  constructor(private stringService: StringsService) { }

  ngOnInit() {
    console.log(this.minyan.minyanId);
  }

  removeMinyan(ind: number) {
    this.synagogue.minyans.splice(ind, 1);
    console.log(ind);
    console.log(this.synagogue.minyans);
  }


  print(s: any) {
    console.log(s);
  }
}
