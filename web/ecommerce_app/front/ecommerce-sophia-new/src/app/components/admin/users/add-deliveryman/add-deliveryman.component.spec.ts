import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDeliverymanComponent } from './add-deliveryman.component';

describe('AddDeliverymanComponent', () => {
  let component: AddDeliverymanComponent;
  let fixture: ComponentFixture<AddDeliverymanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddDeliverymanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDeliverymanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
