import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { BilanDetailsComponent } from './bilan/bilan-details/bilan-details.component';
import { BilanOcrUploadComponent } from './bilan/bilan-ocr-upload/bilan-ocr-upload.component';
import { ListBilansComponent } from './bilan/list-bilans/list-bilans.component';
import { AuthGuardService } from './services/auth-guard.service';
import { UserAuthGuardService } from './services/user-auth-guard.service';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { NewUserComponent } from './user/new-user/new-user.component';

const routes: Routes = [
  { path: '', redirectTo: 'bilan/list', pathMatch: 'full' },
  {
    path: 'bilan',
    canActivate: [AuthGuardService],
    children: [
      { path: 'ocr', component: BilanOcrUploadComponent },
      { path: 'list', component: ListBilansComponent },
      { path: 'details/:matricule', component: BilanDetailsComponent },
    ],
  },
  {
    path: 'user',
    canActivate: [AuthGuardService, UserAuthGuardService],
    children: [
      { path: 'list', component: ListUsersComponent },
      { path: 'edit/:id', component: EditUserComponent },
      { path: 'new', component: NewUserComponent }
    ],
  },
  { path: 'auth', children: [{ path: 'login', component: LoginComponent }] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
