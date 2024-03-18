package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String token;
    private String userName;

}
