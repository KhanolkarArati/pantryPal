import { Component, OnInit } from '@angular/core';
import { ProductService } from './services/product.service';
import { NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  {
  title = 'PantryPal';
 }
