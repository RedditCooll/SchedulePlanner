import { BrowserModule } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from './Common/shared.module';
import { HotTableModule } from '@handsontable/angular';

import { AppComponent } from './app.component';
import { SpreadsheetComponent } from './Common/spreadsheet/spreadsheet.component';
import { LoginComponent } from './LoginPage/login/login.component';
import { LogoutComponent } from './LoginPage/logout/logout.component';
import { RegisterComponent } from './LoginPage/register/register.component';

import { HttpInterceptorService } from './LoginPage/httpInterceptor.service';
import { IconsProviderModule } from './icons-provider.module';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { zh_TW } from 'ng-zorro-antd/i18n';
import zh from '@angular/common/locales/zh';

registerLocaleData(zh);


@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NzIconModule,
    SharedModule,
    IconsProviderModule,
    HotTableModule.forRoot()
  ],
  declarations: [
    AppComponent,
    SpreadsheetComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent
  ],
  exports: [
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true
    },
    { provide: NZ_I18N, useValue: zh_TW }
  ],
  bootstrap: [
    AppComponent
  ]})
export class AppModule { }
