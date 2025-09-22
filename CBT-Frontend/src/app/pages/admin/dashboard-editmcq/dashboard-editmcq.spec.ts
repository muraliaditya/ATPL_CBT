import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardEditmcq } from './dashboard-editmcq';

describe('DashboardEditmcq', () => {
  let component: DashboardEditmcq;
  let fixture: ComponentFixture<DashboardEditmcq>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardEditmcq]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardEditmcq);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
