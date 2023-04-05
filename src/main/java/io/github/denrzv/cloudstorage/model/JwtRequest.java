package io.github.denrzv.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 5926468583005150707L;
    private String login;
    private String password;
}
