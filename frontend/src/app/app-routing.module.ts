import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SpreadsheetComponent } from './Common/spreadsheet/spreadsheet.component';
import { TextEditorComponent } from './Common/textEditor/textEditor.component';
import { ChartsComponent } from './Common/charts/charts.component';
import { LoginComponent } from './LoginPage/login/login.component';
import { LogoutComponent } from './LoginPage/logout/logout.component';
import { RegisterComponent } from './LoginPage/register/register.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/login' },
  { path: 'welcome', loadChildren: () => import('./HomePage/welcome/welcome.module').then(m => m.WelcomeModule) },
  { path: 'stuff', loadChildren: () => import('./HomePage/stuff/stuff.module').then(m => m.StuffModule) },
  { path: 'leisure', loadChildren: () => import('./HomePage/leisure/leisure.module').then(m => m.LeisureModule) },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'logout', component: LogoutComponent},
  { path: 'spreadsheet', component: SpreadsheetComponent},
  { path: 'texteditor', component: TextEditorComponent},
  { path: 'charts', component: ChartsComponent},
  { path: '**', redirectTo: '/login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
