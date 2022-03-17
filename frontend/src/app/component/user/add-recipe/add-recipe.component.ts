import {Component, OnInit} from '@angular/core';
import {RecipeService} from "../../../service/recipe.service";
import {ImageUploadService} from "../../../service/image-upload.service";
import {Router} from "@angular/router";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Recipe} from "../../../model/Recipe";
import {NotificationService} from "../../../service/notification.service";
import {RecipeCategory} from "../../../model/RecipeCategory";

@Component({
  selector: 'app-add-recipe',
  templateUrl: './add-recipe.component.html',
  styleUrls: ['./add-recipe.component.css']
})
export class AddRecipeComponent implements OnInit {

  recipeForm: FormGroup;
  selectedFile: File;
  isRecipeCreated = false;
  createdRecipe: Recipe;
  previewImageURL: any;
  recipeCategories: RecipeCategory[];


  constructor(
    private recipeService: RecipeService,
    private imageUploadService: ImageUploadService,
    private router: Router,
    private formBuilder: FormBuilder,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.recipeForm = this.createRecipeForm();
    this.listRecipeCategories();
  }

  listRecipeCategories(): void {
    this.recipeService.getRecipesCategories()
      .subscribe(data=>{
        console.log(data);
        this.recipeCategories = data;
      });
  }

  createRecipeForm(): FormGroup {
    return this.formBuilder.group({
      name: new FormControl('', Validators.compose([Validators.required])),
      category: new FormControl('', Validators.compose([Validators.required])),
      description: new FormControl('', Validators.compose([Validators.required])),
      prepTime: new FormControl('', Validators.compose([Validators.required])),
      cookTime: new FormControl('', Validators.compose([Validators.required])),
      servings: new FormControl('', Validators.compose([Validators.required])),
      ingredients: new FormArray([]),
      directions: new FormArray([])
    });
  }

  submit(): void {

    this.recipeService.createRecipe({
      name: this.recipeForm.value.name,
      category: this.recipeForm.value.category,
      description: this.recipeForm.value.description,
      prepTime: this.recipeForm.value.prepTime,
      cookTime: this.recipeForm.value.cookTime,
      servings: this.recipeForm.value.servings,
      ingredients: this.recipeForm.value.ingredients,
      directions: this.recipeForm.value.directions
    }).subscribe(data => {
      this.createdRecipe = data;
      console.log(data);

      if (this.createdRecipe.id != null) {
        this.imageUploadService.uploadImageForRecipe(this.createdRecipe.id, this.selectedFile)
          .subscribe(() => {
            this.notificationService.showSnackBar('Recipe created successfully');
            this.isRecipeCreated = true;
            this.router.navigate(['/profile']);
          });
      }
    });
  }

  onFileSelected(event): void {
    this.selectedFile = event.target.files[0];

    const reader = new FileReader();
    reader.readAsDataURL(this.selectedFile);
    reader.onload = (e) => {
      this.previewImageURL = reader.result;
    };
  }

  get ingredients() {
    return this.recipeForm.get('ingredients') as FormArray;
  }

  addIngredient(){
    const control = new FormControl('', Validators.required);
    this.ingredients.push(control);
  }

  get directions() {
    return this.recipeForm.get('directions') as FormArray;
  }

  addDirection(){
    const control = new FormControl('', Validators.required);
    this.directions.push(control);
  }
}
