import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userEmail:string ='';
  private userId: string = '';
  isLoggedIn:boolean =false;
  isAdminUser: boolean = false;

  constructor(private http: HttpClient) { }

public setAdminUser(status: string): void{
  if(status =='ADMIN'){
    this.isAdminUser=true;
  }else{
    this.isAdminUser=false;
  }
  
}
  
  public setLoginStatus(status: boolean):void{
    this.isLoggedIn=status;
  }
   public setUserId(userId: string): void {
      this.userId = userId;
    }
  
   public getUserId(): string {
      return this.userId;
   }
   public setUserEmail(userEmail: string): void {
    this.userEmail = userEmail;
  }

 public getUserEmail(): string {
    return this.userEmail;
 }
//    return this.http.get<any[]>("http://localhost:8080/cart-view/" + custId);

 public   userDetails(userId: string) {
  const url ='http://localhost:8080/user/';
  return this.http.get<any[]>(url+userId);
}

  public userLogin(request: any) {
    const url = 'http://localhost:8080/login';
    return this.http.post(url, request);
  }
  public logout(email :string){
   const url = 'http://localhost:8080/logout';
   return this.http.post(url,{email}); 
  }
  public userRegister(request:any){
    const url = 'http://localhost:8080/register';
    return this.http.post(url, request);
  }
}
