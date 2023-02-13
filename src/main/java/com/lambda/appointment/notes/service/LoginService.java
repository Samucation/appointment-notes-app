package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApplicationScoped
public class LoginService {

    @Inject
    GoogleAuthService authService;

    @Inject
    UserService userService;

    public Map<String, Object> loginGoogleAccess(String code){
        String accessToken = authService.exchangeCodeForToken(code);

        createOrUpdateUserAndSetToken(accessToken);

        // Armazenar o ID do usuário logado na sessão
        // ...

        return createTokenResponse(accessToken);
    }

    @Transactional
    private void createOrUpdateUserAndSetToken(String accessToken){
        UserDTO externalGoogleUserDTO = authService.getGoogleUserId(accessToken);
        UserDTO userDataBaseDTO = userService.getUserByGoogleId(externalGoogleUserDTO.getGoogleUserId());

        if (Objects.isNull(userDataBaseDTO)) {
            createNewUserWithGoogleUserValuesAndToken(externalGoogleUserDTO, accessToken);
        } else {
            reloadGoogleTokenAndUpdateUser(externalGoogleUserDTO, accessToken);
        }
    }

    private void createNewUserWithGoogleUserValuesAndToken(UserDTO externalGoogleUserDTO, String accessToken){
        UserDTO currentUserDTO;
        currentUserDTO = externalGoogleUserDTO;
        currentUserDTO.setCreatedDate(LocalDate.now());
        currentUserDTO.setGoogleToken(accessToken);
        userService.addUser(currentUserDTO);
    }

    private void reloadGoogleTokenAndUpdateUser(UserDTO externalGoogleUserDTO, String accessToken){
        User userInDB = User.findByGoogleUserId(externalGoogleUserDTO.getGoogleUserId());
        if (userInDB != null && userInDB.getGoogleUserId().equals(externalGoogleUserDTO.getGoogleUserId())) {
            userInDB.setGoogleToken(accessToken);
            userService.updateUser(userInDB);
        }
    }

    private Map<String, Object> createTokenResponse(String accessToken){
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        return response;
    }

}
