package org.example.services.security;

import org.example.exception.AccountExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

public interface JwtUserService extends UserService {
    String generateJwtForUser(UserDetails user);

    UserDetails save(String username, String password, String pseudo, String location, String description, String Adresse, MultipartFile profil_picture) throws AccountExistsException;

    UserDetails getUserFromJwt(String jwt);
}
//Remarque : Permet de découpler l'implémentation de la vérification utilisateur avec le JWT, peut-etre mocker dans
//les tests unitaires