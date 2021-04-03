// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

// @Component({
//   selector: 'app-register',
//   templateUrl: './register.component.html',
//   styleUrls: ['./register.component.less']
// })
// export class RegisterComponent implements OnInit {
//     validateForm!: FormGroup;

//     submitForm(): void {
//       for (const i in this.validateForm.controls) {
//         this.validateForm.controls[i].markAsDirty();
//         this.validateForm.controls[i].updateValueAndValidity();
//       }
//     }
  
//     updateConfirmValidator(): void {
//       /** wait for refresh value */
//       Promise.resolve().then(() => this.validateForm.controls.checkPassword.updateValueAndValidity());
//     }
  
//     confirmationValidator = (control: FormControl): { [s: string]: boolean } => {
//       if (!control.value) {
//         return { required: true };
//       } else if (control.value !== this.validateForm.controls.password.value) {
//         return { confirm: true, error: true };
//       }
//       return {};
//     };
  
//     getCaptcha(e: MouseEvent): void {
//       e.preventDefault();
//     }
  
//     constructor(private fb: FormBuilder) {}
  
//     ngOnInit(): void {
//       this.validateForm = this.fb.group({
//         email: [null, [Validators.email, Validators.required]],
//         password: [null, [Validators.required]],
//         checkPassword: [null, [Validators.required, this.confirmationValidator]],
//         nickname: [null, [Validators.required]],
//         phoneNumberPrefix: ['+86'],
//         phoneNumber: [null, [Validators.required]],
//         website: [null, [Validators.required]],
//         captcha: [null, [Validators.required]],
//         agree: [false]
//       });
//     }
// }
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less']
})
export class RegisterComponent implements OnInit {

  form: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.authService.register(this.form).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }
}
