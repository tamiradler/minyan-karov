import { Injectable } from '@angular/core';

@Injectable()
export class StringsService {
  data: any = {
    checkAdress: {
      he: 'בדוק כתובת'
    },
    synagogueMap: {
      he: 'מפת בתי הכנסת'
    },
    submit: {
      he: 'שלח'
    },
    addSynagogue: {
      he: 'הוספת בית כנסת'
    },
    addMinyan: {
      he: 'הוסף מיניין'
    },
    Minyan: {
      he: 'מיניין'
    },
    nearbyMinyan: {
      he: 'מיניין קרוב'
    },
    synagogueName: {
      he: 'שם בית הכנסת'
    },
    synagogueAddress: {
      he: 'כתובת בית הכנסת'
    },
    nosach: {
      he: 'נוסח'
    },
    minutes: {
      he: 'דקות'
    },
    nosachArray: [
      {he: 'נוסח אחיד'},
      {he: 'עדות המזרח'},
      {he: 'אשכנז'}
    ],
    minyanTypes: [
      {he: 'שחרית'},
      {he: 'מנחה'},
      {he: 'ערבית'}
    ],
    timeAtWeek: [
      {he: 'ימות החול'},
      {he: 'כל יום'},
      {he: 'כל יום חוץ משישי שבת'},
      {he: 'ימים א, ג, ד, ו'},
      {he: 'ימים א,ג,ד'},
      {he: 'יום שבת'},
      {he: 'יום שישי'},
      {he: 'שני וחמישי'}
    ],
    timeTypes: [
      {he: 'בשעה קבועה'},
      {he: 'בזמן קבוע'}
    ],
    beforeAfter: [
      {he: 'לפני'},
      {he: 'אחרי'}
    ],
    dayTimes: [
      {he: 'עלות השחר'},
      {he: 'הנץ'},
      {he: 'צאת הכוכבים'},
      {he: 'זריחה'},
      {he: 'שקיעה'},
      {he: 'חצות היום'}
    ]
  };

  constructor() { }

  getString (str: string, language: string): string {
    return this.data[str][language];
  }


  getStringArray (str: string, language: string): string[] {
    var retVal: string[] = [];
    var arr: any[] = this.data[str];
    arr.forEach(el=>{retVal.push(el[language]);})
    return retVal;
  }


  getStringObjArray (str: string): any[] {
    var retVal: string[] = [];
    return this.data[str];
  }

}
