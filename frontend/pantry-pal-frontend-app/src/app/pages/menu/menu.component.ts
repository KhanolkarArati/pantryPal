import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ObservorService } from 'src/app/services/observor.service';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit{
  cartProducts: any[] = [];
  subTotal: number = 0;
  showMenu:boolean=false;

  isLoggedIn: boolean = false; // Initialize with default value
  isAdminUser : boolean =false;

  constructor(private observorService: ObservorService, private userService: UserService,private productService: ProductService, private router: Router) {
  
  // router.events.forEach((event) => {
  //   if(event instanceof NavigationStart){
  //     this.showMenu = event.url !== "/login"
  //   }
  // });
    this.productService.cartAddedSubject.subscribe(res=> {
      debugger;
      this.loadCart();
    })
  }
  checkLoginStatus(): boolean {
    console.log("inside menu component"+this.userService.isLoggedIn);
    return this.userService.isLoggedIn;
    
  }

  checkUserStatus(): boolean{
    return this.userService.isAdminUser;
  }


  ngOnInit(): void {
    this.isLoggedIn = this.checkLoginStatus();
    this.isAdminUser = this.checkUserStatus();
    this.loadCart();
  }
  redirectToSale() {
    this.router.navigateByUrl("/sale");
  }

  logout(){
    console.log("inside logout");
    this.isLoggedIn=false;
    this.isAdminUser = false;
    this.userService.logout(this.userService.getUserEmail()).subscribe((res: any)=>{
      alert(res.message);
      console.log(res);
    });
    this.userService.setLoginStatus(false);
    this.userService.setAdminUser('');
    this.userService.setUserEmail('');

  }
  loadCart() {

    this.subTotal = 0;
    this.productService.getCartItemsByCustId(this.userService.getUserId()).subscribe((res: any)=> {
      this.cartProducts = res;
      console.log(this.cartProducts);
      this.cartProducts.forEach(element => {
          this.subTotal =  this.subTotal + element.productQuantity;
          this.sendquantity(this.subTotal);

      });
      debugger;
    })

  }
  sendquantity(totalitems :any) {
    this.observorService.emit('dataSent', totalitems);
  }
}


