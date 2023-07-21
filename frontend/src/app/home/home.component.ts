import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_service/auth.service';

import { saveAs } from 'file-saver';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  users!: any[]; // Array to store the user data retrieved from AuthService

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Retrieve user data from AuthService and assign it to 'users' array
    this.authService.getXmlData().subscribe((data) => {
      this.users = data;
    });
  }

  generateReport(userId: number): void {
    const user = this.users.find((user) => user.id === userId);
    if (user) {
      // Generate XML report for the user
      const xmlDeclaration = '<?xml version="1.0" encoding="UTF-8"?>';
      const xmlReport = `
        <user>
          <userId>${user.id}</userId>
          <name>${user.name}</name>
          <city>${user.city}</city>
          <mobileNumber>${user.mobileNumber}</mobileNumber>
        </user>
      `;
      const blobContent = xmlDeclaration + xmlReport;
      const blob = new Blob([blobContent], { type: 'application/xml' });
       // Save the generated report as a file using 'saveAs' functio
      saveAs(blob, `report_${user.name}-${user.id}.xml`);
    }
  }
}
