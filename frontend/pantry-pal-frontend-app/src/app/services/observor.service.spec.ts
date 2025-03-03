import { TestBed } from '@angular/core/testing';

import { ObservorService } from './observor.service';

describe('ObservorService', () => {
  let service: ObservorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObservorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
