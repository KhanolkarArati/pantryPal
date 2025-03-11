import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AdminComponent } from './admin.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ProductService } from 'src/app/services/product.service';
import { FormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;
  let productService: jasmine.SpyObj<ProductService>;

  beforeEach(async () => {
    const productServiceMock = jasmine.createSpyObj('ProductService', [
      'getAllProducts',
      'deleteProduct'
    ]);

    await TestBed.configureTestingModule({
      declarations: [AdminComponent],
      imports: [HttpClientTestingModule, FormsModule], // Mock HTTP and FormsModule
      providers: [{ provide: ProductService, useValue: productServiceMock }], // Provide mock service
      schemas: [CUSTOM_ELEMENTS_SCHEMA] // Handle unknown elements like <app-menu>
    }).compileComponents();

    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    productService = TestBed.inject(ProductService) as jasmine.SpyObj<ProductService>;

    // Mock getAllProducts to return an empty array initially
    productService.getAllProducts.and.returnValue(of([]));
    fixture.detectChanges(); // Trigger component lifecycle
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch products on init', () => {
    const mockProducts = [
      { productId: 1, productName: 'Product A', productQuantity: 10, productExpiryDate: '2025-12-31' }
    ];
    productService.getAllProducts.and.returnValue(of(mockProducts));

    component.ngOnInit();
    expect(component.products).toEqual(mockProducts);
    expect(productService.getAllProducts).toHaveBeenCalled();
  });

  it('should open the update product dialog', () => {
    const mockProduct = { productId: 1, productName: 'Test Product', productQuantity: 5, productExpiryDate: '2025-01-01' };
    component.openUpdateProductDialog(mockProduct);

    expect(component.showModal).toBeTrue();
    expect(component.updatedProduct).toEqual(mockProduct);
  });

  it('should close the update modal', () => {
    component.showModal = true;
    component.closeModal();
    expect(component.showModal).toBeFalse();
  });

  it('should open the add product modal', () => {
    component.openAddProductModal();
    expect(component.modalVisible).toBeTrue();
  });

  it('should close the add product modal', () => {
    component.modalVisible = true;
    component.closeAddProductModal();
    expect(component.modalVisible).toBeFalse();
  });

  it('should delete a product and refresh the product list', () => {
    productService.deleteProduct.and.returnValue(of('Product removed successfully...!!!'));
    spyOn(component, 'fetchProducts');

    component.deleteProduct(1);

    expect(productService.deleteProduct).toHaveBeenCalledWith(1);
    expect(component.fetchProducts).toHaveBeenCalled();
  });
});
