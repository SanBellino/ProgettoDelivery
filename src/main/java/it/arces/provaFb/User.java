package it.arces.provaFb;

public class User {
    
   private int id;
   private String user;
   private String password;
   private String email;
   private String role;

   public User(){   
   }
   
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getUser() {
      return user;
   }
   public void setUser(String user) {
      this.user = user;
   }
   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail(){
    return email;
   }
   public void setEmail(String email){
    this.email = email;
   }
   public String getRole(){
    return role;
   }
   public void setRole(String role){
    this.role = role;
   }
   }