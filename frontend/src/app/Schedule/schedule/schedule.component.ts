import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html'
})
export class ScheduleComponent implements OnInit{

    dataObject: any;
    settings: any;

    ngOnInit(){
      this.dataObject = [
        {
          id: 'id1',
          date: '01/30/2021',
          user: 'user1',
          content: 'studying',
          priority: '1',
          classification:'Work',
          status: 'Done'
        },
        {
          id: 'id2',
          date: '01/31/2021',
          user: 'user2',
          content: 'going to exerice',
          priority: '2',
          classification:'Work',
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


}