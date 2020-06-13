import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentSubmittedComponent } from './payment-submitted.component';

describe('PaymentSubmittedComponent', () => {
  let component: PaymentSubmittedComponent;
  let fixture: ComponentFixture<PaymentSubmittedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentSubmittedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentSubmittedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
