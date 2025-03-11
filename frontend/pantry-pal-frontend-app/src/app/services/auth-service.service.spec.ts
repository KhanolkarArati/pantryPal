import { TestBed } from '@angular/core/testing';
import { AuthService } from '../services/auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should have a default login status as false', () => {
    expect(service.isLoggedIn).toBeFalse();
  });

  it('should set login status to true', () => {
    service.setLoginStatus(true);
    expect(service.isLoggedIn).toBeTrue();
  });

  it('should set login status to false', () => {
    service.setLoginStatus(true);
    service.setLoginStatus(false);
    expect(service.isLoggedIn).toBeFalse();
  });
});
