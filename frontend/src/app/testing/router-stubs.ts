import { BehaviorSubject } from 'rxjs';
import { convertToParamMap, ParamMap } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()
export class ActivatedRouteStub {

  private subject = new BehaviorSubject(this.testParams);
  paramMap = this.subject.asObservable();

  private _testParams: {};
  get testParams() { return this._testParams; }
  set testParams(params: {}) {
    this._testParams = params;
    let map = new Map();
    for(let i in params){
      map.set(i, params[i]);
    }
    this.subject.next(map);
  }
  
}