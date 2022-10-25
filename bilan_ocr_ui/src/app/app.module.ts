import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './header/header.component';
import { BilanOcrUploadComponent } from './bilan/bilan-ocr-upload/bilan-ocr-upload.component';
import { ListBilansComponent } from './bilan/list-bilans/list-bilans.component';
import { LoginComponent } from './auth/login/login.component';
import { BilanDetailsComponent } from './bilan/bilan-details/bilan-details.component';
import { FormsModule } from '@angular/forms';
import { ApiInterceptorService } from './services/api-interceptor.service';

import { GridJsAngularModule } from 'gridjs-angular';
import { ListUsersComponent } from './user/list-users/list-users.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { NewUserComponent } from './user/new-user/new-user.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BilanOcrUploadComponent,
    ListBilansComponent,
    LoginComponent,
    BilanDetailsComponent,
    ListUsersComponent,
    EditUserComponent,
    NewUserComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    GridJsAngularModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
