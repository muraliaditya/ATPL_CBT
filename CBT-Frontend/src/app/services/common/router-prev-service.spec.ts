import { TestBed } from '@angular/core/testing';

import { RouterPrevService } from './router-prev-service';

describe('RouterPrevService', () => {
  let service: RouterPrevService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouterPrevService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
