import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MCQListing } from './mcq-listing';

describe('MCQListing', () => {
  let component: MCQListing;
  let fixture: ComponentFixture<MCQListing>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MCQListing]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MCQListing);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
