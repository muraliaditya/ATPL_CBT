import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditMcq } from './edit-mcq';

describe('EditMcq', () => {
  let component: EditMcq;
  let fixture: ComponentFixture<EditMcq>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditMcq]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditMcq);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
