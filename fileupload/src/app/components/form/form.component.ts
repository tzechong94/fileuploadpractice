import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  @ViewChild('file') 
  imageFile!: ElementRef;
  imageData!: any;

  form!: FormGroup
  constructor(private http: HttpClient, private fb: FormBuilder,
    private fileSvc: FileService){}

  ngOnInit(): void {
      this.form = this.fb.group({
        'image-file': this.fb.control('')
      })
  }

  upload(){
    const formData = new FormData();
    formData.set('name', this.form.get('image-file')!.value)
    formData.set('file', this.imageFile.nativeElement.files[0])
    firstValueFrom(
      this.http.post("http://localhost:8080/upload", formData)
    ).then((response)=>{ console.log(response)
    }).catch(err => console.log(err))
  }


}
