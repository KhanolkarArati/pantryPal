import { TestBed } from '@angular/core/testing';
import { AuthGuard } from './auth.guard';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let userService: jasmine.SpyObj<UserService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(() => {
    // Create mock objects
    const userSpy = jasmine.createSpyObj('UserService', [], ['isLoggedIn']); // Mock property separately
    const routerSpy = jasmine.createSpyObj('Router', ['createUrlTree']);

    TestBed.configureTestingModule({
      imports: [RouterTestingModule], // Needed for Router dependencies
      providers: [
        AuthGuard,
        { provide: UserService, useValue: userSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });

    guard = TestBed.inject(AuthGuard);
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('should allow access when user is logged in', () => {
    (Object.getOwnPropertyDescriptor(userService, 'isLoggedIn')?.get as jasmine.Spy).and.returnValue(true);
    
    expect(guard.canActivate()).toBeTrue();
  });

  it('should redirect to /login when user is NOT logged in', () => {
    (Object.getOwnPropertyDescriptor(userService, 'isLoggedIn')?.get as jasmine.Spy).and.returnValue(false);

    const mockUrlTree = {} as any; // Fake UrlTree object
    router.createUrlTree.and.returnValue(mockUrlTree);

    expect(guard.canActivate()).toBe(mockUrlTree);
    expect(router.createUrlTree).toHaveBeenCalledWith(['/login']);
  });
});
