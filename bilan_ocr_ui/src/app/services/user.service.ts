import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserDto } from '../models/user-dto.model';
import { UserRequest } from '../models/user-request.model';
import { UserResponse } from '../models/user-response.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private url = 'users';

  constructor(private http: HttpClient) {}

  getUser(id: number) {
    return this.http.get<UserResponse>(`${this.url}/${id}`);
  }

  postUser(user: UserRequest) {
    return this.http.post<UserResponse>(this.url, user);
  }

  updateUser(id: number, user: UserDto) {
    return this.http.put<UserResponse>(`${this.url}/${id}`, user);
  }

  deleteUser(id: number) {
    return this.http.delete(`${this.url}/${id}`);
  }

  changePassword(id: number, password: string) {
    return this.http.put<UserResponse>(
      `${this.url}/change-password/${id}`,
      password
    );
  }
}
