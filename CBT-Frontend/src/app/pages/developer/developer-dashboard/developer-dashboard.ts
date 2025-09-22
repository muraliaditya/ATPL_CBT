import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { codingQuestions } from '../../../models/admin/admin';
import { InputText } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { recentquestion } from '../../../models/developer/developer';
import { devSummary } from '../../../models/developer/developer';
import { Progress } from '../../../components/progress/progress';
@Component({
  selector: 'app-developer-dashboard',
  imports: [Progress,CommonModule,InputText,FloatLabel,FormsModule,DynamicLayout],
  templateUrl: './developer-dashboard.html',
  styleUrl: './developer-dashboard.css'
})
export class DeveloperDashboard {
  
  solved='';
  total='';
  sort='';
  RecentAdd:recentquestion[]= [
    {
      "codingQuestionId": "code_001",
      "questionName": "Add Two Numbers",
      "adminId": "admin_123",
      "adminName": "John Smith",
      "createdAt": "2025-01-15T10:30:00Z",
      "updatedAt": "2025-01-20T14:45:00Z"
    },
    {
      "codingQuestionId": "code_002", 
      "questionName": "Binary Search Implementation",
      "adminId": "admin_456",
      "adminName": "Sarah Johnson",
      "createdAt": "2025-01-18T09:15:00Z",
      "updatedAt": "2025-01-22T16:20:00Z"
    },
    {
      "codingQuestionId": "code_003",
      "questionName": "Merge Two Sorted Arrays", 
      "adminId": "admin_789",
      "adminName": "Mike Wilson",
      "createdAt": "2025-01-20T11:00:00Z",
      "updatedAt": "2025-01-23T13:30:00Z"
    },
    {
      "codingQuestionId": "code_004",
      "questionName": "Find Maximum Element",
      "adminId": "admin_123", 
      "adminName": "John Smith",
      "createdAt": "2025-01-22T08:45:00Z",
      "updatedAt": "2025-01-24T10:15:00Z"
    },
    {
      "codingQuestionId": "code_003",
      "questionName": "Merge Two Sorted Arrays", 
      "adminId": "admin_789",
      "adminName": "Mike Wilson",
      "createdAt": "2025-01-20T11:00:00Z",
      "updatedAt": "2025-01-23T13:30:00Z"
    },
    {
      "codingQuestionId": "code_004",
      "questionName": "Find Maximum Element",
      "adminId": "admin_123", 
      "adminName": "John Smith",
      "createdAt": "2025-01-22T08:45:00Z",
      "updatedAt": "2025-01-24T10:15:00Z"
    },
    {
      "codingQuestionId": "code_003",
      "questionName": "Merge Two Sorted Arrays", 
      "adminId": "admin_789",
      "adminName": "Mike Wilson",
      "createdAt": "2025-01-20T11:00:00Z",
      "updatedAt": "2025-01-23T13:30:00Z"
    },
    {
      "codingQuestionId": "code_004",
      "questionName": "Find Maximum Element",
      "adminId": "admin_123", 
      "adminName": "John Smith",
      "createdAt": "2025-01-22T08:45:00Z",
      "updatedAt": "2025-01-24T10:15:00Z"
    },
     {
      "codingQuestionId": "code_003",
      "questionName": "Merge Two Sorted Arrays", 
      "adminId": "admin_789",
      "adminName": "Mike Wilson",
      "createdAt": "2025-01-20T11:00:00Z",
      "updatedAt": "2025-01-23T13:30:00Z"
    },
    {
      "codingQuestionId": "code_004",
      "questionName": "Find Maximum Element",
      "adminId": "admin_123", 
      "adminName": "John Smith",
      "createdAt": "2025-01-22T08:45:00Z",
      "updatedAt": "2025-01-24T10:15:00Z"
    },
    
  ]
  "pageNo": 1;
    Codes:codingQuestions[]= 
    [
      {
        "questionId": "code_001",
        "questionName": "Add Two Numbers",
        "difficulty": "Easy"
      },
      {
        "questionId": "code_002", 
        "questionName": "Integer Conversion",
        "difficulty": "Medium"
      },
      {
        "questionId": "code_003",
        "questionName": "Binary Tree Traversal",
        "difficulty": "Hard"
      },
      {
        "questionId": "code_004",
        "questionName": "Array Sum",
        "difficulty": "Easy"
      },
      {
        "questionId": "code_005",
        "questionName": "String Palindrome",
        "difficulty": "Medium"
      },
      
    ]
    developerstatus:devSummary[]= [
    {
      "devId": "#DEV-0441",
      "devName": "John Doe",
      "solvedQuestionsCount": 45
    },
    {
      "devId": "#DEV-0442", 
      "devName": "Jane Smith",
      "solvedQuestionsCount": 38
    },
    {
      "devId": "#DEV-0443",
      "devName": "Mike Johnson", 
      "solvedQuestionsCount": 52
    },
    {
      "devId": "#DEV-0444",
      "devName": "Sarah Wilson",
      "solvedQuestionsCount": 29
    },
    {
      "devId": "#DEV-0445",
      "devName": "Alex Brown",
      "solvedQuestionsCount": 41
    },
    {
      "devId": "#DEV-0441",
      "devName": "John Doe",
      "solvedQuestionsCount": 45
    },
    {
      "devId": "#DEV-0442", 
      "devName": "Jane Smith",
      "solvedQuestionsCount": 38
    },
    {
      "devId": "#DEV-0443",
      "devName": "Mike Johnson", 
      "solvedQuestionsCount": 52
    },
    {
      "devId": "#DEV-0444",
      "devName": "Sarah Wilson",
      "solvedQuestionsCount": 29
    },
    {
      "devId": "#DEV-0445",
      "devName": "Alex Brown",
      "solvedQuestionsCount": 41
    }
  ];
  totalQuestions= 300;
}
  
