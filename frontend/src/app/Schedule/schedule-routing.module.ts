import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SchedulePlanComponent } from './schedulePlan/schedulePlan.component';
import { ScheduleListComponent } from './scheduleList/scheduleList.component';
import { ScheduleTextEditComponent } from './scheduleEdit/scheduleTextEdit.component';

const routes: Routes = [
  { path: 'plan', component: SchedulePlanComponent },
  { path: 'list',component: ScheduleListComponent},
  { path: 'edit/:id', component: ScheduleTextEditComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScheduleRoutingModule { }