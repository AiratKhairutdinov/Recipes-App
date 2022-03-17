import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from "./material/material-module";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginComponent} from "./component/login/login.component";
import {RegisterComponent} from "./component/register/register.component";
import {authInterceptorProviders} from "./service/auth-interceptor.service";
import {authErrorInterceptorProviders} from "./service/error-interceptor.service";
import {NavigationComponent} from './component/navigation/navigation.component';
import {RecipeComponent} from './component/recipe/recipe.component';
import {RecipeDetailsComponent} from './component/recipe-details/recipe-details.component';
import {UserProfileComponent} from './component/user/user-profile/user-profile.component';
import {UserRecipesComponent} from './component/user/user-recipes/user-recipes.component';
import {UserEditComponent} from './component/user/user-edit/user-edit.component';
import {AddRecipeComponent} from './component/user/add-recipe/add-recipe.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavigationComponent,
    RecipeComponent,
    RecipeDetailsComponent,
    UserProfileComponent,
    UserRecipesComponent,
    UserEditComponent,
    AddRecipeComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [authInterceptorProviders, authErrorInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
