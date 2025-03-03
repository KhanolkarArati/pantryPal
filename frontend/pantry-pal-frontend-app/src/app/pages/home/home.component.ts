import { HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { MenuComponent } from 'src/app/pages/menu/menu.component';
import { ObservorService } from 'src/app/services/observor.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  userId:string = '';
  productList: any [] = [];
  isAdminUser : boolean =false;
  cartObj : any = {
    
    "user_id": 1,
    "product_id": 1,
    "quantity": 1,
    "AddedDate": "2023-04-27T07:12:40.926Z"
  };
  userIDFinal: any;
   receivedData: any;
  constructor(private observerService:ObservorService ,private productService: ProductService,private userService:UserService) {

  }
  checkUserStatus(): boolean{
    return this.userService.isAdminUser;
  }

  ngOnInit(): void {
    debugger;
    this.isAdminUser = this.checkUserStatus();
    this.loadAllProducts();
    this.userId = this.userService.getUserId();
    console.log('User ID in HomeComponent:', this.userId);
    this.userDetails();

    this.observerService.on('dataSent', (data: any) => {
      this.receivedData = data;
      console.log('Received data:', data);
    });
  

  }
    

  

  loadAllProducts() {
    debugger;
    this.productService.getAllProducts().subscribe((result: any)=>{
      this.productList = result;
    })
  }



  userDetails() {

   this.userService.userDetails(this.userId).subscribe((result:any)=>{

    console.log("result of userdetails");
    console.log(result.user.id);
    this.userIDFinal = result.user.id;
//     this.userId= result.user.id;

   },err=>{
    alert("Something went wrong")
  })

        }

  // user_id: any, product_id
  addItemToCart(product_id: number,quantity:number) {
    debugger;
    this.cartObj.product_id = product_id;
    this.cartObj.user_id = this.userIDFinal;
    this.cartObj.quantity = quantity;
console.log(this.cartObj.user_id);
    const quantitystr = parseInt(this.cartObj.quantity, 10); // Parse quantity string to number

    // const body = {
    //   quantity: this.cartObj.quantity
    // };

    this.productService.addToCart(this.cartObj.user_id,this.cartObj.product_id,this.cartObj.quantity).subscribe((result: any)=>{
      try {
      const jsonResponse = JSON.parse(result);
      
      }catch(error){

        if(result && result.includes('Product added in the cart successfully...!!!')) {
          alert("Product Added To Cart");
          this.loadAllProducts();
          this.productService.cartAddedSubject.next(true);
         }
         

      }
    
    })
  }
}


