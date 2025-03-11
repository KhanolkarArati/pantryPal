import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { ObservorService } from 'src/app/services/observor.service';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let fixture: ComponentFixture<HomeComponent>;
  let productService: jasmine.SpyObj<ProductService>;
  let userService: jasmine.SpyObj<UserService>;
  let observerService: jasmine.SpyObj<ObservorService>;

  beforeEach(async () => {
    const productSpy = jasmine.createSpyObj('ProductService', ['getAllProducts', 'addToCart']);
    const userSpy = jasmine.createSpyObj('UserService', ['isAdminUser', 'getUserId', 'userDetails']);
    const observerSpy = jasmine.createSpyObj('ObservorService', ['on']);

    await TestBed.configureTestingModule({
      declarations: [HomeComponent],
      imports: [HttpClientTestingModule], 
      providers: [
        { provide: ProductService, useValue: productSpy },
        { provide: UserService, useValue: userSpy },
        { provide: ObservorService, useValue: observerSpy }
      ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA] // To ignore <app-menu> error
    }).compileComponents();

    productService = TestBed.inject(ProductService) as jasmine.SpyObj<ProductService>;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    observerService = TestBed.inject(ObservorService) as jasmine.SpyObj<ObservorService>;

    productService.getAllProducts.and.returnValue(of([])); 
    userService.getUserId.and.returnValue('123');
    userService.userDetails.and.returnValue(of({ user: { id: '123' } } as any)); // FIXED: Added `as any` to match expected return type
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the HomeComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should load all products on init', () => {
    expect(productService.getAllProducts).toHaveBeenCalled();
  });

  it('should get user details on init', () => {
    expect(userService.userDetails).toHaveBeenCalledWith('123');
  });
});
