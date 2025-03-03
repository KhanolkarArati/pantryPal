import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { MenuComponent } from '../menu/menu.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css']
})
export class SaleComponent implements OnInit {
  userId: any;
  cartProducts: any[] = [];
  subTotal: number = 0;
  totalitems: number = 0;
  itemsForOrder :any;
  items: { [key: number]: number } = {}; // Example dynamic values
  orderPlacedDate: string = '2023-12-01T10:30:00';

 aggregateditems :any ={};
  constructor(private productService: ProductService, private userService:UserService, private router:Router) {
    debugger;
  }
  ngOnInit(): void {
    this.userId = this.userService.getUserId();
    console.log('User ID in Sale Component:', this.userId);
    this.loadCart();

  }

  loadCart() {
    this.subTotal = 0;
    this.totalitems = 0;
    this.items ={};
    this.itemsForOrder ={};
    this.productService.getCartItemsByCustId(this.userId).subscribe((res: any) => {
      this.cartProducts = res;
      console.log(this.cartProducts.length);
      this.totalitems = Object.keys(res).length;
      this.cartProducts.forEach(element => {

        this.subTotal =  this.subTotal + element.productQuantity;});
       
       this.cartProducts.forEach(element => {
        this.itemsForOrder[element.productID] = element.productQuantity;
        });
        console.log("items"+this.itemsForOrder);
      debugger;
    })
  }
  RemoveItem(id: any) {
    this.productService.removeItemfromcart(id,this.userId).subscribe((res: any) => {
      if (res) {
          // const jsonResponse = JSON.parse(res);
          console.log(res);
          if(res && res.includes('Product removed from the cart successfully...!!!')) {
            alert("Product Removed From Cart");
            this.loadCart();
            this.productService.cartAddedSubject.next(true);
           }
          
        //Product removed from the cart successfully...!!!

      }
    })
  }
  makeSale() {
    const orderobject =  {
      "userId": this.userId,
      "items" : this.itemsForOrder,
      "orderPlacedDate" : this.orderPlacedDate
  };


    this.productService.cartAddedSubject.next(true);
    this.productService.makeSale(orderobject).subscribe((res: any) => {
      console.log(res);
        alert(res.message);
        this.loadCart();
        this.productService.cartAddedSubject.next(true);
        this.router.navigate(["orders"]);

    })
  }
}
