import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMcqQuestion } from './add-mcq-question';

describe('AddMcqQuestion', () => {
  let component: AddMcqQuestion;
  let fixture: ComponentFixture<AddMcqQuestion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMcqQuestion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddMcqQuestion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
