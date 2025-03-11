import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userService: jasmine.SpyObj<UserService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    // Creating spy objects for UserService and Router
    const userSpy = jasmine.createSpyObj('UserService', [
      'userLogin',
      'setUserId',
      'setLoginStatus',
      'setUserEmail',
      'setAdminUser'
    ]);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule],
      declarations: [LoginComponent],
      providers: [
        FormBuilder,
        { provide: UserService, useValue: userSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;

    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have an invalid form when fields are empty', () => {
    expect(component.loginForm.valid).toBeFalse();
  });

  it('should have a valid form when fields are correctly filled', () => {
    component.loginForm.setValue({
      email: 'test@umich.edu',
      userPasswd: 'password123',
      userrole: 'user'
    });
    expect(component.loginForm.valid).toBeTrue();
  });

  it('should display an alert if the form is invalid', () => {
    spyOn(window, 'alert'); // Spy on window alert

    component.loginForm.setValue({
      email: '',
      userPasswd: '',
      userrole: ''
    });

    component.login(); // Call login method

    expect(window.alert).toHaveBeenCalledWith(
      'Please fill in all required fields and make sure the conditions are met.'
    );
  });

  it('should authenticate user and navigate to products page on successful login', () => {
    const mockResponse = {
      message: 'LOGIN SUCESS',
      user: { email: 'test@umich.edu', userPasswd: 'password123', id: '123', userrole: 'user' }
    };

    userService.userLogin.and.returnValue(of(mockResponse)); // Mock login API success

    component.loginForm.setValue({
      email: 'test@umich.edu',
      userPasswd: 'password123',
      userrole: 'user'
    });

    component.login();

    expect(userService.setUserId).toHaveBeenCalledWith('123');
    expect(userService.setLoginStatus).toHaveBeenCalledWith(true);
    expect(userService.setUserEmail).toHaveBeenCalledWith('test@umich.edu');
    expect(userService.setAdminUser).toHaveBeenCalledWith('user');
    expect(router.navigate).toHaveBeenCalledWith(['products']);
  });

  it('should show an alert with the error message on failed login', () => {
    spyOn(window, 'alert'); // Spy on window alert

    const mockResponse = { message: 'Invalid credentials' };
    userService.userLogin.and.returnValue(of(mockResponse)); // Mock failed login

    component.loginForm.setValue({
      email: 'wrong@umich.edu',
      userPasswd: 'wrongpassword',
      userrole: 'user'
    });

    component.login();

    expect(window.alert).toHaveBeenCalledWith('Invalid credentials');
  });

  it('should handle login API errors gracefully', () => {
    spyOn(window, 'alert'); // Spy on window alert

    userService.userLogin.and.returnValue(throwError(() => new Error('Something went wrong')));

    component.loginForm.setValue({
      email: 'test@umich.edu',
      userPasswd: 'password123',
      userrole: 'user'
    });

    component.login();

    expect(window.alert).toHaveBeenCalledWith('Something went wrong');
  });

  it('should navigate to register page when gotoRegister is called', () => {
    component.gotoRegister();
    expect(router.navigate).toHaveBeenCalledWith(['register']);
  });
});
