import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from 'src/app/models/role.model';
import { UserDto } from 'src/app/models/user-dto.model';
import { UserResponse } from 'src/app/models/user-response.model';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css'],
})
export class EditUserComponent implements OnInit {
  user?: UserResponse;
  roles: Role[] = [];
  passwordChanged = false;
  isError = false;
  isFetchUserError = false;
  errorMsg = '';

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private roleService: RoleService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchUser();
    this.fetchRoles();
  }

  private fetchUser() {
    const id = +this.route.snapshot.params['id'];

    if (id) {
      this.userService.getUser(id).subscribe({
        next: (user) => {
          this.user = user;
        },
        error: (error: HttpErrorResponse) => {
          this.isFetchUserError = true;
          this.errorMsg = error.error.message;
        }
      });
    }
  }

  private fetchRoles() {
    this.roleService.getRoles().subscribe((roles) => {
      this.roles = roles;
    });
  }

  onProfileSubmit(form: NgForm) {
    this.isError = false;
    if (form.valid && this.user) {
      const user: UserDto = {
        username: form.value.username,
        firstName: form.value.firstName,
        lastName: form.value.lastName,
        role: form.value.role,
        enabled: form.value.enabled,
      };

      this.userService.updateUser(this.user.id, user).subscribe({
        next: (_) => {
          this.router.navigate(['user', 'list']);
        },
        error: (error: HttpErrorResponse) => {
          this.isError = true;
          this.errorMsg = error.error.message;
        },
      });
    }
  }

  onPasswordSubmit(form: NgForm) {
    this.passwordChanged = false;
    if (this.user && form.valid) {
      this.userService
        .changePassword(this.user.id, form.value.password)
        .subscribe((_) => {
          this.passwordChanged = true;
        });
    }

    form.reset();
  }
}
