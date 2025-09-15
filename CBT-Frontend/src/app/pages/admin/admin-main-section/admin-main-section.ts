import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Navbar } from '../../../components/UI/navbar/navbar';
import { SideBar } from '../../../components/admin/side-bar/side-bar';

@Component({
  selector: 'app-admin-main-section',
  imports: [Navbar, SideBar, RouterOutlet],
  templateUrl: './admin-main-section.html',
  styleUrl: './admin-main-section.css',
})
export class AdminMainSection {}
