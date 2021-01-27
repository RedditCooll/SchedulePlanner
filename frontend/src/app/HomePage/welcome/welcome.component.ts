import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.less']
})
export class WelcomeComponent implements OnInit {
  array = [1, 2, 3, 4];
  arrayOfImgUrls = [
    'https://images.unsplash.com/photo-1545893835-abaa50cbe628?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2073&q=80',
    'https://images.unsplash.com/photo-1546884680-a1de22e94d50?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80',
  ];
  constructor() { }

  ngOnInit() {
  }

}
