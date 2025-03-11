import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Import HttpClientTestingModule to mock HTTP requests
      providers: [UserService] // Provide the UserService
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController); // Inject HttpTestingController to mock requests
  });

  afterEach(() => {
    httpMock.verify(); // Verify that no unmatched requests are made
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // Test set and get user ID
  it('should set and get user ID', () => {
    const userId = '123';
    service.setUserId(userId);
    expect(service.getUserId()).toBe(userId);
  });

  // Test set and get user email
  it('should set and get user email', () => {
    const email = 'test@example.com';
    service.setUserEmail(email);
    expect(service.getUserEmail()).toBe(email);
  });

  // Test set login status to true
  it('should set login status to true', () => {
    service.setLoginStatus(true);
    expect(service.isLoggedIn).toBeTrue();
  });

  // Test set admin user status to true for ADMIN
  it('should set admin user status to true for ADMIN', () => {
    service.setAdminUser('ADMIN');
    expect(service.isAdminUser).toBeTrue();
  });

  // Test set login status to false
  it('should set login status to false', () => {
    service.setLoginStatus(false);
    expect(service.isLoggedIn).toBeFalse();
  });

  // Test set admin user status to false for USER
  it('should set admin user status to false for USER', () => {
    service.setAdminUser('USER');
    expect(service.isAdminUser).toBeFalse();
  });

  // Test userDetails API call
  it('should fetch user details', () => {
    const userId = '123';
    const mockUserDetails = { id: '123', name: 'John Doe' };

    service.userDetails(userId).subscribe((user: any) => {  // Ensure `user` type matches the response
      expect(user).toEqual(mockUserDetails);
    });

    const req = httpMock.expectOne(`http://localhost:8080/user/${userId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUserDetails); // Mock response
  });

  // Test userLogin API call
  it('should perform user login', () => {
    const loginRequest = { email: 'test@example.com', password: 'password' };
    const mockResponse = { success: true };

    service.userLogin(loginRequest).subscribe((response: any) => {  // Use `any` if ApiResponse is not defined
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('http://localhost:8080/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(loginRequest);
    req.flush(mockResponse); // Mock response
  });

  // Test logout API call
  it('should perform logout', () => {
    const email = 'test@example.com';
    const mockResponse = { success: true };

    service.logout(email).subscribe((response: any) => {  // Use `any` instead of `ApiResponse`
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('http://localhost:8080/logout');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ email });
    req.flush(mockResponse); // Mock response
  });

  // Test userRegister API call
  it('should perform user registration', () => {
    const registerRequest = { email: 'test@example.com', password: 'password' };
    const mockResponse = { success: true };

    service.userRegister(registerRequest).subscribe((response: any) => {  // Use `any`
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('http://localhost:8080/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(registerRequest);
    req.flush(mockResponse); // Mock response
  });
});
