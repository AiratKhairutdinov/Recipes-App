import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Recipe} from "../model/Recipe";

const RECIPE_API = 'http://localhost:8080/api/recipes/';
const RECIPE_CATEGORY_API = 'http://localhost:8080/api/recipeCategory/';


@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  constructor(private httpClient: HttpClient) {
  }

  getAllRecipes(): Observable<any> {
    return this.httpClient.get(RECIPE_API);
  }

  createRecipe(recipe: Recipe): Observable<any> {
    return this.httpClient.post(RECIPE_API, recipe);
  }

  deleteRecipe(recipeId: number): Observable<any> {
    return this.httpClient.delete(RECIPE_API + recipeId);
  }

  getAllRecipesForUser(): Observable<any> {
    return this.httpClient.get(RECIPE_API + 'user/recipes');
  }

  likeRecipe(recipeId: number, username: string): Observable<any> {
    return this.httpClient.post(RECIPE_API + 'like/' + recipeId + '/' + username, null);
  }

  getRecipesByCategory(categoryId: number): Observable<any> {
    return this.httpClient.get(RECIPE_CATEGORY_API + categoryId + '/recipes');
  }

  getRecipesCategories(): Observable<any> {
    return this.httpClient.get(RECIPE_CATEGORY_API);
  }

  getRecipe(recipeId: number): Observable<any> {
    return this.httpClient.get(RECIPE_API + recipeId);
  }

}

