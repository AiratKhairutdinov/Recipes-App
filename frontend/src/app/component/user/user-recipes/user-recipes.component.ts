import {Component, OnInit} from '@angular/core';
import {RecipeService} from "../../../service/recipe.service";
import {NotificationService} from "../../../service/notification.service";
import {ImageUploadService} from "../../../service/image-upload.service";
import {Recipe} from "../../../model/Recipe";

@Component({
  selector: 'app-user-recipes',
  templateUrl: './user-recipes.component.html',
  styleUrls: ['./user-recipes.component.css']
})
export class UserRecipesComponent implements OnInit {

  isUserRecipesLoaded = false;
  recipes: Recipe[];

  constructor(
    private recipeService: RecipeService,
    private notificationService: NotificationService,
    private imageService: ImageUploadService) {
  }

  ngOnInit(): void {
    this.recipeService.getAllRecipesForUser()
      .subscribe(data => {
        this.recipes = data;
        this.getImagesToRecipes(this.recipes);
        this.isUserRecipesLoaded = true;
      });
  }

  getImagesToRecipes(recipes: Recipe[]): void {
    recipes.forEach(recipe => {
      this.imageService.getRecipeImage(recipe.id)
        .subscribe(data => {
            recipe.image = data.imageBytes;
          }
        )
    });
  }

  removeRecipe(recipe: Recipe, index: number): void {
    console.log(recipe);
    const result = confirm('Вы действительно хотите удалить рецепт?');

    if (result) {
      this.recipeService.deleteRecipe(recipe.id)
        .subscribe(()=>{
          this.recipes.slice(index, 1);
          this.notificationService.showSnackBar('Рецепт удален');
          window.location.reload();
        });
    }
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }
}
