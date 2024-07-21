package org.example.controller.auth; // Declaración del paquete donde se encuentra la clase


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
public class AuthResponseDto {
    // Método setter para establecer los detalles del usuario
    // Método getter para obtener los detalles del usuario
    private UserDetails user; // Declaración de un campo para almacenar los detalles del usuario autenticado
    // Método setter para establecer el token de autenticación
    // Método getter para obtener el token de autenticación
    private String token; // Declaración de un campo para almacenar el token de autenticación

    // Constructor de la clase que recibe los detalles del usuario y el token como parámetros
    public AuthResponseDto(UserDetails user, String token) {
        this.user = user;   // Inicialización del campo de detalles del usuario
        this.token = token; // Inicialización del campo de token
    }


}
