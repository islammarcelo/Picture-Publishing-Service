# Picture-Publishing-Service
Backend By Spring boot to enable user to upload photo and view it at landing page.
- Landing page which has all accepted images.

## We have two type of user admin and user, Everyone of them has particular flow.

## 🔍 Admin Flow:
- Admin can login by email and password.
- Afther that admin can view uploaded images.
- Admin can accept or reject uploaded images, if admin accept the image, it removes from uploaded images and view in langing page and if admin reject the image remove the image from storage only  and  the data record marked as 
rejected.
- Admin can view image information and display image.


## 🔍 User Flow:
- User can register by email and password.
- After user create account must chose the role (admin or normal user) through endpoint addRoleToUser.
- User can login by eamil and password.
- After that user can upload image through endpoint uploadImage.

## 🎯 Features:
- Java & Spring Boot Framework.
- Spring Security
- Spring Data JPA.
- PostgreSQL Database.
- MVC Architectur Pattern.
- Builder Design Pattern.
- Dependency Injection.
- Unit Test.
- 📝 Writing Clean Code As I Can.
- Postman Documentation.
