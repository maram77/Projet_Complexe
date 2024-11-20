import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, AbstractControl } from '@angular/forms';
import { UserService } from '../../../../services/user-service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-deliveryman',
  templateUrl: './add-deliveryman.component.html',
  styleUrls: ['./add-deliveryman.component.scss']
})
export class AddDeliverymanComponent implements OnInit {
  deliverymanForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router  
  ) { }

  ngOnInit(): void {
    this.initForm();

    // Logging form value changes
    this.deliverymanForm.valueChanges.subscribe(values => {
      console.log('Current form values:', values);
    });

    // Logging form status changes
    this.deliverymanForm.statusChanges.subscribe(status => {
      console.log('Form status:', status);
    });
  }

  initForm(): void {
    this.deliverymanForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(4)]],
      confirmPassword: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });
  }

  passwordMatchValidator(form: AbstractControl): { [key: string]: boolean } | null {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      return { 'mismatch': true };
    }
    return null;
  }

  addDeliveryman(): void {
    console.log("Attempting to add deliveryman with form values:", this.deliverymanForm.value);
    if (this.deliverymanForm.valid) {
      this.userService.addDeliveryman(this.deliverymanForm.value).subscribe({
        next: (res) => {
          console.log('Deliveryman added successfully!', res);
          this.router.navigate(['/admin/users']); 
        },
        error: (err) => {
          console.error('Error adding deliveryman', err);
        }
      });
    } else {
      console.error('Form is not valid', this.deliverymanForm.errors);
      Object.keys(this.deliverymanForm.controls).forEach(key => {
        console.log(key + ' errors:', this.deliverymanForm.get(key).errors);
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/admin/users']);  
  }
}
