import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { SaleComponent } from './pages/sale/sale.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AuthGuard } from './guards/auth.guard'; // Import the AuthGuard
import { OrdersComponent } from './pages/orders/orders.component';

const routes: Routes = [
  {
    path: "",
    component: HomeComponent ,canActivate:[AuthGuard]
  },
  {
    path:'login',
    component:LoginComponent
  },
  {
    path:'register',
    component:RegisterComponent 
  },
  {
    path: 'products',
    component: HomeComponent ,canActivate:[AuthGuard]
  }, 
 
  {
    path: "sale",
    component: SaleComponent ,canActivate:[AuthGuard]
  },
  {
    path: "admin",
    component: AdminComponent ,canActivate:[AuthGuard]
  },
  {
    path: "orders",
    component:OrdersComponent, canActivate:[AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
