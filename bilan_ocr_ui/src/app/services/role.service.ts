import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Role } from '../models/role.model';

@Injectable({
  providedIn: 'root',
})
export class RoleService {
  private url = 'roles';

  constructor(private http: HttpClient) {}

  getRoles() {
    return this.http.get<Role[]>(this.url);
  }
}
