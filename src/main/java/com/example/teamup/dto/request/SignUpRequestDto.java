package com.example.teamup.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequestDto {
    @NotBlank(message = "Firstname cannot be empty")
    @Schema(defaultValue = "Chi")
    private String firstName;
    @NotBlank(message = "Lastname cannot be empty")
    @Schema(defaultValue = "Rae")
    private String lastName;
    @NotBlank(message = "Phone number cannot be empty")
    @Schema(defaultValue = "07062126363")
    private String phoneNumber;
    @NotBlank(message = "Email cannot be empty")
    @Schema(defaultValue = "cjoraeki@yahoo.com")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, max = 8)
    @Schema(defaultValue = "12345678")
    private String password;
    private String validOTP;
    private String tokenForEmail;
    @NotBlank
    private String favoriteSports;
    private String username;
}
