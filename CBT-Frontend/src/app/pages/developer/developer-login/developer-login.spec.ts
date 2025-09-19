import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperLogin } from './developer-login';

describe('DeveloperLogin', () => {
  let component: DeveloperLogin;
  let fixture: ComponentFixture<DeveloperLogin>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeveloperLogin]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeveloperLogin);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
