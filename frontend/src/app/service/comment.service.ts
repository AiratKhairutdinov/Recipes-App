import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const COMMENT_API = 'http://localhost:8080/api/comments/'

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) {
  }

  addComment(recipeId: number, message: string): Observable<any> {
    return this.httpClient.post(COMMENT_API + recipeId, {
      message: message
    });
  }

  getCommentsForRecipe(recipeId: number): Observable<any> {
    return this.httpClient.get(COMMENT_API + recipeId);
  }

  deleteComment(commentId: number): Observable<any> {
    return this.httpClient.delete(COMMENT_API + commentId);
  }
}
