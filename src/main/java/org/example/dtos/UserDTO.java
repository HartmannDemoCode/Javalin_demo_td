package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.persistence.User;

import java.util.List;
import java.util.Set;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private Set<String> roles;

    public UserDTO(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRolesAsStrings();
    }
    public UserDTO(String username, Set<String> roleSet){
        this.username = username;
        this.roles = roleSet;
    }
}
