import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateContest } from './create-contest';

describe('CreateContest', () => {
  let component: CreateContest;
  let fixture: ComponentFixture<CreateContest>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateContest]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateContest);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
