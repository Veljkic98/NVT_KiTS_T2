import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from '../services/auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(
    public auth: AuthService,
    public router: Router
  ) { }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const expectedRoles: string = route.data.expectedRoles;
    const token = localStorage.getItem('user');

    if (!token) {
      this.router.navigate(['/login']);
      return false;
    }

    const role = JSON.parse(token).role + "";

    const roles: string[] = expectedRoles.split('|');
    
    if (roles.indexOf(role) === -1) {
      this.router.navigate(['']);
      return false;
    }
    return true;
  }

}
