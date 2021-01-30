import { NgModule } from '@angular/core';
import { ScheduleComponent } from './schedule.component';
import { ScheduleRoutingModule } from './schedule-routing.module';
import { HotTableModule } from '@handsontable/angular';

@NgModule({
  declarations: [
      ScheduleComponent
  ],
  imports: [
    ScheduleRoutingModule,
    HotTableModule
  ],
  exports: [

  ],
  providers: [],
})
export class ScheduleModule { }
