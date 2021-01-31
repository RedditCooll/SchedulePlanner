import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { SchedulePlanComponent } from './schedulePlan/schedulePlan.component';
import { ScheduleListComponent } from './scheduleList/scheduleList.component';
import { ScheduleRoutingModule } from './schedule-routing.module';
import { HotTableModule } from '@handsontable/angular';
import { SharedModule } from '../Common/shared.module';

@NgModule({
  declarations: [
      SchedulePlanComponent,
      ScheduleListComponent
  ],
  imports: [
    CommonModule,
    ScheduleRoutingModule,
    HotTableModule,
    SharedModule
  ],
  exports: [

  ],
  providers: [],
})
export class ScheduleModule { }
