import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminMainSection } from './admin-main-section';

describe('AdminMainSection', () => {
  let component: AdminMainSection;
  let fixture: ComponentFixture<AdminMainSection>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminMainSection]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminMainSection);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
