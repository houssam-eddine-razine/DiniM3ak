package com.example.DeniM3ak.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String firstname;
    private String phone;
    private String birthdate;
    private boolean prefSmoking;
    private boolean prefAnimals;
    private boolean prefTalk;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public boolean isPrefSmoking() { return prefSmoking; }
    public void setPrefSmoking(boolean prefSmoking) { this.prefSmoking = prefSmoking; }

    public boolean isPrefAnimals() { return prefAnimals; }
    public void setPrefAnimals(boolean prefAnimals) { this.prefAnimals = prefAnimals; }

    public boolean isPrefTalk() { return prefTalk; }
    public void setPrefTalk(boolean prefTalk) { this.prefTalk = prefTalk; }
}
