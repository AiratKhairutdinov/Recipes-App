import {Comment} from "./Comment";

export class Recipe {
  id?: number;
  username?: string;
  category: string;
  name: string;
  description: string;
  prepTime: number;
  cookTime: number;
  servings: number;
  ingredients: string[];
  directions: string[];
  image?: File;
  likes?: number;
  usersLiked?: string[];
  comments?: Comment [];

}
