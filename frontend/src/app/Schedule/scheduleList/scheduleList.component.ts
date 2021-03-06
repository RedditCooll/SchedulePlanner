import { Component, OnInit } from '@angular/core';
import { ScheduleTo } from '../schedule.model';
import { ScheduleService } from '../schedule.service';
import { AppConstants } from '../../LoginPage/common/app.constants';

@Component({
  selector: 'app-scheduleList',
  templateUrl: './scheduleList.component.html'
})
export class ScheduleListComponent implements OnInit{

    constructor(private scheduleService: ScheduleService){};

    data: ScheduleTo[] = [];
    
    ngOnInit(){
      this.scheduleService.showSchedules().subscribe( schedule => {
        this.data = schedule;
        console.log('get data from backend:', this.data);
      })
    }

    passTheEditPageUrl(scheduleTo: ScheduleTo):string{
      return AppConstants.SCHEDULE_EDIT_PAGE_URL + scheduleTo.id;
    }
}