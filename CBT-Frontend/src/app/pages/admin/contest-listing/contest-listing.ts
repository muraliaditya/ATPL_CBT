import { Component, ElementRef, HostListener } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FloatLabelModule } from 'primeng/floatlabel';
import { FormsModule } from '@angular/forms';
import { Select } from 'primeng/select';
import { CommonModule } from '@angular/common';
import { Contest } from '../../../models/admin/admin';
@Component({
  selector: 'app-contest-listing',
  imports: [
    CommonModule,
    Select,
    FloatLabelModule,
    FormsModule,
    InputTextModule,
  ],
  templateUrl: './contest-listing.html',
  styleUrl: './contest-listing.css',
})
export class ContestListing {
  choice: string = '';
  status: string[] = ['Completed', 'Active', 'InActive'];
  ContestId = '';
  ContestName = '';
  contests: Contest[] = [
    {
      id: '#CT-0441',
      name: 'Contest 1',
      status: 'COMPLETED',
      startTime: '24 Jan 2025, 5:00pm',
      endTime: '24 Jan 2025, 7:00pm',
      duration: '120 min',
      eligibility: 'Student',
    },
    {
      id: '#CT-0443',
      name: 'Contest 1',
      status: 'ACTIVE',
      startTime: '24 Jan 2025, 5:00pm',
      endTime: '24 Jan 2025, 7:00pm',
      duration: '120 min',
      eligibility: 'Preliminary',
    },
    {
      id: '#CT-0442',
      name: 'Contest 1',
      status: 'COMPLETED',
      startTime: '24 Jan 2025, 5:00pm',
      endTime: '24 Jan 2025, 7:00pm',
      duration: '120 min',
      eligibility: 'Advanced',
    },
    {
      id: '#CT-0439',
      name: 'Contest 1',
      status: 'ACTIVE',
      startTime: '24 Jan 2025, 5:00pm',
      endTime: '24 Jan 2025, 7:00pm',
      duration: '120 min',
      eligibility: 'Preliminary',
    },
  ];

filteredContests: Contest[] = [];

ngOnInit() {
  this.filteredContests = this.contests;
}

searchContests() {
  this.filteredContests = this.contests.filter(contest => {
    const matchName = this.ContestName
      ? contest.name.toLowerCase().includes(this.ContestName.toLowerCase())
      : true;

    const matchId = this.ContestId
      ? contest.id.toLowerCase().includes(this.ContestId.toLowerCase())
      : true;

    const matchStatus = this.choice
      ? contest.status.toLowerCase() === this.choice.toLowerCase()
      : true;

    return matchName && matchId && matchStatus;
  });
}

  openRow: number | null = null;
  constructor(private eRef: ElementRef) {}

  toggleMenu(index: number, event: Event) {
    event.stopPropagation();
    this.openRow = this.openRow === index ? null : index;
  }

  onView(contest: Contest) {
    alert(`View ${contest.id}`);
    this.openRow = null;
  }

  onEdit(contest: Contest) {
    alert(`Edit ${contest.id}`);
    this.openRow = null;
  }

  onDelete(contest: Contest) {
    if (confirm(`Delete ${contest.id}?`)) {
      this.contests = this.contests.filter((c) => c.id !== contest.id);
    }
    this.openRow = null;
  }
  result(){
    alert('result');
  }
  @HostListener('document:click', ['$event'])
  handleClickOutside(event: Event) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.openRow = null;
    }
  }
}
