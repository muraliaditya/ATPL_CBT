import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCodingQuestion } from './add-coding-question';

describe('AddCodingQuestion', () => {
  let component: AddCodingQuestion;
  let fixture: ComponentFixture<AddCodingQuestion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddCodingQuestion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCodingQuestion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
