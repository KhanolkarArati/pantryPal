import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductService } from './product.service';

describe('ProductService', () => {
  let service: ProductService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule], // Import HttpClientTestingModule to mock HTTP requests
      providers: [ProductService] // Provide the ProductService
    });
    service = TestBed.inject(ProductService);
    httpMock = TestBed.inject(HttpTestingController); // Inject HttpTestingController to mock requests
  });

  afterEach(() => {
    httpMock.verify(); // Verify that no unmatched requests are made
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // Test getAllProducts
  it('should fetch all products', () => {
    const mockProducts = [{ id: 1, name: 'Product1' }, { id: 2, name: 'Product2' }];
    
    service.getAllProducts().subscribe((products) => {
      expect(products.length).toBe(2);
      expect(products).toEqual(mockProducts);
    });

    const req = httpMock.expectOne('http://localhost:8080/products');
    expect(req.request.method).toBe('GET');
    req.flush(mockProducts); // Return mock products
  });

  // Test addToCart
  it('should add item to cart', () => {
    const user_id = 1;
    const product_id = 101;
    const quantity = 2;

    service.addToCart(user_id, product_id, quantity).subscribe((response) => {
      expect(response).toBe('success');
    });

    const req = httpMock.expectOne((req) => 
      req.url === `http://localhost:8080/cart/${user_id}/add/${product_id}` &&
      req.params.get('quantity') === '2'
    );
    expect(req.request.method).toBe('POST');
    req.flush('success');
  });

  // Test updateProduct
  it('should update product details', () => {
    const updateData = { productName: 'Updated Product', productQuantity: 5 };
    const mockResponse = 'success';
  
    service.updateProduct(updateData).subscribe((response) => {
      expect(response).toBe(mockResponse);
    });
  
    // Modify the test to match the URL with query parameters
    const req = httpMock.expectOne((req) =>
      req.url === 'http://localhost:8080/products/update' &&
      req.params.get('productName') === 'Updated Product' &&
      req.params.get('productQuantity') === '5'
    );
    
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  // Test deleteProduct
  it('should delete a product', () => {
    const productId = 123;

    service.deleteProduct(productId).subscribe((response) => {
      expect(response).toBe('success');
    });

    const req = httpMock.expectOne(`http://localhost:8080/products/remove?productId=${productId}`);
    expect(req.request.method).toBe('POST');
    req.flush('success');
  });

  // Test getCartItemsByCustId
  it('should fetch cart items by customer ID', () => {
    const custId = 1;
    const mockCartItems = [{ id: 1, productName: 'Product1' }, { id: 2, productName: 'Product2' }];

    service.getCartItemsByCustId(custId).subscribe((cartItems) => {
      expect(cartItems.length).toBe(2);
      expect(cartItems).toEqual(mockCartItems);
    });

    const req = httpMock.expectOne(`http://localhost:8080/cart-view/${custId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockCartItems);
  });

  // Test removeItemfromcart
  it('should remove item from cart', () => {
    const user_id = 1;
    const product_id = 101;
  
    // Expected request in your service
    service.removeItemfromcart(product_id, user_id).subscribe((response) => {
      expect(response).toBe('success');
    });
  
    const req = httpMock.expectOne((req) => 
      req.url === `http://localhost:8080/cart/${user_id}/remove/${product_id}` &&
      req.params.get('quantity') === '99999'  // Adjust according to the actual quantity logic in your service
    );
    expect(req.request.method).toBe('POST');
    req.flush('success');
  });

  // Test makeSale
  it('should create a sale', () => {
    const saleData = { productId: 101, quantity: 2 };
    const mockResponse = { success: true };

    service.makeSale(saleData).subscribe((response) => {
      expect(response.success).toBeTrue();
    });

    const req = httpMock.expectOne('http://localhost:8080/order-history/create');
    expect(req.request.method).toBe('POST');
    expect(req.request.body.productId).toBe(101);
    expect(req.request.body.quantity).toBe(2);
    req.flush(mockResponse);
  });
});
