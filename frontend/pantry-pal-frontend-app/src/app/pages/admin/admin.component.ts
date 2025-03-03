import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { ProductService } from 'src/app/services/product.service';
import { MenuComponent } from 'src/app/pages/menu/menu.component';
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {
  products: any[] = []; // Assuming product data structure
  modalVisible: boolean = false;
  showModal: boolean = false;
  updatedProduct: any = {}; // Object to store updated product data
  constructor(private productService: ProductService,private http: HttpClient) { }
  

  //
  name: string = '';
  qty: number = 0;
  exp: string = '';
  url: string = '';
  ///

  
  ngOnInit(): void {
    // Fetch products from the API using ProductService
   this.fetchProducts();
  }

  fetchProducts():void{
    this.productService.getAllProducts().subscribe((data: any) => {
      console.log('Fetched Products:', data); // Check if products are fetched
      this.products = data; // Assuming the API response contains a list of products
    });
  }
  onAddProduct() {
    const newProductData = {
      productName: '',
      productQuantity: null,
      productExpiryDate: '',
      productImageURL: ''
    };

    this.productService.addProduct(newProductData)
      .subscribe(
        () => {
         
          this.fetchProducts(); // Fetch updated products after successful addition
        },
        error => {
          // Handle errors
        }
      );
  }

  openUpdateProductDialog(product: any): void {
    // Open the modal
    console.log('Opening update modal'); // Log to check if this method is being triggered

    this.showModal = true;
    // Assign the selected product to updatedProduct for updating
    this.updatedProduct = { ...product };
    console.log(this.updatedProduct.productName);
console.log(this.updatedProduct.productQuantity);
  }
  closeModal(): void {
    // Close the modal without updating the product
    this.showModal = false;
  }
  updateProduct(): void {
    // Update product using API
    const updateData = {
      productId: this.updatedProduct.productId,
      productName: this.updatedProduct.productName,
      productQuantity: this.updatedProduct.productQuantity,
      productExpiryDate: this.updatedProduct.productExpiryDate,
      productImageURL:this.updatedProduct.productImageURL
      
    };


    // Make HTTP PUT request to update the product
    this.productService.updateProduct(updateData)
      .subscribe(
        response => {
          
          console.log('Product updated successfully:', response);
          alert(response);
          // Update the local products list or perform additional actions after successful update if needed
          this.fetchProducts(); // Refresh product list after updating
        },
        error => {
          console.error('Error updating product:', error);
          // Handle error cases
        }
      );

    // Close the modal after attempting to update the product
    this.showModal = false;
  }

  deleteProduct(productId: any): void {
    this.productService.deleteProduct(productId)
      .subscribe(
        (response) => {
          if(response =='Product removed successfully...!!!'){
            // alert(response.message);
          console.log('Product deleted successfully', response);
alert('Product removed !');
this.fetchProducts();
          }else{
            alert(response.message);
          }
          // Handle success, if needed
        },
        (error) => {
          console.error('Error deleting product', error);
          // Handle error, if needed
        }
      );
}

  

  // add new product

  openAddProductModal(): void {
    this.modalVisible = true;
  }

  closeAddProductModal(): void {
    this.modalVisible = false;
  }

  addProduct(): void {
    const productData = {
      productName: this.name,
      productQuantity: this.qty,
      productExpiryDate: this.exp,
      productImageURL: this.url
    };

    this.productService.addProduct(productData).subscribe(
      (response) => {
        // Handle success response if needed
        console.log('Product added:', response);
        alert(response);
        this.closeModal();
        this.fetchProducts(); // Refresh product list after updating

      },
      (error) => {
        // Handle error response if needed
        console.error('Error adding product:', error);
      }
    );
  }
  // 
}