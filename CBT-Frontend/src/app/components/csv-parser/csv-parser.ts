import { Component } from '@angular/core';
import { NgxCsvParserModule } from 'ngx-csv-parser';
import { NgxCsvParser, NgxCSVParserError } from 'ngx-csv-parser';

@Component({
  selector: 'app-csv-parser',
  imports: [NgxCsvParserModule],
  templateUrl: './csv-parser.html',
  styleUrl: './csv-parser.css',
})
export class CsvParser {
  csvRecords: any[] = [];
  header = true;
  constructor(private ngxCsvParser: NgxCsvParser) {}

  fileChangeListener($event: any): void {
    const files = $event.srcElement.files;
    this.header =
      (this.header as unknown as string) === 'true' || this.header === true;

    this.ngxCsvParser
      .parse(files[0], {
        header: this.header,
        delimiter: ',',
        encoding: 'utf8',
      })
      .pipe()
      .subscribe({
        next: (result): void => {
          console.log('Result', result);
        },
        error: (error: NgxCSVParserError): void => {
          console.log('Error', error);
        },
      });
  }
}
