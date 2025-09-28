import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCodingquestion } from './view-codingquestion';

describe('ViewCodingquestion', () => {
  let component: ViewCodingquestion;
  let fixture: ComponentFixture<ViewCodingquestion>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewCodingquestion]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewCodingquestion);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
