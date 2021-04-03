// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { Router, ActivatedRoute } from '@angular/router';
// import { AuthenticationService } from './auth.service';

// @Component({
//   selector: 'app-login',
//   templateUrl: './login.component.html',
//   styleUrls: ['./login.component.less']
// })
// export class LoginComponent implements OnInit {

//   errorMessage = 'Invalid Credentials';
//   successMessage: string;
//   invalidLogin = false;
//   loginSuccess = false;

//   validateForm!: FormGroup;

//   submitForm(): void {
//     // tslint:disable-next-line: forin
//     for (const i in this.validateForm.controls) {
//       this.validateForm.controls[i].markAsDirty();
//       this.validateForm.controls[i].updateValueAndValidity();
//     }
//   }

//   constructor(
//     private route: ActivatedRoute,
//     private router: Router,
//     private authenticationService: AuthenticationService,
//     private fb: FormBuilder) {   }

//   ngOnInit(): void {
//     this.validateForm = this.fb.group({
//       userName: [null, [Validators.required]],
//       password: [null, [Validators.required]],
//       remember: [true]
//     });
//   }

//   handleLogin(): void {
//     console.log('login request');
//     this.authenticationService.authenticationService(this.validateForm.get('userName').value,
//                                 this.validateForm.get('password').value).subscribe( result => {
//       console.log('login response', result);
//       this.invalidLogin = false;
//       this.loginSuccess = true;
//       this.successMessage = 'Login Successful.';
//       console.log('Login Successful.');
//       this.router.navigate(['../chat']);
//     }, () => {
//       this.invalidLogin = true;
//       this.loginSuccess = false;
//     });
//   }
// }

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { UserService } from '../services/user.service';
import { TokenStorageService } from '../services/token-storage.service';
import { ActivatedRoute } from '@angular/router';
import { AppConstants } from '../common/app.constants';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;
  googleURL = AppConstants.GOOGLE_AUTH_URL;
  facebookURL = AppConstants.FACEBOOK_AUTH_URL;
  githubURL = AppConstants.GITHUB_AUTH_URL;
  linkedinURL = AppConstants.LINKEDIN_AUTH_URL;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService, private route: ActivatedRoute, private userService: UserService) {}

  ngOnInit(): void {
	const token: string = this.route.snapshot.queryParamMap.get('token');
	const error: string = this.route.snapshot.queryParamMap.get('error');
  	if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
    }
  	else if(token){
  		this.tokenStorage.saveToken(token);
  		this.userService.getCurrentUser().subscribe(
  		      data => {
  		        this.login(data);
  		      },
  		      err => {
  		        this.errorMessage = err.error.message;
  		        this.isLoginFailed = true;
  		      }
  		  );
  	}
  	else if(error){
  		this.errorMessage = error;
	    this.isLoginFailed = true;
  	}
  }

  onSubmit(): void {
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.login(data.user);
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  login(user): void {
	this.tokenStorage.saveUser(user);
	this.isLoginFailed = false;
	this.isLoggedIn = true;
	this.currentUser = this.tokenStorage.getUser();
    window.location.reload();
  }

  logout(): void {
    this.tokenStorage.signOut();
    window.location.reload();
  }

}

