import { ComponentFixture, TestBed } from '@angular/core/testing';

import { McqSection } from './mcq-section';

describe('McqSection', () => {
  let component: McqSection;
  let fixture: ComponentFixture<McqSection>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [McqSection]
    })
    .compileComponents();

    fixture = TestBed.createComponent(McqSection);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
