import { NgModule } from '@angular/core';
import { LeisureComponent } from '../leisure/leisure.component';
import { LeisureRoutingModule } from './leisure-routing.module';
import { SharedModule } from '../../Common/shared.module';
import { CommonModule, registerLocaleData } from '@angular/common';


@NgModule({
  declarations: [LeisureComponent],
  imports: [
    LeisureRoutingModule,
    SharedModule,
    CommonModule
  ]
})
export class LeisureModule { }
