import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private httpClient: HttpClient) { }

  getImage(id:string){
    return firstValueFrom(
      this.httpClient.get('http://localhost:8080/upload/'+id)
    )
  }
}
