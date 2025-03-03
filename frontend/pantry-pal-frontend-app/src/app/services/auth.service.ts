import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isLoggedIn: boolean = false; // Default to not logged in

  // Method to update login status
  setLoginStatus(status: boolean): void {
    this.isLoggedIn = status;
  }
}
