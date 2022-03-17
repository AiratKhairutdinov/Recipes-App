import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NotificationService} from "../../../service/notification.service";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/User";

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {

  profileFormGroup: FormGroup;

  constructor(
    private dialogRef: MatDialogRef<UserEditComponent>,
    private formBuilder: FormBuilder,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) public data,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    this.profileFormGroup = this.createProfileGroup();
  }

  private createProfileGroup(): FormGroup {
    return this.formBuilder.group({
      firstname: [this.data.user.firstname, Validators.compose([Validators.required])],
      lastname: [this.data.user.lastname, Validators.compose([Validators.required])],
      bio: [this.data.user.bio, Validators.compose([Validators.required])]
    });
  }

  submit(): void {
    this.userService.updateUser(this.updateUser())
      .subscribe(() => {
        this.notificationService.showSnackBar('User was updated successfully');
        this.dialogRef.close();
      });
  }

  private updateUser(): User {
    this.data.user.firstname = this.profileFormGroup.value.firstname;
    this.data.user.lastname = this.profileFormGroup.value.lastname;
    this.data.user.bio = this.profileFormGroup.value.bio;
    return this.data.user;
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
