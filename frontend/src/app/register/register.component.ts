import { DOCUMENT } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit, Renderer2 } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../_service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  formdata={
    name:"",
    email:"",
    password:""
  }

  submit=false;
  errorMessage="";
  loading=false;

  constructor(
    @Inject(DOCUMENT) private readonly document: Document,
    private render: Renderer2,
    private auth:AuthService
  ) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.loading=true;
    this.auth.register(this.formdata.name,this.formdata.email,this.formdata.password).subscribe(
      {
        next:data=>{
          this.auth.storeResponse(data.email);
          console.log('name',data);
          this.auth.canAuthenticate("register");
        }
      }
    ).add(()=>{
      this.loading=false;
    });

  }

  openEye(){
    let y = this.document.getElementById("hands")
    if (y) {
      this.render.setStyle(y, 'visibility', 'hidden')
    }
  }

  closeEye(){
    let x = this.document.getElementById('monkey')
    let y = this.document.getElementById("hands")
    if (x) {
      this.render.setAttribute(x, 'src', '../../assets/monkey_password.gif')
    }
    if (y) {
      this.render.setStyle(y, 'visibility', 'visible')
    }
  }

}
