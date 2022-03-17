import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuardService} from "./service/auth-guard.service";
import {RegisterComponent} from "./component/register/register.component";
import {LoginComponent} from "./component/login/login.component";
import {RecipeDetailsComponent} from "./component/recipe-details/recipe-details.component";
import {RecipeComponent} from "./component/recipe/recipe.component";
import {UserProfileComponent} from "./component/user/user-profile/user-profile.component";
import {AddRecipeComponent} from "./component/user/add-recipe/add-recipe.component";
import {UserRecipesComponent} from "./component/user/user-recipes/user-recipes.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'main', component: RecipeComponent},
  {path: 'category/:id', component: RecipeComponent},
  {path: 'recipes/:id', component: RecipeDetailsComponent},
  {path: 'profile', component: UserProfileComponent, canActivate: [AuthGuardService], children: [
      {path: '', component: UserRecipesComponent, canActivate: [AuthGuardService]},
      {path: 'add', component: AddRecipeComponent, canActivate: [AuthGuardService]}
    ]},
  {path: '', redirectTo: 'main', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
