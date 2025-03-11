import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let userServiceSpy: jasmine.SpyObj<UserService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    userServiceSpy = jasmine.createSpyObj('UserService', ['userRegister']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule], // Import ReactiveFormsModule to work with FormGroup
      declarations: [RegisterComponent],
      providers: [
        FormBuilder,
        { provide: UserService, useValue: userServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  function setValidFormValues() {
    component.registerForm.setValue({
      email: 'valid@umich.edu',
      userPasswd: 'Passw0rd',
      userrole: 'STUDENT',
      studentId: '12345',
      username: '' // Ensure all fields are set
    });
  }

  it('should create the RegisterComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form correctly', () => {
    expect(component.registerForm).toBeDefined();
    expect(component.registerForm.controls['email']).toBeDefined();
    expect(component.registerForm.controls['studentId']).toBeDefined();
    expect(component.registerForm.controls['userPasswd']).toBeDefined();
    expect(component.registerForm.controls['userrole']).toBeDefined();
    expect(component.registerForm.controls['username']).toBeDefined();
  });

  it('should require email to be a valid umich.edu email', () => {
    const emailControl = component.registerForm.controls['email'];
    
    emailControl.setValue('invalid-email');
    expect(emailControl.valid).toBeFalsy();
    
    emailControl.setValue('valid@umich.edu');
    expect(emailControl.valid).toBeTruthy();
  });

  it('should require studentId to be numeric', () => {
    const studentIdControl = component.registerForm.controls['studentId'];
    
    studentIdControl.setValue('12345');
    expect(studentIdControl.valid).toBeTruthy();
    
    studentIdControl.setValue('abcde');
    expect(studentIdControl.valid).toBeFalsy();
  });

  it('should require password to meet strength conditions', () => {
    const passwordControl = component.registerForm.controls['userPasswd'];
    
    passwordControl.setValue('password');
    expect(passwordControl.valid).toBeFalsy();
    
    passwordControl.setValue('Passw0rd');
    expect(passwordControl.valid).toBeTruthy();
  });

  it('should show an alert if the form is invalid on submit', () => {
    spyOn(window, 'alert'); // Spy on alert
    component.registerForm.reset(); // Reset form to be invalid

    component.register(); // Attempt to submit

    expect(window.alert).toHaveBeenCalledWith('Please fill in all required fields and make sure the conditions are met.');
  });
});
