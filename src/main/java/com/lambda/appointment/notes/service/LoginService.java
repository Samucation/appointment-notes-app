package com.lambda.appointment.notes.service;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.mapper.UserMapper;
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

    public Map<String, Object> loginGoogleAccess(String loginAuthenticateCode, Boolean requestRefreshToken) throws Exception {
        String accessToken = authService.exchangeCodeForToken(loginAuthenticateCode, requestRefreshToken);

        UserDTO userDTO = createOrUpdateUserAndSetToken(accessToken);

        // Armazenar o ID do usuário logado na sessão //TODO Precisa implementar uma estrategia de JWT.
        // ...

        return createTokenResponse(accessToken);
    }

    @Transactional
    public UserDTO createOrUpdateUserAndSetToken(String accessToken){
        UserDTO externalGoogleUserDTO = authService.getGoogleUserParams(accessToken);
        UserDTO userDataBaseDTO = userService.getUserByGoogleId(externalGoogleUserDTO.getGoogleUserId());

        if (Objects.isNull(userDataBaseDTO)) {
            return createNewUserWithGoogleUserValuesAndToken(externalGoogleUserDTO, accessToken);
        } else {
            return reloadGoogleTokenAndUpdateUser(externalGoogleUserDTO, accessToken);
        }
    }

    private UserDTO createNewUserWithGoogleUserValuesAndToken(UserDTO externalGoogleUserDTO, String accessToken){
        UserDTO currentUserDTO;
        currentUserDTO = externalGoogleUserDTO;
        currentUserDTO.setCreatedDate(LocalDate.now());
        currentUserDTO.setGoogleToken(accessToken);
        userService.addUser(currentUserDTO);
        return currentUserDTO;
    }

    private UserDTO reloadGoogleTokenAndUpdateUser(UserDTO externalGoogleUserDTO, String accessToken){
        User userInDB = User.findByGoogleUserId(externalGoogleUserDTO.getGoogleUserId());
        if (userInDB != null && userInDB.getGoogleUserId().equals(externalGoogleUserDTO.getGoogleUserId())) {
            userInDB.setGoogleToken(accessToken);
            userService.updateUser(userInDB);
        }
        return UserMapper.toDTO(userInDB);
    }

    private Map<String, Object> createTokenResponse(String accessToken){
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        return response;
    }

}
