import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewContest } from './view-contest';

describe('ViewContest', () => {
  let component: ViewContest;
  let fixture: ComponentFixture<ViewContest>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewContest]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewContest);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
