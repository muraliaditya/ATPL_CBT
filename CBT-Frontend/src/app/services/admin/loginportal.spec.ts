import { TestBed } from '@angular/core/testing';

import { Loginportal } from './loginportal';

describe('Loginportal', () => {
  let service: Loginportal;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Loginportal);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
