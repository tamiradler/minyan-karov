import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class StringsService {
  data: any;

  constructor(private http: HttpClient) { }

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


  getSettings(): Promise<any> {
    console.log(`getSettings:: before http.get call`);
    const promise = this.http.get(environment.hostUrl + 'getStringConfiguration')
      .toPromise()
      .then(settings => {
        this.data = settings;        
        return settings;
      });
    return promise;
  }


  
}
