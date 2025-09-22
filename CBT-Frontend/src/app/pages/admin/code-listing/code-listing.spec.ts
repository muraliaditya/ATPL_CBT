import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeListing } from './code-listing';

describe('CodeListing', () => {
  let component: CodeListing;
  let fixture: ComponentFixture<CodeListing>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeListing]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodeListing);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
