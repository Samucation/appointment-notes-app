package com.lambda.appointment.notes.dto.response;

import com.lambda.appointment.notes.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleUserIdResponse {

    UserDTO userDTO;
    String externalError;

    public GoogleUserIdResponse(String externalError) {
        this.externalError = externalError;
    }

    public GoogleUserIdResponse(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


}
