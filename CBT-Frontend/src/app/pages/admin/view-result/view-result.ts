import { Component, OnInit } from '@angular/core';
import {
  contestResultsResponse,
  Participants,
} from '../../../models/admin/participant';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { Select } from 'primeng/select';
import { ResultService } from '../../../services/admin/result/result-service';

@Component({
  selector: 'app-view-result',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    FloatLabel,
    Select,
    InputTextModule,
    CommonModule,
    DynamicLayout,
  ],
  templateUrl: './view-result.html',
  styleUrl: './view-result.css',
})
export class ViewResult implements OnInit {
  eligibilty: 'Student' | 'Experienced' | '' = '';
  currentContestId: string = '';
  codingMarks: number | '' = '';
  McqMarks: number | '' = '';
  TotalMarks: number | '' = '';
  userSearch: string = '';
  priority: string[] = [];
  sortBy: string = '';

  boldColumns: string[] = [];

  headers: string[] = [];

  disableFilterButton(): boolean {
    return !(
      (this.codingMarks !== '' && this.codingMarks > 0) ||
      (this.McqMarks !== '' && this.McqMarks > 0) ||
      (this.TotalMarks !== '' && this.TotalMarks > 0)
    );
  }
  onSortMethodChange(value: Event) {
    this.filterData();
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private resultService: ResultService
  ) {}
  resultsData: Participants[] = [];
  originalData: Participants[] = [];
  sortOptions: string[] = ['Total Marks', 'Coding Marks', 'Mcq Marks'];

  priorityStudent: string[] = [
    'participantId',
    'userName',
    'codingMarks',
    'mcqMarks',
    'totalMarks',
    'college',
    'percentage',
    'collegeRegdNo',
  ];
  boldStudentColumns = ['participantId', 'collegeRegdNo', 'college'];
  headersStudent: string[] = [
    'ParticipantId',
    'Participant',
    'CodingMarks',
    'McqMarks',
    'TotalMarks',
    'College',
    'Percentage',
    'CollegeRegdNo',
  ];

  priorityExp: string[] = [
    'participantId',
    'userName',
    'codingMarks',
    'mcqMarks',
    'totalMarks',
    'company',
    'overallExperience',
    'designation',
  ];
  headersExp = [
    'ParticipantId',
    'Participant',
    'CodingMarks',
    'McqMarks',
    'TotalMarks',
    'Company',
    'Overall Experience',
    'Designation',
  ];
  boldExpColumns = ['participantId', 'company', 'designation'];

  compareCategory(results: Participants[]) {
    if (this.eligibilty === 'Student') {
      this.resultsData = results;
      this.originalData = results;
      this.headers = this.headersStudent;
      this.boldColumns = this.boldStudentColumns;
      this.priority = this.priorityStudent;
    } else if (this.eligibilty === 'Experienced') {
      this.resultsData = results;
      this.originalData = results;
      this.headers = this.headersExp;
      this.priority = this.priorityExp;
      this.boldColumns = this.boldExpColumns;
    }
  }

  userNameSearch() {
    this.resultsData = [...this.originalData].filter((participant) =>
      participant.userName.toLowerCase().includes(this.userSearch.toLowerCase())
    );
  }

  filterData() {
    let filters: { [key: string]: (value: number) => boolean } = {};
    let codeMarks = Number(this.codingMarks);

    if (!isNaN(codeMarks) && codeMarks > 0) {
      filters['codingMarks'] = (value: number) => value >= codeMarks;
    }

    let mcqMarks = Number(this.McqMarks);
    if (!isNaN(mcqMarks) && mcqMarks > 0) {
      filters['mcqMarks'] = (value: number) => value >= mcqMarks;
    }
    let totalMarks = Number(this.TotalMarks);
    if (!isNaN(totalMarks) && totalMarks > 0) {
      filters['totalMarks'] = (value: number) => value >= totalMarks;
    }
    let results: Participants[] = [];
    results = [...this.originalData].filter((participant) =>
      Object.entries(filters).every(([key, condition]) => {
        return condition(participant[key]);
      })
    );
    // sortOptions: string[] = ['Total Marks', 'Coding Marks', 'Mcq Marks'];

    if (this.sortBy === 'Total Marks') {
      results.sort((a, b) => b.totalMarks - a.totalMarks);
    } else if (this.sortBy === 'Coding Marks') {
      results.sort((a, b) => b.codingMarks - a.codingMarks);
    } else if (this.sortBy === 'Mcq Marks') {
      results.sort((a, b) => b.mcqMarks - b.mcqMarks);
    }
    this.resultsData = results;
  }
  goToParticipantResponse(submissionId: string) {
    this.router.navigate(['/admin/view-response', submissionId]);
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.currentContestId = id;
      this.resultService.getContestResults(this.currentContestId).subscribe({
        next: (data) => {
          console.log(data);
          if (data.eligibility === 'Student') {
            this.eligibilty = 'Student';
          } else {
            this.eligibilty = 'Experienced';
          }
          this.compareCategory(data.results);
        },
        error: (error) => {
          console.log(error);
        },
      });
    }
  }
}
