import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/User";

const USER_API = 'http://localhost:8080/api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  getCurrentUser(): Observable<any> {
    return this.httpClient.get(USER_API);
  }

  getUserById(id: number): Observable<any> {
    return this.httpClient.get(USER_API + id);
  }

  updateUser(user: User): Observable<any> {
    return this.httpClient.post(USER_API, user);
  }

}
