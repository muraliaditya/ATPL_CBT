import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MonocoEditor } from './monoco-editor';

describe('MonocoEditor', () => {
  let component: MonocoEditor;
  let fixture: ComponentFixture<MonocoEditor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MonocoEditor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MonocoEditor);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
