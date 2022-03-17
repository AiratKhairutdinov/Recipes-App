import {Component, OnInit} from '@angular/core';
import {User} from "../../model/User";
import {TokenStorageService} from "../../service/token-storage.service";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {RecipeCategory} from "../../model/RecipeCategory";
import {RecipeService} from "../../service/recipe.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  recipeCategories: RecipeCategory[];
  isLoggedIn = false;
  isDataLoaded = false;
  user: User;

  constructor(
    private tokenService: TokenStorageService,
    private userService: UserService,
    private recipeService: RecipeService,
    private router: Router) {
  }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenService.getToken();

    if (this.isLoggedIn) {
      this.userService.getCurrentUser()
        .subscribe(data => {
          this.user = data;
          this.isDataLoaded = true;
        });
    }

    this.listRecipeCategories();
  }

  listRecipeCategories(): void {
    this.recipeService.getRecipesCategories()
      .subscribe(data=>{
        console.log(data);
        this.recipeCategories = data;
      });
  }

  logOut(): void {
    this.tokenService.logOut();
    this.router.navigate(['/login']);
  }

}
