import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CsvParser } from './csv-parser';

describe('CsvParser', () => {
  let component: CsvParser;
  let fixture: ComponentFixture<CsvParser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CsvParser]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CsvParser);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
