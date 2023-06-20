import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private router: Router,
    private http: HttpClient) {

  }

  isAunthenticated(): Boolean {
    if (sessionStorage.getItem('token') !== null) {
      return true;
    }
    return false;
  }

  canAcess() {
    if (!(this.isAunthenticated())) {
      this.router.navigate(['/login'])
    }
  }

  canAuthenticate(process: string, response?: string,) {
    if (process === 'register') {
      if ((this.isAunthenticated())) {
        this.router.navigate(['/dashboard'])
      }
    }
    else if (process === 'login') {
      if (response === 'authenticated') {
        if ((this.isAunthenticated())) {
          this.router.navigate(['/dashboard'])
        }
      }
    }
  }

  register(name: string, email: string, password: string) {
    return this.http
      .post<{ email: string }>("http://localhost:8085/api/user/save",
        { displayName: name, email: email, password: password }
      )
  }

  login(email: string, password: string) {
    return this.http.post<{ authenticate: string }>(
      "http://localhost:8085/api/user/login", {
      email, password
    }
    )
  }

  storeResponse(data: string) {
    sessionStorage.setItem('token', data);
  }
  removeToken() {
    sessionStorage.removeItem('token');
  }


}
