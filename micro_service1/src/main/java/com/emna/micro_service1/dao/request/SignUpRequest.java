package com.emna.micro_service1.dao.request;


import com.emna.micro_service1.entities.Branche;
import com.emna.micro_service1.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private List<Branche> branches;
}