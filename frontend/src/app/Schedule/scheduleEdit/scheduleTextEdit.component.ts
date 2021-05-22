import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import * as DecoupledEditor from '@ckeditor/ckeditor5-build-decoupled-document';
import { ScheduleTo } from '../schedule.model';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-scheduleTextEditComponent',
  templateUrl: './scheduleTextEdit.component.html',
  styleUrls: ['./scheduleTextEdit.component.less']
})
export class ScheduleTextEditComponent implements OnInit {

  scheduleId: string;
  schedule: ScheduleTo;
  public Editor = DecoupledEditor;

  constructor(
    private scheduleService: ScheduleService,
    private route: ActivatedRoute) {};

  ngOnInit(): void {
    var user = {
      id: '',
      displayName: ''
    };
    var rate = {
      veryGood: 0,
      good: 0,
      like: 0
    };
    this.schedule = {
      id: '',
      date: new Date(),
      user: user,
      priority: 0,
      status: '',
      classification: '',
      content: '',
      address: '',
      rate: rate
    };

    this.scheduleId = this.route.snapshot.paramMap.get('id');
    this.getSchedule();
  }

  onReady( editor ) {
    editor.ui.getEditableElement().parentElement.insertBefore(
        editor.ui.view.toolbar.element,
        editor.ui.getEditableElement()
    );
  }

  getSchedule(){
    this.scheduleService.getScheduleById(this.scheduleId).subscribe( result => {
      if(result != null){
        this.schedule = result;
        console.log(this.schedule);
        console.log('getSchedule sucessfully!');
      }
      else{
        console.log('getSchedule failed!');
      }
    });
  }
}
