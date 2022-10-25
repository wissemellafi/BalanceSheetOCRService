import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isLoading = false;
  isError = false;
  errorMsg = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  onSubmit(form: NgForm) {
    this.isError = false;
    this.isLoading = true;
    if (form.valid) {
      this.authService
        .login({
          username: form.value.username,
          password: form.value.password,
        })
        .subscribe({
          next: (_) => {
            this.isLoading = false;
            this.router.navigate(['/']);
          },
          error: (errorMsg) => {
            this.isLoading = false;
            this.isError = true;
            this.errorMsg = errorMsg;
          },
        });
    }

    form.reset();
  }
}
