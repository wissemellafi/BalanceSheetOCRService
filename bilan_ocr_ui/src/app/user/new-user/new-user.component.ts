import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role.model';
import { UserRequest } from 'src/app/models/user-request.model';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-new-user',
  templateUrl: './new-user.component.html',
  styleUrls: ['./new-user.component.css'],
})
export class NewUserComponent implements OnInit {
  roles: Role[] = [];
  selectedRole = '';
  isError = false;
  errorMsg = '';

  constructor(
    private roleService: RoleService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchRoles();
  }

  private fetchRoles() {
    this.roleService.getRoles().subscribe((roles) => {
      this.roles = roles;
      this.selectedRole = roles[0].role;
    });
  }

  onSubmit(f: NgForm) {
    this.isError = false;
    if (f.valid) {
      const user: UserRequest = {
        username: f.value.username,
        firstName: f.value.firstName,
        lastName: f.value.lastName,
        role: f.value.role,
        password: f.value.password,
        enabled: f.value.enabled,
      };

      this.userService.postUser(user).subscribe({
        next: (_) => {
          this.router.navigate(['user', 'list']);
        },
        error: (error: HttpErrorResponse) => {
          this.isError = true;
          this.errorMsg = error.error.message;
        }
      });
    }
  }
}
