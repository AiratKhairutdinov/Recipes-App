import {Component, OnInit} from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {NotificationService} from "../../../service/notification.service";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/User";
import {ImageUploadService} from "../../../service/image-upload.service";
import {UserEditComponent} from "../user-edit/user-edit.component";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  isUserDataLoaded = false;
  user: User;
  selectedFile: File;
  userProfileImage: File;
  previewImgURL: any;

  constructor(
    private dialog: MatDialog,
    private notificationService: NotificationService,
    private imageService: ImageUploadService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getCurrentUser()
      .subscribe(data => {
        this.user = data;
        this.isUserDataLoaded = true;
      });

    this.imageService.getProfileImage()
      .subscribe(data => {
        this.userProfileImage = data.imageBytes;
      });
  }

  onFileSelected(event): void {
    this.selectedFile = event.target.files[0];

    const reader = new FileReader();
    reader.readAsDataURL(this.selectedFile);

    reader.onload = () => {
      this.previewImgURL = reader.result;
    };
  }

  openEditDialog(): void {
    const dialogUserEditConfig = new MatDialogConfig();
    dialogUserEditConfig.width = '400px';
    dialogUserEditConfig.data = {
      user: this.user
    }
    this.dialog.open(UserEditComponent, dialogUserEditConfig);
  }

  formatImage(img: any): any {
    if (img == null) {
      return null;
    }
    return 'data:image/jpeg;base64,' + img;
  }

  onUpload(): void {
    if (this.selectedFile != null) {
      this.imageService.uploadImageForUser(this.selectedFile)
        .subscribe(() => {
          this.notificationService.showSnackBar('Profile Image updated successfully');
          window.location.reload();
        });
    }
  }

}
