import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditContest } from './edit-contest';

describe('EditContest', () => {
  let component: EditContest;
  let fixture: ComponentFixture<EditContest>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditContest]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditContest);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
