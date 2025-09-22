import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperMainSection } from './developer-main-section';

describe('DeveloperMainSection', () => {
  let component: DeveloperMainSection;
  let fixture: ComponentFixture<DeveloperMainSection>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeveloperMainSection]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeveloperMainSection);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
