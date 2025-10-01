import { TestBed } from '@angular/core/testing';

import { Managecontest } from './managecontest';

describe('Managecontest', () => {
  let service: Managecontest;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Managecontest);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
