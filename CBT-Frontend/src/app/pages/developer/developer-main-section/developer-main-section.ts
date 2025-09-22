import { Component } from '@angular/core';
import { Navbar } from '../../../components/UI/navbar/navbar';
import { SideBar} from '../../../components/developer/side-bar/side-bar';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-developer-main-section',
  imports: [Navbar, SideBar, RouterOutlet],
  templateUrl: './developer-main-section.html',
  styleUrl: './developer-main-section.css'
})
export class DeveloperMainSection {

}

