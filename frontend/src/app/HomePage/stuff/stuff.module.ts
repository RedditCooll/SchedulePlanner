import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/Common/shared.module';
import { StuffComponent } from './stuff.component';
import { StuffRoutingModule} from './stuff-routing.module';


@NgModule({
  declarations: [StuffComponent],
  imports: [
    StuffRoutingModule,
    SharedModule,
  ],
  exports: [StuffComponent]
})
export class StuffModule { }
