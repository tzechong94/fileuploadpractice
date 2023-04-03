import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormComponent } from './components/form/form.component';
import { ViewComponent } from './components/view/view.component';

const routes: Routes = [
  { path: "", component: FormComponent },
  { path: "get/:id", component: ViewComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
