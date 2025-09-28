import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfiniteScroller } from './infinite-scroller';

describe('InfiniteScroller', () => {
  let component: InfiniteScroller;
  let fixture: ComponentFixture<InfiniteScroller>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfiniteScroller]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfiniteScroller);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
