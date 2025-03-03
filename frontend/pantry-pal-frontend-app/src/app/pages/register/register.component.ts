import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  public registerForm !: FormGroup
constructor(private formbuilder:FormBuilder, private userservice:UserService, private router:Router){
  this.registerForm = this.formbuilder.group({
    //     email: ['', [Validators.required, Validators.pattern('.*@umich\.edu')]],

    email: ['', [Validators.required, Validators.pattern('.*@umich\.edu')]],
    studentId: ['', [Validators.required, Validators.pattern('^[0-9]+$')]],
    userPasswd: ['', [Validators.required, Validators.pattern('^(?=.*[A-Z]).{6,}$')]]
 
  });
  
}
  ngOnInit():void{
    this.registerForm = this.formbuilder.group({
      email: [''],
      userPasswd: ['', Validators.required],
      userrole: ['STUDENT'],
      studentId: [''],
      username:  ['']
    })
  }

  register(){
    if(this.registerForm.invalid){
      window.alert('Please fill in all required fields and make sure the conditions are met.'); // Show alert if form is invalid
      return;
    }else{
    this.userservice.userRegister(this.registerForm.value).subscribe((data:any)=>{
      if(data.message == 'Registration Successfull')
      {
        alert("Registration Successful. Please login to access the portal.");
        this.registerForm.reset();
        this.router.navigate(["login"]);
  
      }else{
        alert("Registration Failed ! Please try with correct credentials.");
      }
    },err=>{
      alert("Something went wrong")
    } )
  }
  }

}

