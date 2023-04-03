import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileService } from 'src/app/services/file.service';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit{
  imageData: any;

  constructor(private activatedRoute: ActivatedRoute, private fileSvc: FileService) {}

  ngOnInit(): void {
      const id = this.activatedRoute.snapshot.params['id']
      this.fileSvc.getImage(id).then((r:any)=>{
        this.imageData = r.image
      })
    
      
  }
}


