import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmissionScreen } from './submission-screen';

describe('SubmissionScreen', () => {
  let component: SubmissionScreen;
  let fixture: ComponentFixture<SubmissionScreen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubmissionScreen]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubmissionScreen);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
