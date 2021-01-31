import { Component, OnInit, ViewChild } from '@angular/core';
import { HotTableRegisterer } from '@handsontable/angular';
import Handsontable from 'handsontable';
import { Rate, ScheduleTo, User } from '../schedule.model';
import { ScheduleService } from '../schedule.service';

@Component({
  selector: 'app-schedulePlan',
  templateUrl: './schedulePlan.component.html'
})
export class SchedulePlanComponent implements OnInit{

  constructor(private scheduleService: ScheduleService){

  }

  isLoading = false;
  dataObject: any;
  settings: any;

  private hotRegisterer = new HotTableRegisterer();
  hotId = 'hotInstance';

  ngOnInit(){
    // example data
    this.dataObject = [
        {
          id: 'id1',
          date: '01/30/2021',
          user: 'user1',
          content: 'studying',
          priority: '1',
          classification:'Work',
          address: 'My home',
          status: 'Done'
        },
        {
          id: 'id2',
          date: '01/31/2021',
          user: 'user2',
          content: 'going to exerice',
          priority: '2',
          classification:'Work',
          address: 'School',
          status: ''
        }
      ];
    this.settings = {
        data: this.dataObject,
        columns: [
          {
            data: 'id',
            type: 'text',
            width: 40
          },
          {
            data: 'date',
            type: 'date',
            dateFormat: 'MM/DD/YYYY'
          },
          {
            data: 'user',
            type: 'text'
          },
          {
            data: 'content',
            type: 'text'
          },
          {
            data: 'priority',
            type: 'numeric'
          },
          {
            data: 'classification',
            type: 'text'
          },
          {
            data: 'address',
            type: 'text'
          },
          {
            data: 'status',
            editor: 'select',
            selectOptions: ['Done', '']
          },
        ],
        stretchH: 'all',
        width: 924,
        autoWrapRow: true,
        height: 487,
        maxRows: 22,
        manualRowResize: true,
        manualColumnResize: true,
        rowHeaders: true,
        colHeaders: [
          'ID',
          'Date',
          'User',
          'Content',
          'Priority',
          'Classification',
          'Address',
          'Status'
        ],
        manualRowMove: true,
        manualColumnMove: true,
        contextMenu: true,
        filters: true,
        dropdownMenu: true,
        licenseKey: 'non-commercial-and-evaluation'
    }
    
    
  } 

  createSchedules(){
    // get current data
    // change string to date
    var scheduleToArray: ScheduleTo[] = [];
    this.hotRegisterer.getInstance(this.hotId).getSourceDataArray().forEach( row => {
      var rate: Rate = {
        veryGood: 0,
        good: 0,
        like: 0
      }
      var user: User = {
        id: row[2],
        name: ''
      }
      var scheduleTo = {
        id : row[0],
        date : new Date(row[1]),
        user : user,
        content : row[3],
        priority : row[4],
        classification : row[5],
        address: row[6],
        status : row[7],
        rate : rate,
      }
      scheduleToArray.push(scheduleTo);
    } )
    console.log('send data to backend:',scheduleToArray)

    this.isLoading = true;
    this.scheduleService.createSchedules(scheduleToArray).subscribe( result => {
        if(result == true){
          this.isLoading = false;
          console.log('create schedules sucessfully!');
        }
        else{
          this.isLoading = false;
          console.log('create schedules failed!');
        }
    } )
  }



}