package com.lambda.appointment.notes.controller;

import com.lambda.appointment.notes.dto.UserDTO;
import com.lambda.appointment.notes.model.User;
import com.lambda.appointment.notes.service.GoogleAuthService;
import com.lambda.appointment.notes.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Path("/auth")
public class GoogleAuthResource {

    @Inject
    GoogleAuthService authService;

    @Inject
    UserService userService;

    @GET
    @Path("/google")
    public Response redirectToGoogleAuth() {
        // Gerar o URL de autenticação do Google e redirecionar o usuário para ele
        String redirectUrl = authService.getGoogleAuthUrl();
        return Response.seeOther(URI.create(redirectUrl)).build();
    }

    @POST
    @Path("/google/callback")
    public Response googleCallback(@QueryParam("code") String code) {
        String accessToken = authService.exchangeCodeForToken(code);
        UserDTO userDTO = authService.getGoogleUserId(accessToken);

        // Verificar se o usuário já está registrado na aplicação
        UserDTO userDataBaseDTO = userService.getUserByGoogleId(userDTO.getGoogleUserId());
        if (userDataBaseDTO == null) {
            userService.addUser(userDTO);
        }

        // Atualizar o token de acesso do Google no usuário
        userDTO.setGoogleToken(accessToken);
        userService.addUser(userDTO);

        // Armazenar o ID do usuário logado na sessão
        // ...

        // Criar a resposta da API com o token de acesso
        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken);
        return Response.ok(response).build();
    }


}
