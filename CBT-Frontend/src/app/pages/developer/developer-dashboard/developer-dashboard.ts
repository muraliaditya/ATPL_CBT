import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { codingQuestions } from '../../../models/admin/admin';
import { InputText } from 'primeng/inputtext';
import { FloatLabel } from 'primeng/floatlabel';
import { DynamicLayout } from '../../../components/UI/dynamic-layout/dynamic-layout';
import { recentquestion } from '../../../models/developer/developer';
import { devSummary } from '../../../models/developer/developer';
import { Progress } from '../../../components/UI/progress/progress';
@Component({
  selector: 'app-developer-dashboard',
  imports: [
    Progress,
    CommonModule,
    InputText,
    FloatLabel,
    FormsModule,
    DynamicLayout,
  ],
  templateUrl: './developer-dashboard.html',
  styleUrl: './developer-dashboard.css',
})
export class DeveloperDashboard {
  solved = '';
  total = '';
  sort = '';
  RecentAdd: recentquestion[] = [
    {
      codingQuestionId: 'code_001',
      questionName: 'Add Two Numbers',
      adminId: 'admin_123',
      adminName: 'John Smith',
    },
    {
      codingQuestionId: 'code_002',
      questionName: 'Binary Search Implementation',
      adminId: 'admin_456',
      adminName: 'Sarah Johnson',
    },
    {
      codingQuestionId: 'code_003',
      questionName: 'Merge Two Sorted Arrays',
      adminId: 'admin_789',
      adminName: 'Mike Wilson',
    },
    {
      codingQuestionId: 'code_004',
      questionName: 'Find Maximum Element',
      adminId: 'admin_123',
      adminName: 'John Smith',
    },
    {
      codingQuestionId: 'code_003',
      questionName: 'Merge Two Sorted Arrays',
      adminId: 'admin_789',
      adminName: 'Mike Wilson',
    },
    {
      codingQuestionId: 'code_004',
      questionName: 'Find Maximum Element',
      adminId: 'admin_123',
      adminName: 'John Smith',
    },
    {
      codingQuestionId: 'code_003',
      questionName: 'Merge Two Sorted Arrays',
      adminId: 'admin_789',
      adminName: 'Mike Wilson',
    },
    {
      codingQuestionId: 'code_004',
      questionName: 'Find Maximum Element',
      adminId: 'admin_123',
      adminName: 'John Smith',
    },
    {
      codingQuestionId: 'code_003',
      questionName: 'Merge Two Sorted Arrays',
      adminId: 'admin_789',
      adminName: 'Mike Wilson',
    },
    {
      codingQuestionId: 'code_004',
      questionName: 'Find Maximum Element',
      adminId: 'admin_123',
      adminName: 'John Smith',
    },
  ];
  'pageNo': 1;
  Codes: codingQuestions[] = [
    {
      questionId: 'code_001',
      questionName: 'Add Two Numbers',
      difficulty: 'Easy',
    },
    {
      questionId: 'code_002',
      questionName: 'Integer Conversion',
      difficulty: 'Medium',
    },
    {
      questionId: 'code_003',
      questionName: 'Binary Tree Traversal',
      difficulty: 'Hard',
    },
    {
      questionId: 'code_004',
      questionName: 'Array Sum',
      difficulty: 'Easy',
    },
    {
      questionId: 'code_005',
      questionName: 'String Palindrome',
      difficulty: 'Medium',
    },
  ];
  developerstatus: devSummary[] = [
    {
      devId: '#DEV-0441',
      devName: 'John Doe',
      solvedQuestionsCount: 45,
    },
    {
      devId: '#DEV-0442',
      devName: 'Jane Smith',
      solvedQuestionsCount: 38,
    },
    {
      devId: '#DEV-0443',
      devName: 'Mike Johnson',
      solvedQuestionsCount: 52,
    },
    {
      devId: '#DEV-0444',
      devName: 'Sarah Wilson',
      solvedQuestionsCount: 29,
    },
    {
      devId: '#DEV-0445',
      devName: 'Alex Brown',
      solvedQuestionsCount: 41,
    },
    {
      devId: '#DEV-0441',
      devName: 'John Doe',
      solvedQuestionsCount: 45,
    },
    {
      devId: '#DEV-0442',
      devName: 'Jane Smith',
      solvedQuestionsCount: 38,
    },
    {
      devId: '#DEV-0443',
      devName: 'Mike Johnson',
      solvedQuestionsCount: 52,
    },
    {
      devId: '#DEV-0444',
      devName: 'Sarah Wilson',
      solvedQuestionsCount: 29,
    },
    {
      devId: '#DEV-0445',
      devName: 'Alex Brown',
      solvedQuestionsCount: 41,
    },
  ];
  totalQuestions = 300;
}
