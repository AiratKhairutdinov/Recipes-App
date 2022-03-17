import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const IMAGE_API = 'http://localhost:8080/api/images/';

@Injectable({
  providedIn: 'root'
})
export class ImageUploadService {

  constructor(private httpClient: HttpClient) {
  }

  getProfileImage(): Observable<any> {
    return this.httpClient.get(IMAGE_API);
  }

  getRecipeImage(recipeId: number): Observable<any> {
    return this.httpClient.get(IMAGE_API + recipeId);
  }

  uploadImageForUser(file: File): Observable<any> {
    const uploadData = new FormData();
    uploadData.append('file', file);

    return this.httpClient.post(IMAGE_API, uploadData);
  }

  uploadImageForRecipe(recipeId: number, file: File): Observable<any> {
    const uploadData = new FormData();
    uploadData.append('file', file);

    return this.httpClient.post(IMAGE_API + recipeId, uploadData);
  }
}
