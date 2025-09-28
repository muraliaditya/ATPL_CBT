import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCodingquestion } from './edit-codingquestion';

describe('EditCodingquestion', () => {
  let component: EditCodingquestion;
  let fixture: ComponentFixture<EditCodingquestion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditCodingquestion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCodingquestion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
