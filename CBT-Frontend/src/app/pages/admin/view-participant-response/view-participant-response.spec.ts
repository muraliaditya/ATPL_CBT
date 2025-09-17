import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewParticipantResponse } from './view-participant-response';

describe('ViewParticipantResponse', () => {
  let component: ViewParticipantResponse;
  let fixture: ComponentFixture<ViewParticipantResponse>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewParticipantResponse]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewParticipantResponse);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
