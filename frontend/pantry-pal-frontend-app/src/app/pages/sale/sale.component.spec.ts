import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SaleComponent } from './sale.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ProductService } from '../../services/product.service'; 
import { MenuComponent } from '../menu/menu.component'; 
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

describe('SaleComponent', () => {
  let component: SaleComponent;
  let fixture: ComponentFixture<SaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SaleComponent, MenuComponent],
      imports: [HttpClientTestingModule], 
      providers: [ProductService], 
      schemas: [CUSTOM_ELEMENTS_SCHEMA] 
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
