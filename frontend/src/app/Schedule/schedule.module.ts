import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { SchedulePlanComponent } from './schedulePlan/schedulePlan.component';
import { ScheduleListComponent } from './scheduleList/scheduleList.component';
import { ScheduleTextEditComponent } from './scheduleEdit/scheduleTextEdit.component';
import { ScheduleRoutingModule } from './schedule-routing.module';
import { HotTableModule } from '@handsontable/angular';
import { SharedModule } from '../Common/shared.module';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';


@NgModule({
  declarations: [
      SchedulePlanComponent,
      ScheduleListComponent,
      ScheduleTextEditComponent
  ],
  imports: [
    CommonModule,
    ScheduleRoutingModule,
    HotTableModule,
    SharedModule,
    CKEditorModule
  ],
  exports: [

  ],
  providers: [],
})
export class ScheduleModule { }
