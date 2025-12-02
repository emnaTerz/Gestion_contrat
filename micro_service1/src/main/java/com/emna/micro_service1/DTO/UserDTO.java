package com.emna.micro_service1.DTO;

import com.emna.micro_service1.entities.Branche;
import com.emna.micro_service1.entities.Role;

import java.util.List;


public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private Role role;

    private List<Branche> branches;
    // Constructors
    public UserDTO() {}



    public UserDTO(String email, String firstName, String lastName, Role role, List<Branche> branches) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.branches = branches;
    }





    // Getters & Setters
    public List<Branche> getBranches() { return branches; }
    public void setBranches(List<Branche> branches) { this.branches = branches; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }


}
