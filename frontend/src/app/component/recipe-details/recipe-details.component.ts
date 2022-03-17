import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {RecipeService} from "../../service/recipe.service";
import {ActivatedRoute} from "@angular/router";
import {Recipe} from "../../model/Recipe";
import {ImageUploadService} from "../../service/image-upload.service";
import {NotificationService} from "../../service/notification.service";
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {CommentService} from "../../service/comment.service";

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit {

  recipe: Recipe = new Recipe();
  isUserDataLoaded = false;
  user: User;
  @ViewChild('message') message: ElementRef;

  constructor(
    private recipeService: RecipeService,
    private route: ActivatedRoute,
    private imageService: ImageUploadService,
    private notificationService: NotificationService,
    private userService: UserService,
    private commentService: CommentService
  ) {
  }

  ngOnInit(): void {

    this.userService.getCurrentUser()
      .subscribe(data => {
        console.log(data);
        this.user = data;
        this.isUserDataLoaded = true;
      });

    this.route.paramMap.subscribe(() => {
      this.handleRecipeDetails();
    });


  }

  handleRecipeDetails(): void {
    const recipeId: number = +this.route.snapshot.paramMap.get('id');

    this.recipeService.getRecipe(recipeId)
      .subscribe(data => {
        console.log(data);
        this.recipe = data;
      });
    this.imageService.getRecipeImage(recipeId)
      .subscribe(data => {
        this.recipe.image = data.imageBytes;
      });
    this.commentService.getCommentsForRecipe(recipeId)
      .subscribe(data => {
        this.recipe.comments = data;
      });
  }

  likeRecipe(recipe: Recipe): void {
    if (!this.recipe.usersLiked.includes(this.user.username)) {
      this.recipeService.likeRecipe(recipe.id, this.user.username)
        .subscribe(() => {
          this.recipe.usersLiked.push(this.user.username);
          this.notificationService.showSnackBar('Лайк!');
        });
    } else {
      this.recipeService.likeRecipe(recipe.id, this.user.username)
        .subscribe(() => {
          const index = this.recipe.usersLiked.indexOf(this.user.username, 0);
          if (index > -1) {
            this.recipe.usersLiked.splice(index, 1);
          }
        });
    }
  }

  addComment(recipeId: number, message: string): void {
    this.commentService.addComment(recipeId, message)
      .subscribe(data => {
        console.log(data);
        this.recipe.comments.push(data);
      });
    this.message.nativeElement.value = '';
  }

  deleteComment(commentId: number, index: number): void {
    this.commentService.deleteComment(commentId)
      .subscribe(() => {
        this.recipe.comments.splice(index, 1);
        this.notificationService.showSnackBar('Комментарий удален');
      });
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }

}
