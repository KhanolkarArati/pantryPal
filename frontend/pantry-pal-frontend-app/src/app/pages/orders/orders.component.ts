import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { MenuComponent } from 'src/app/pages/menu/menu.component'; // Adjust the path as needed

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  userId: any;
  orders: any[] = [];
  subTotal: number = 0;

  constructor(private productService: ProductService, private userService:UserService) {
    debugger;
  }
  ngOnInit(): void {
    this.userId = this.userService.getUserId();
    console.log('User ID in Sale Component:', this.userId);
    this.loadOrders();

  }

  loadOrders() {
    this.subTotal = 0;
    this.productService.getOrdersbyId(this.userId).subscribe((res: any) => {
      this.orders = res;
      this.orders.forEach(element => {
         this.subTotal =  this.subTotal + 1;});
        console.log(this.orders);
      debugger;
    })
  }

}

