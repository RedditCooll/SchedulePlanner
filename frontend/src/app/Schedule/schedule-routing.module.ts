import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SchedulePlanComponent } from './schedulePlan/schedulePlan.component';
import { ScheduleListComponent } from './scheduleList/scheduleList.component';

const routes: Routes = [
  { path: 'plan', component: SchedulePlanComponent },
  { path: 'list',component: ScheduleListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ScheduleRoutingModule { }