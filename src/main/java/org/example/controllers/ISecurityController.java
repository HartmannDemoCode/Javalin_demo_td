package org.example.controllers;

import io.javalin.http.Handler;
import org.example.dtos.UserDTO;

import java.util.Set;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public interface ISecurityController {
    Handler register();

    Handler login();

    String createToken(UserDTO user);

    boolean authorize(UserDTO user, Set<String> allowedRoles);

    Handler authenticate();
    UserDTO verifyToken(String token);

}
