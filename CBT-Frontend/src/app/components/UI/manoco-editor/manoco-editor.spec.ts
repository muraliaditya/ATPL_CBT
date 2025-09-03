import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManocoEditor } from './manoco-editor';

describe('ManocoEditor', () => {
  let component: ManocoEditor;
  let fixture: ComponentFixture<ManocoEditor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManocoEditor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManocoEditor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
