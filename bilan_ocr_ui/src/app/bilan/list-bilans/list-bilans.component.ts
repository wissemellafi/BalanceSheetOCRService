import { Component, OnInit } from '@angular/core';

import { h, UserConfig } from 'gridjs';
import { environment } from 'src/environments/environment';
import { BilanResponse } from 'src/app/models/bilan-response.model';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-list-bilans',
  templateUrl: './list-bilans.component.html',
  styleUrls: ['./list-bilans.component.css'],
})
export class ListBilansComponent implements OnInit {
  private baseUrl = environment.apiBaseUrl;

  public gridConfig: UserConfig = {
    columns: [
      'Matricule',
      'RS',
      'Année',
      'Etat',
      'Publisher',
      'Created At',
      {
        name: 'Actions',
        formatter: (_, row) =>
          h(
            'button',
            {
              class: 'btn btn-primary btn-sm',
              onClick: () =>
                this.router.navigate(['bilan', 'details', row.cells[6].data]),
            },
            'Afficher plus'
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
      url: `${this.baseUrl}/bilans`,
      headers: { Authorization: this.authService.token },
      then: (res) =>
        res.data.map((bilan: BilanResponse) => {
          return [
            bilan.matricule,
            bilan.rs,
            bilan.year,
            bilan.etat,
            bilan.publisher,
            bilan.createdAt,
            bilan.matricule,
          ];
        }),
      total: (res) => res.total,
    },
  };

  constructor(private router: Router, private authService: AuthService) {}

  ngOnInit(): void {}
}
