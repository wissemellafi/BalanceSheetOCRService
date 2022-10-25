import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { h, UserConfig } from 'gridjs';
import { UserResponse } from 'src/app/models/user-response.model';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css'],
})
export class ListUsersComponent implements OnInit {
  private baseUrl = environment.apiBaseUrl;

  gridConfig: UserConfig = {
    columns: [
      "Nom d'utilisateur",
      'Prénom',
      'Nom',
      'Role',
      'Date de création',
      'Etat',
      {
        name: 'Actions',
        formatter: (_, row) =>
          h(
            'button',
            {
              class: 'btn btn-primary btn-sm',
              onClick: () =>
                this.router.navigate(['user', 'edit', row.cells[6].data]),
            },
            'Modifier'
          ),
      },
    ],
    sort: true,
    search: true,
    pagination: {
      enabled: true,
      limit: 10,
      server: {
        url: (prev, page, limit) => `${prev}?page=${page}&size=${limit}`,
      },
    },
    server: {
      url: `${this.baseUrl}/users`,
      headers: { Authorization: this.authService.token },
      then: (res) => {
        return res.data.map((user: UserResponse) => {
          return [
            user.username,
            user.firstName,
            user.lastName,
            user.role,
            user.createdAt,
            user.enabled ? 'Activé' : 'Désactivé',
            user.id,
          ];
        });
      },
      total: (res) => res.total,
    },
  };

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {}
}
