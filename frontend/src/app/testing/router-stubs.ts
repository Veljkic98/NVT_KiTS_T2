import { BehaviorSubject } from 'rxjs';
import { convertToParamMap, ParamMap } from '@angular/router';
import { Injectable } from '@angular/core';

@Injectable()
export class ActivatedRouteStub {

  private subject = new BehaviorSubject(this.testParams);
  paramMap = this.subject.asObservable();

  private privTestParams: {};
  get testParams(): object { return this.privTestParams; }
  set testParams(params: object) {
    this.privTestParams = params;
    const map = new Map();
    for (const i in params){
      if (params.hasOwnProperty(i)) {
        map.set(i, params[i]);
      }
    }
    this.subject.next(map);
  }

}
