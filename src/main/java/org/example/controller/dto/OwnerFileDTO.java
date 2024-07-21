package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerFileDTO {
    private String username;
    private String email;
    private String location;
    private String telephone;
    private String description;
    private MultipartFile profil_picture;
}
