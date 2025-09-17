import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { RouterPrevService } from '../../../services/common/router-prev-service';

@Component({
  selector: 'app-admin-header',
  imports: [],
  templateUrl: './admin-header.html',
  styleUrl: './admin-header.css',
})
export class AdminHeader {
  @Input() heading: string = '';
  @Input() navigateUrl: string = '';
  constructor(
    private router: Router,
    private location: Location,
    private routerprev: RouterPrevService
  ) {}
  goToPrevPage() {
    let prev = this.routerprev.getPreviousUrl();
    if (prev) {
      this.router.navigateByUrl(prev);
    }
  }
}
