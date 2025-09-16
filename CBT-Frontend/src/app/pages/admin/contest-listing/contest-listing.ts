import { Component, ElementRef, HostListener } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { Select } from 'primeng/select';
import { Message } from 'primeng/message';
import { CommonModule } from '@angular/common';
import { Contest } from '../../../models/admin/admin';
// interface Contest {
//   id: string;
//   name: string;
//   status: 'ACTIVE' | 'COMPLETED';
//   startTime?: string;
//   endTime?: string;
//   duration?: string;
//   eligibility?: string;
// }
@Component({
  selector: 'app-contest-listing',
  imports: [CommonModule,Message,Select,FloatLabelModule,FormsModule,InputTextModule],
  templateUrl: './contest-listing.html',
  styleUrl: './contest-listing.css'
})
export class ContestListing {
  choice: string = '';
  status: string[] = ['Completed','Active','InActive'];
  value2='';
  contests: Contest[] = [
    {
      id: '#CT-0441',
      name: 'Contest 1',
      status: 'COMPLETED',
      startTime: '24 Jan 2025, 5:00pm',
      endTime: '24 Jan 2025, 7:00pm',
      duration: '120 min',
      eligibility: 'Student'
    },
    {
      id: '#CT-0443',
      name: 'Contest 1',
      status: 'ACTIVE',
      eligibility: 'Preliminary'
    },
    {
      id: '#CT-0442',
      name: 'Contest 1',
      status: 'COMPLETED',
      eligibility: 'Advanced'
    },
    {
      id: '#CT-0439',
      name: 'Contest 1',
      status: 'ACTIVE'
    }
  ];

  openRow: number | null = null;
  constructor(private eRef: ElementRef) {}

  toggleMenu(index: number, event: Event) {
    event.stopPropagation(); 
    this.openRow = this.openRow === index ? null : index;
  }

  onView(contest: Contest) {
    alert(`Viewing ${contest.id}`);
    this.openRow = null;
  }

  onEdit(contest: Contest) {
    alert(`Editing ${contest.id}`);
    this.openRow = null;
  }

  onDelete(contest: Contest) {
    if (confirm(`Delete ${contest.id}?`)) {
      this.contests = this.contests.filter(c => c.id !== contest.id);
    }
    this.openRow = null;
  }
    @HostListener('document:click', ['$event'])
  handleClickOutside(event: Event) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.openRow = null;
    }
  }
}
