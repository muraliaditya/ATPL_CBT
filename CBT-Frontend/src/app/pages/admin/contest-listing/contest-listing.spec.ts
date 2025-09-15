import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContestListing } from './contest-listing';

describe('ContestListing', () => {
  let component: ContestListing;
  let fixture: ComponentFixture<ContestListing>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContestListing]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContestListing);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
