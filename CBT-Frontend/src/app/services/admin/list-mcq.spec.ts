import { TestBed } from '@angular/core/testing';

import { ListMcq } from './list-mcq';

describe('ListMcq', () => {
  let service: ListMcq;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListMcq);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
