import { Injectable } from '@angular/core';

@Injectable()
export class StringsService {
  data: any = {
    checkAdress: {
      he: 'בדוק כתובת'
    },
    submit: {
      he: 'שלח'
    },
    addSynagogue: {
      he: 'הוספת בית כנסת'
    },
    synagogueName: {
      he: 'שם בית הכנסת'
    },
    synagogueAddress: {
      he: 'כתובת בית הכנסת'
    }
  };

  constructor() { }

  getString(str: string, language: string): string {
    return this.data[str][language];
  }

}
