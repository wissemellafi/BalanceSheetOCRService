import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { exhaustMap, Observable, take } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class ApiInterceptorService implements HttpInterceptor {
  private apiBaseUrl = environment.apiBaseUrl;
  private baseUrl = environment.baseUrl;

  constructor(private authService: AuthService) {}
  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    let httpReq: HttpRequest<any>;

    if (req.url === 'login') {
      httpReq = req.clone({ url: `${this.baseUrl}/${req.url}` });
    } else {
      httpReq = req.clone({ url: `${this.apiBaseUrl}/${req.url}` });
    }

    return this.authService.user.pipe(
      take(1),
      exhaustMap((user) => {
        const token = user?.token ?? '';
        const newReq = httpReq.clone({
          headers: new HttpHeaders().append('Authorization', token),
        });
        return next.handle(newReq);
      })
    );
  }
}
