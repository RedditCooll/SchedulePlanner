import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LeisureComponent } from './leisure.component';

const routes: Routes = [
  { path: '', component: LeisureComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LeisureRoutingModule { }
