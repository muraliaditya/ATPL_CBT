import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCode } from './add-code';

describe('AddCode', () => {
  let component: AddCode;
  let fixture: ComponentFixture<AddCode>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddCode]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCode);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
