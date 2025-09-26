import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeQuestion } from './code-question';

describe('CodeQuestion', () => {
  let component: CodeQuestion;
  let fixture: ComponentFixture<CodeQuestion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeQuestion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodeQuestion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
