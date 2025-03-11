import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OrdersComponent } from './orders.component';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('OrdersComponent', () => {
  let component: OrdersComponent;
  let fixture: ComponentFixture<OrdersComponent>;
  let productService: jasmine.SpyObj<ProductService>;
  let userService: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    const productServiceSpy = jasmine.createSpyObj('ProductService', ['getOrdersbyId']);
    const userServiceSpy = jasmine.createSpyObj('UserService', ['getUserId']);

    await TestBed.configureTestingModule({
      declarations: [OrdersComponent], // No need to declare MenuComponent explicitly
      imports: [HttpClientTestingModule],
      providers: [
        { provide: ProductService, useValue: productServiceSpy },
        { provide: UserService, useValue: userServiceSpy }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA] // Ignores unknown elements like <app-menu>
    }).compileComponents();

    productService = TestBed.inject(ProductService) as jasmine.SpyObj<ProductService>;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OrdersComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set userId and call loadOrders on init', () => {
    userService.getUserId.and.returnValue('123');
    productService.getOrdersbyId.and.returnValue(of([{ orderId: 'O001', orderPlacedDate: '2024-03-10' }]));

    fixture.detectChanges();

    expect(component.userId).toBe('123');
    expect(productService.getOrdersbyId).toHaveBeenCalledWith('123');
    expect(component.orders.length).toBe(1);
    expect(component.subTotal).toBe(1);
  });

  it('should calculate subTotal correctly', () => {
    productService.getOrdersbyId.and.returnValue(of([
      { orderId: 'O001', orderPlacedDate: '2024-03-10' },
      { orderId: 'O002', orderPlacedDate: '2024-03-11' }
    ]));

    component.loadOrders();

    expect(component.orders.length).toBe(2);
    expect(component.subTotal).toBe(2);
  });
});
