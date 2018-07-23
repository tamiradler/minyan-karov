import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SynagoguePageComponent } from './synagogue-page.component';

describe('SynagoguePageComponent', () => {
  let component: SynagoguePageComponent;
  let fixture: ComponentFixture<SynagoguePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SynagoguePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SynagoguePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
