import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodingLayout } from './coding-layout';

describe('CodingLayout', () => {
  let component: CodingLayout;
  let fixture: ComponentFixture<CodingLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodingLayout],
    }).compileComponents();

    fixture = TestBed.createComponent(CodingLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
