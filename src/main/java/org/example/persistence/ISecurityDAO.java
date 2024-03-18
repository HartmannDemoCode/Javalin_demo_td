package org.example.persistence;

public interface ISecurityDAO {
    User createUser(String username, String password);
    Role createRole(String role);
    User addRoleToUser(String username, String role);
}
