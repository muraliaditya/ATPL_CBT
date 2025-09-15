import { Component } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { Select } from 'primeng/select';
import { Message } from 'primeng/message';

@Component({
  selector: 'app-contest-listing',
  imports: [Message,Select,FloatLabelModule,FormsModule,InputTextModule],
  templateUrl: './contest-listing.html',
  styleUrl: './contest-listing.css'
})
export class ContestListing {
  choice: string = '';
  status: string[] = ['Completed','Active','InActive'];
  value2='';
}
