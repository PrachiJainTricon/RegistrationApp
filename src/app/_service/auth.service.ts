import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private xmlDataUrl = 'assets/User.xml';

  constructor(private router: Router, private http: HttpClient) {}

  isAunthenticated(): Boolean {
    if (sessionStorage.getItem('token') !== null) {
      return true;
    }
    return false;
  }

  canAcess() {
    if (!this.isAunthenticated()) {
      this.router.navigate(['/login']);
    }
  }

  canAuthenticate(process: string, response?: string) {
    if (process === 'register') {
      if (this.isAunthenticated()) {
        this.router.navigate(['/dashboard']);
      }
    } else if (process === 'login') {
      if (response === 'authenticated') {
        if (this.isAunthenticated()) {
          this.router.navigate(['/dashboard']);
        }
      }
    }
  }

  register(name: string, email: string, password: string) {
    return this.http.post<{ email: string }>(
      'http://localhost:8085/api/user/save',
      { displayName: name, email: email, password: password }
    );
  }

  login(email: string, password: string) {
    return this.http.post<{ authenticate: string }>(
      'http://localhost:8085/api/user/login',
      {
        email,
        password,
      }
    );
  }

  storeResponse(data: string) {
    sessionStorage.setItem('token', data);
  }
  removeToken() {
    sessionStorage.removeItem('token');
  }

  getXmlData(): Observable<any[]> {
    return this.http.get(this.xmlDataUrl, { responseType: 'text' }).pipe(
      map((xmlString) => {
        const parser = new DOMParser();
        const xml = parser.parseFromString(xmlString, 'text/xml');
        const users = Array.from(xml.getElementsByTagName('user'));
        return users.map((user) => {
          const id = user.getElementsByTagName('id')[0].textContent;
          const name = user.getElementsByTagName('name')[0].textContent;
          const city = user.getElementsByTagName('city')[0].textContent;
          const mobileNumber =
            user.getElementsByTagName('mobileNumber')[0].textContent;
          return { id, name, city, mobileNumber };
        });
      })
    );
  }
}
