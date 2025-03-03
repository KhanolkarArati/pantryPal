import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginForm !: FormGroup
constructor(private formbuilder:FormBuilder, private userservice:UserService, private router:Router){
  this.loginForm = this.formbuilder.group({
    email: ['', [Validators.required, Validators.pattern('.*@umich\.edu')]],
    phone: ['', [Validators.required, Validators.pattern('[0-9]*')]],
  
  });
}
ngOnInit(): void {
  this.loginForm = this.formbuilder.group({
    email: [''],
    userPasswd: ['', Validators.required],
    userrole: ['']
  })
 

 
}

login(){
  if(this.loginForm.invalid){
    window.alert('Please fill in all required fields and make sure the conditions are met.'); // Show alert if form is invalid
    return;
  }else{
  this.userservice.userLogin(this.loginForm.value).subscribe((data:any)=>{
    if(this.loginForm.value.email === data.user.email && data.message == 'LOGIN SUCESS' && this.loginForm.value.userPasswd=== data.user.userPasswd )
    {
      alert("Authenticated Successfully. ");
     console.log("success login "+this.userservice.isLoggedIn);

      this.userservice.setUserId(data.user.id);
      this.userservice.setLoginStatus(true);
      this.userservice.setUserEmail(data.user.email);
      this.userservice.setAdminUser(data.user.userrole);
      this.loginForm.reset();
      this.router.navigate(["products"]);

    }else{
      alert(data.message);
    }
  },err=>{
    alert("Something went wrong")
  } )}
}

gotoRegister () { 
  this.router.navigate(["register"]);
};

}


