import { DOCUMENT } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../_service/auth.service';
import { error } from 'console';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  formdata = {
    email: "",
    password: ""
  }
  submit = false;
  loading = false;
  errorMessage = ""

  constructor(
    @Inject(DOCUMENT) private readonly document: Document,
    private render: Renderer2,
    private auth:AuthService
  ) { }


  ngOnInit(): void {
  }


  onSubmit() {
    this.loading = true;
    this.auth.login(this.formdata.email,this.formdata.password).subscribe(
      {
        next:data=>{
          this.auth.storeResponse(data.authenticate);
          this.auth.canAuthenticate("login",data.authenticate,);
        },
        error:data=>{
          alert(data.message)
        }
      }
    ).add(()=>{
      this.loading=false;
    });
  }

  openEye() {
    let y = this.document.getElementById("hands")
    if (y) {
      this.render.setStyle(y, 'visibility', 'hidden')
    }
  }

  closeEye() {
    let x = this.document.getElementById('monkey')
    let y = this.document.getElementById("hands")
    if (x) {
      this.render.setAttribute(x, 'src', '../../assets/monkey_password.gif')
    }
    if (y) {
      this.render.setStyle(y, 'visibility', 'visible')
    }
  }

  wrongPWD() {
    let x = this.document.getElementById("monkey-anim");
    let y = this.document.getElementById("hands")
    if (x) {
      this.render.setAttribute(x, 'src', '../../assets/wrong.gif')
    }
    if (y) {
      this.render.setStyle(y, 'visibility', 'hidden')
    }
  }



}
