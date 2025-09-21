import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicLayout } from './dynamic-layout';

describe('DynamicLayout', () => {
  let component: DynamicLayout;
  let fixture: ComponentFixture<DynamicLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DynamicLayout]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DynamicLayout);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
