import { Component, OnInit } from '@angular/core';
import { Participants } from '../../../models/admin/participant';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FloatLabel } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view-result',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    FloatLabel,
    InputTextModule,
    CommonModule,
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

  boldColumns: string[] = [];

  headers: string[] = [];

  disableFilterButton(): boolean {
    return !(
      (this.codingMarks !== '' && this.codingMarks > 0) ||
      (this.McqMarks !== '' && this.McqMarks > 0) ||
      (this.TotalMarks !== '' && this.TotalMarks > 0)
    );
  }
  constructor(private route: ActivatedRoute) {}
  //hi
  resultsData: Participants[] = [];
  originalData: Participants[] = [];

  priorityStudent: string[] = [
    'participantId',
    'userName',
    'college',
    'percentage',
    'collegeRegdNo',
    'codingMarks',
    'mcqMarks',
    'totalMarks',
  ];
  boldStudentColumns = ['participantId', 'collegeRegdNo', 'college'];
  headersStudent: string[] = [
    'ParticipantId',
    'Participant',

    'College',
    'Percentage',
    'CollegeRegdNo',
    'CodingMarks',
    'McqMarks',
    'TotalMarks',
  ];

  priorityExp: string[] = [
    'participantId',
    'userName',
    'company',
    'overallExperience',
    'designation',
    'codingMarks',
    'mcqMarks',
    'totalMarks',
  ];
  headersExp = [
    'ParticipantId',
    'Participant',
    'Company',
    'Overall Experience',
    'Designation',
    'CodingMarks',
    'McqMarks',
    'TotalMarks',
  ];
  boldExpColumns = ['participantId', 'company', 'designation'];
  experiencedData: Participants[] = [
    {
      submissionId: 'SUB-1001',
      participantId: 'UD-0441',
      userName: 'John deo',
      email: 'johndeo@gmail.com',
      company: 'Maron',
      overallExperience: 2,
      designation: 'SDE1',
      codingMarks: 23,
      mcqMarks: 23,
      totalMarks: 56,
    },
    {
      submissionId: 'SUB-1002',
      participantId: 'UD-0441',
      userName: 'hello deo',
      email: 'johndeo@gmail.com',
      company: 'Maron',
      designation: 'SDE1',
      overallExperience: 10,
      codingMarks: 23,
      mcqMarks: 23,
      totalMarks: 40,
    },
  ];
  studentsData = [
    {
      submissionId: 'SUB-1001',
      participantId: 'UD-0441',
      userName: 'John deo',
      email: 'john@gmail.com',
      college: 'LENDI',
      collegeRegdNo: '21KD1A0566',
      percentage: 88,
      codingMarks: 23,
      mcqMarks: 23,
      totalMarks: 56,
    },
    {
      submissionId: 'SUB-1002',
      participantId: 'UD-0442',
      userName: 'Alice Smith',
      email: 'alice@gmail.com',
      college: 'GITAM',
      collegeRegdNo: '21KD1A0455',
      percentage: 92,
      codingMarks: 28,
      mcqMarks: 24,
      totalMarks: 62,
    },
    {
      submissionId: 'SUB-1003',
      participantId: 'UD-0442',
      userName: 'Alice Smith',
      email: 'alice@gmail.com',
      college: 'GITAM',
      collegeRegdNo: '21KD1A0455',
      percentage: 50,
      codingMarks: 35,
      mcqMarks: 60,
      totalMarks: 62,
    },
    {
      submissionId: 'SUB-1004',
      participantId: 'UD-0442',
      userName: 'Alice Smith',
      email: 'alice@gmail.com',
      college: 'GITAM',
      collegeRegdNo: '21KD1A0455',
      percentage: 50,
      codingMarks: 35,
      mcqMarks: 60,
      totalMarks: 90,
    },
  ];
  compareCategory() {
    if (this.eligibilty === 'Student') {
      this.resultsData = this.studentsData;
      this.originalData = this.studentsData;
      this.headers = this.headersStudent;
      this.boldColumns = this.boldStudentColumns;
      this.priority = this.priorityStudent;
    } else if (this.eligibilty === 'Experienced') {
      this.resultsData = this.experiencedData;
      this.originalData = this.experiencedData;
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
    this.resultsData = [...this.originalData].filter((participant) =>
      Object.entries(filters).every(([key, condition]) => {
        return condition(participant[key]);
      })
    );
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.currentContestId = id;
    }
    this.eligibilty = 'Student';
    this.compareCategory();
  }
}
