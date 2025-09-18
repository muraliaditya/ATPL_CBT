import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardViewmcq } from './dashboard-viewmcq';

describe('DashboardViewmcq', () => {
  let component: DashboardViewmcq;
  let fixture: ComponentFixture<DashboardViewmcq>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardViewmcq]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardViewmcq);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
