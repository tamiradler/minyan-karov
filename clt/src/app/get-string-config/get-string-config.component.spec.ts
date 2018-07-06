import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GetStringConfigComponent } from './get-string-config.component';

describe('GetStringConfigComponent', () => {
  let component: GetStringConfigComponent;
  let fixture: ComponentFixture<GetStringConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetStringConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GetStringConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
