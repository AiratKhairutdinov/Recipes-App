import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {Recipe} from "../../model/Recipe";
import {RecipeService} from "../../service/recipe.service";
import {UserService} from "../../service/user.service";
import {ImageUploadService} from "../../service/image-upload.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {

  isRecipesLoaded = false;
  recipes: Recipe[];
  isUserDataLoaded = false;
  user: User;

  constructor(
    private recipeService: RecipeService,
    private userService: UserService,
    private imageService: ImageUploadService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listRecipes();
    });
  }


  listRecipes(): void {
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');

    if (hasCategoryId) {
      const categoryId = +this.route.snapshot.paramMap.get('id');
      this.recipeService.getRecipesByCategory(categoryId)
        .subscribe(data => {
          console.log(data);
          this.recipes = data;
          this.getImagesToRecipes(this.recipes);
          this.isRecipesLoaded = true;
        });
    } else {
      this.recipeService.getAllRecipes()
        .subscribe(data => {
          console.log(data);
          this.recipes = data;
          this.getImagesToRecipes(this.recipes);
          this.isRecipesLoaded = true;
        });
    }
  }

  getImagesToRecipes(recipes: Recipe[]): void {
    recipes.forEach(recipe => {
      this.imageService.getRecipeImage(recipe.id)
        .subscribe(data => {
          recipe.image = data.imageBytes;
        });
    })
  }



  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }
}
