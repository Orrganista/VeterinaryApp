package pl.gr.veterinaryapp.model.dto;

import lombok.Data;

@Data
public class VetUserDto {

    private String username;
    private String password;
    private long role;
}
