import { Injectable } from '@angular/core';
import {  CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(private userSerivce:UserService,private router:Router){

  }

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree{

    if(this.userSerivce.isLoggedIn){
      return true;
    }else{
      return this.router.createUrlTree(['/login']);
    }
  }
   
  }
  

