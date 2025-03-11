import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MenuComponent } from './menu.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ObservorService } from 'src/app/services/observor.service';
import { ProductService } from 'src/app/services/product.service';
import { UserService } from 'src/app/services/user.service';
import { of } from 'rxjs';

describe('MenuComponent', () => {
  let component: MenuComponent;
  let fixture: ComponentFixture<MenuComponent>;
  let userServiceMock: any;
  let productServiceMock: any;
  let observorServiceMock: any;

  beforeEach(async () => {
    userServiceMock = {
      isLoggedIn: true,
      isAdminUser: false,
      getUserId: jasmine.createSpy('getUserId').and.returnValue(1),
      getUserEmail: jasmine.createSpy('getUserEmail').and.returnValue('test@example.com'),
      logout: jasmine.createSpy('logout').and.returnValue(of({ message: 'Logged out' })),
      setLoginStatus: jasmine.createSpy('setLoginStatus'),
      setAdminUser: jasmine.createSpy('setAdminUser'),
      setUserEmail: jasmine.createSpy('setUserEmail')
    };

    productServiceMock = {
      cartAddedSubject: of([]),
      getCartItemsByCustId: jasmine.createSpy('getCartItemsByCustId').and.returnValue(of([]))
    };

    observorServiceMock = {
      emit: jasmine.createSpy('emit')
    };

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule],
      declarations: [MenuComponent],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: ProductService, useValue: productServiceMock },
        { provide: ObservorService, useValue: observorServiceMock }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the MenuComponent', () => {
    expect(component).toBeTruthy();
  });

  it('should check login status correctly', () => {
    expect(component.checkLoginStatus()).toBeTrue();
  });

  it('should check admin user status correctly', () => {
    expect(component.checkUserStatus()).toBeFalse();
  });

  it('should call logout and reset user state', () => {
    component.logout();
    expect(userServiceMock.logout).toHaveBeenCalledWith('test@example.com');
    expect(userServiceMock.setLoginStatus).toHaveBeenCalledWith(false);
    expect(userServiceMock.setAdminUser).toHaveBeenCalledWith('');
    expect(userServiceMock.setUserEmail).toHaveBeenCalledWith('');
  });

  it('should load cart items and update total', () => {
    productServiceMock.getCartItemsByCustId.and.returnValue(of([{ productQuantity: 2 }, { productQuantity: 3 }]));
    component.loadCart();
    expect(productServiceMock.getCartItemsByCustId).toHaveBeenCalledWith(1);
  });
});
