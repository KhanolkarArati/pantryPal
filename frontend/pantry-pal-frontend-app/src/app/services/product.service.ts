import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  getOrdersbyId(userId: any) {
    return this.http.get<any>("http://localhost:8080/order-history/user/"+userId);
  }

  public cartAddedSubject = new Subject<boolean>();

  constructor(private http: HttpClient) { }

  getAllProducts(): Observable<any[]> {
    debugger;
    return this.http.get<any[]>("http://localhost:8080/products");
  }

  addToCart(user_id: any, product_id:any,quantity:any) : Observable<any> {
    debugger;
    const body = {
      quantity: quantity.toString() // Convert quantity to string
    };
    const params = new HttpParams().set('quantity', quantity.toString());

    return this.http.post<string>("http://localhost:8080/cart/" + user_id + "/add/" + product_id , body,{params, responseType: 'text' as 'json' });
  }

  updateProduct(updateData: any) {
     let params = new HttpParams();
    Object.keys(updateData).forEach(key => {
      params = params.append(key, updateData[key]);
    });

    return this.http.post<any>('http://localhost:8080/products/update',{}, { params, responseType: 'text' as 'json' });
  }
  
  addProduct(productData: any) {
    const params = new HttpParams()
    .set('productName', productData.productName)
    .set('productQuantity', productData.productQuantity)
    .set('productExpiryDate', productData.productExpiryDate)
    .set('productImageURL', productData.productImageURL || '');


    // Assuming your API endpoint accepts POST requests with the provided data
    return this.http.post<any>('http://localhost:8080/products/add', {},{params,responseType:'text' as 'json'});
  }

  deleteProduct(productId: any): Observable<any> {

    return this.http.post(`http://localhost:8080/products/remove?productId=${productId}`, null,{responseType:'text' as 'json'});
  }

  getCartItemsByCustId(custId: any) : Observable<any[]>  {
    return this.http.get<any[]>("http://localhost:8080/cart-view/" + custId);
  }
   removeItemfromcart(product_id:any, user_id:any) : Observable<any[]>  {
    const params = new HttpParams().set('quantity', 99999);
    return this.http.post<any>('http://localhost:8080/cart/'+user_id+'/remove/' + product_id,{},{params,responseType:'text' as 'json'});
  }



  makeSale(obj: any) : Observable<any> {
    debugger;
    return this.http.post<any>("http://localhost:8080/order-history/create",obj);
  }
}
