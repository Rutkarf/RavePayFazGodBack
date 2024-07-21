package org.example.controller.security;



import org.springframework.security.core.Authentication;
import org.example.controller.auth.AuthRequestDto;
import org.example.controller.auth.AuthResponseDto;
import org.example.controller.dto.OwnerDTO;
import org.example.exception.AccountExistsException;
import org.example.exception.UnauthorizedException;
import org.example.repository.security.OwnerRepositoryModel;
import org.example.services.impl.JwtUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class SecurityController {
    @Autowired
    private JwtUserServiceImpl userService;
    @PostMapping("/register")
    public ResponseEntity<OwnerDTO> register(@RequestParam("username") String username,
                                             @RequestParam("password") String password,
                                             @RequestParam("pseudo") String pseudo,
                                             @RequestParam("location") String location,
                                             @RequestParam("description") String description,
                                             @RequestParam("adresse") String adresse,
                                             @RequestParam("profil_picture") MultipartFile profil_picture

                                             ) throws AccountExistsException {

        OwnerRepositoryModel user = userService.save(username,password,pseudo,location,description,adresse,profil_picture);

        String token = userService.generateJwtForUser(user);
        return ResponseEntity.ok(new OwnerDTO(user,token));
    }
//Remarque : ajouter un nouvel utilisateur et génère un JWT à la volée






    @PostMapping("/authorize")
    public ResponseEntity<AuthResponseDto> authorize(@RequestBody AuthRequestDto requestDto) throws UnauthorizedException {
        Authentication authentication = null;
        try {
            authentication = userService.authenticate(requestDto.getUsername(),
                    requestDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
// Token generation
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = userService.generateJwtForUser(user);
            return ResponseEntity.ok(new AuthResponseDto(user, token));
        } catch(AuthenticationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

 //   @PostMapping        //insert
//        @PreAuthorize("hasAuthority('ADMIN')")
//        public ResponseEntity<String> add(@RequestParam("username") String username,
//                                          @RequestParam("email") String email,
//                                          @RequestParam("location") String location,
//                                          @RequestParam("telephone") String telephone,
//                                          @RequestParam("description") String description,
//                                          @RequestParam("adresse") String adresse,
//                                          @RequestParam("profil_picture") MultipartFile picture
//        ){
//            if(!picture.isEmpty()){
//                if (userService.uploadPicture(picture)){
//                    userService.save(new OwnerRepositoryModel(username, email, location, telephone, description, adresse, picture));
//                };
//            }
//            return new ResponseEntity<>("Le User " + username +" a été ajoutée", HttpStatus.OK) ;
//        }

//        @GetMapping             //getAll
//        public ArrayList<OwnerGetDTO> findAll(ArrayList<OwnerGetDTO> ownerGetDTO){
//
//            ArrayList<UserServiceModel> userDTO = new ArrayList<>();
//
//            ArrayList<UserServiceModel>  userServiceModels = userService.findAll();
//
//            userServiceModels.forEach((item)->userDTO.add(new UserServiceModel(item.getId().get(), item.getUsername(), item.getEmail(), item.getLocation(), item.getTelephone(), item.getDescription(), item.getAdresse(), item.getProfil_picture())) );
//
//            return ownerGetDTO;
//        }

//        @GetMapping("/{id}")  //findById
//        public ResponseEntity<OwnerGetDTO> findById(@PathVariable Long id){
//            try{
//                UserServiceModel userServiceModel = userService.findById(id);
//                return new ResponseEntity<>(new OwnerGetDTO( userServiceModel.getId().get(), userServiceModel.getUsername(), userServiceModel.getEmail(), userServiceModel.getLocation(), userServiceModel.getTelephone(), userServiceModel.getDescription(), userServiceModel.getAdresse(), userServiceModel.getProfil_picture()), HttpStatus.OK) ;
//
//            }catch(UserNotFoundException ex){
//
//                System.out.println(ex.getReason());
//                throw new ResponseStatusException(
//                        HttpStatus.NOT_FOUND, ex.getReason() );
//
//            }
//        }

//        @PutMapping("/{id}")
//        public void update(@PathVariable Long id, @RequestParam String name){
//            System.out.println(id + " " + name);
//        }
//
//        @DeleteMapping("/{id}")
//        public ResponseEntity <String> delete(@PathVariable Long id){
//            boolean isDelete = userService.delete(id);
//            if(isDelete ) {
//                return new ResponseEntity<>("le user id : " + id + " a été supprimé", HttpStatus.OK);
//            }else{
//                //  throw new NotFoundException(id);
//                return new ResponseEntity<>("le user id : " + id + " n'a pas été trouvé", HttpStatus.NOT_FOUND);
//            }
//        }

//        @DeleteMapping
//        public String deleteAll(){
//            return userService.deleteAll();
//        }
//Remarque: authentifie le principal (le user) à partir du JWT.
}


