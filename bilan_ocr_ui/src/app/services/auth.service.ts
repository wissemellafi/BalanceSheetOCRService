import {
  HttpClient,
  HttpErrorResponse,
  HttpStatusCode,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, tap, throwError } from 'rxjs';
import { AuthUser } from '../models/auth-user.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public user = new BehaviorSubject<AuthUser | null>(null);
  public token: string = '';
  private tokenExpirationTimer: any;

  constructor(private http: HttpClient, private router: Router) {}

  login(user: { username: string; password: string }) {
    const userData = new FormData();
    userData.append('username', user.username);
    userData.append('password', user.password);

    return this.http.post<AuthResponseData>('login', userData).pipe(
      catchError(this.handleError),
      tap((resData) => this.handleAuthentication(resData))
    );
  }

  autoLogin() {
    const jsonUser = localStorage.getItem('user');

    if (jsonUser) {
      const userData: {
        username: string;
        role: string;
        _token: string;
        _tokenExpirationDate: string;
      } = JSON.parse(jsonUser);

      const user = new AuthUser(
        userData.username,
        userData.role,
        userData._token,
        new Date(userData._tokenExpirationDate)
      );

      if (user.token) {
        this.user.next(user);
        this.token = user.token;
        const expirationDuration =
          new Date(userData._tokenExpirationDate).getTime() -
          new Date().getTime();

        this.autoLogout(expirationDuration);
      }
    }

    return;
  }

  logout() {
    this.user.next(null);
    this.token = '';
    this.router.navigate(['auth', 'login']);
    localStorage.removeItem('user');
    this.clearTokenTimer();
  }

  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(
      () => this.logout(),
      expirationDuration
    );
  }

  private handleAuthentication(authResData: AuthResponseData) {
    const expirationDate = new Date(
      new Date().getTime() + authResData.expiresIn * 1000
    );

    const user = new AuthUser(
      authResData.username,
      authResData.role,
      authResData.token,
      expirationDate
    );

    this.user.next(user);
    this.token = user.token ?? '';
    this.autoLogout(authResData.expiresIn * 1000);

    localStorage.setItem('user', JSON.stringify(user));
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = "Une erreur s'est produite";

    if (errorRes.status === HttpStatusCode.Unauthorized) {
      errorMessage = 'Identifiants invalides';
    }

    return throwError(() => errorMessage);
  }

  private clearTokenTimer() {
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
  }
}

interface AuthResponseData {
  username: string;
  role: string;
  token: string;
  expiresIn: number;
}
