# Ecommerce with Spring Boot

This is a **fully functional e-commerce application built with Spring Boot**, deployed and running in production.  
It’s actively being improved with new features, better UX, and deeper backend logic.

> ✨ **Live demo**: [https://ecommerce-with-spring-boot.onrender.com](https://ecommerce-with-spring-boot.onrender.com)

---

## 🚀 Current Features (Working in Production)

✅ **User System**  
- Secure registration & login (Spring Security + BCrypt)  
- Role-based access (USER / ADMIN)  
- Password reset via email  

✅ **Product Catalog**  
- Dynamic categories with images  
- Product search & filtering  
- Real-time image display from cloud storage  

✅ **Shopping Cart**  
- Add/remove items  
- Increase/decrease quantity  
- Automatic stock management  
- Empty cart handling  

✅ **Admin Dashboard**  
- Manage products & categories  
- Upload product/category images  

✅ **Deployment Ready**  
- Runs locally and on **Render**  
- Handles file uploads, emails, and database persistence in the cloud  

---

## 🛠️ Technologies Used

- **Backend**: Java 17, Spring Boot, Spring Security, Spring Data JPA, Hibernate  
- **Frontend**: Thymeleaf, Bootstrap 5, HTML/CSS/JS  
- **Database**: MySQL (compatible with Render PostgreSQL via dialect)  
- **Build**: Maven  
- **Deployment**: Render (cloud), Docker-ready  

---

## ▶️ How to Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/MiguelAntonioRS/Ecommerce-with-Spring.git

2. Configure your database in src/main/resources/application.properties:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=your_user
   spring.datasource.password=your_pass   
   ```

3. Set up Gmail for password reset:
   ```
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_app_password
   ```

4. Run the app:
   ```
   mvn spring-boot:run
   ```

5. Visit:
   ```
   👉 http://localhost:8080
   ```

### Author

   Built in public • By Miguel Antonio • Cuban Backend Developer 💪
   "Learning by doing, failing, and shipping."
