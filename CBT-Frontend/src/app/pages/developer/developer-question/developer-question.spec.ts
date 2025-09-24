import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperQuestion } from './developer-question';

describe('DeveloperQuestion', () => {
  let component: DeveloperQuestion;
  let fixture: ComponentFixture<DeveloperQuestion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeveloperQuestion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeveloperQuestion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
