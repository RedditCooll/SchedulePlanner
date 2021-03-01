import { Component, OnInit, ViewChild } from '@angular/core';
import { HotTableRegisterer } from '@handsontable/angular';
import Handsontable from 'handsontable';
import { Rate, ScheduleTo, User } from '../schedule.model';
import { ScheduleService } from '../schedule.service';
import { saveAs } from 'file-saver';
import { NzUploadChangeParam } from 'ng-zorro-antd/upload';
import { NzMessageService } from 'ng-zorro-antd/message';

@Component({
  selector: 'app-schedulePlan',
  templateUrl: './schedulePlan.component.html'
})
export class SchedulePlanComponent implements OnInit{

  constructor(private scheduleService: ScheduleService,
              private msg: NzMessageService){
  }

  isCreatSchedulesBtnLoading = false;
  isImportExcelBtnLoading = false;
  isExportExcelBtnLoading = false;
  dataObject: any;
  settings: any; // table setting

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
    var scheduleToArray = this.getCurrentSheetData()
    console.log('send data to backend:',scheduleToArray)

    this.isCreatSchedulesBtnLoading = true;
    this.scheduleService.createSchedules(scheduleToArray).subscribe( result => {
        if(result == true){
          this.isCreatSchedulesBtnLoading = false;
          console.log('create schedules sucessfully!');
        }
        else{
          this.isCreatSchedulesBtnLoading = false;
          console.log('create schedules failed!');
        }
    } )
  }

  importExcel(info: NzUploadChangeParam){ 
    if (info.file.status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === 'done') {
      this.msg.success(`${info.file.name} file uploaded successfully`);
      let file = info ? info.file : null;
      console.log('importExcel response:', file.response)
      // put response into table, update
      // formate date, user
      var newData = []
      file.response.forEach(element => {
        var tmp = element
        tmp.date = element.date[0] + "/" + element.date[1] + "/" + element.date[2]
        tmp.user = element.user.id
        newData.push(tmp)
      });
      this.settings.data = newData;
      this.hotRegisterer.getInstance(this.hotId).updateSettings(this.settings, true)
    } 
    else if (info.file.status === 'error') {
      this.msg.error(`${info.file.name} file upload failed.`);
    }
  }

  exportExcel(){
    var scheduleToArray = this.getCurrentSheetData()
    console.log('send data to backend:',scheduleToArray)
    this.isExportExcelBtnLoading = true;
    this.scheduleService.exportExcel(scheduleToArray).subscribe( response => {
        this.saveFile(response.body, 'Schedule_Export.xlsx');
        console.log('exportExcel response:', response);
        console.log('export excel sucessfully!');
        this.isExportExcelBtnLoading = false;
    },
    error => console.log("error downloading the file"));
  }

  getCurrentSheetData(): ScheduleTo[]{
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
    return scheduleToArray;
  }

  saveFile(data: any, filename?: string) {
    const blob = new Blob([data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8'});
    saveAs(blob, filename);
  }
}