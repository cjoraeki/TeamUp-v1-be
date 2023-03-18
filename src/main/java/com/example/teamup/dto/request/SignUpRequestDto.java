package com.example.teamup.dto.request;

import com.example.teamup.enums.Gender;
import com.example.teamup.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequestDto {
    @NotBlank(message = "Firstname cannot be empty")
    @Schema(defaultValue = "Chi")
    private String firstName;
    @NotNull(message = "Lastname cannot be empty")
    @Schema(defaultValue = "Rae")
    private String lastName;
    @NotNull(message = "Phone number cannot be empty")
    @Schema(defaultValue = "07062126363")
    private String phoneNumber;
    @NotNull(message = "Email cannot be empty")
    @Schema(defaultValue = "cjoraeki@yahoo.com")
    private String email;
    @NotNull(message = "Password cannot be blank")
    @Schema(defaultValue = "12345678")
    private String password;
    private String validOTP;
    private String tokenForEmail;
    private String favoriteSports;
    private String username;
}
