package com.lambda.appointment.notes;

import com.lambda.appointment.notes.dto.GoogleAuthConfigDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class ConfigControllerTest {

    static final String MESSAGE_NOT_CORRECT = "That code is not correct for expected value: ";
    static final String CLIENT_ID = "119876353782-5lu09i7mfu1omm23r6s9so1i9m5hiejg.apps.googleusercontent.com";
    static final String AUTH_APPLICATION_URL = "https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id={clientId}&redirect_uri={redirectUri}&scope=openid%20email%20profile&prompt=select_account";
    static final String CLIENT_SECRET = "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";
    static final String HEADER_NAME = "Content-Type";
    static final String TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=";
    static final String REQUEST_URL = "https://oauth2.googleapis.com/token";

    @Test
    public void testGetRedirectUri() {
        GoogleAuthConfigDTO googleAuthConfigDTO =
                given()
                        .header("accept", "*/*")
                        .when().get("/config/google")
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().body().as(GoogleAuthConfigDTO.class);

        String redirectUri = googleAuthConfigDTO.getRedirectUri();

        assertThat(MESSAGE_NOT_CORRECT + "ClientId", googleAuthConfigDTO.getClientId(), equalTo(CLIENT_ID));
        assertThat(MESSAGE_NOT_CORRECT + "GoogleAuthApplicationUrl", googleAuthConfigDTO.getGoogleAuthApplicationUrl(), equalTo(AUTH_APPLICATION_URL));
        assertThat(MESSAGE_NOT_CORRECT + "ClientSecret", googleAuthConfigDTO.getClientSecret(), equalTo(CLIENT_SECRET));
        assertThat(MESSAGE_NOT_CORRECT + "HeaderName", googleAuthConfigDTO.getHeaderName(), equalTo(HEADER_NAME));
        assertThat(MESSAGE_NOT_CORRECT + "TokenInfoUrl", googleAuthConfigDTO.getTokenInfoUrl(), equalTo(TOKEN_INFO_URL));
        assertThat(MESSAGE_NOT_CORRECT + "RequestUrl", googleAuthConfigDTO.getRequestUrl(), equalTo(REQUEST_URL));
        assertThat(MESSAGE_NOT_CORRECT + "redirectUri", redirectUri, equalTo("https://eab5-189-112-50-138.sa.ngrok.io"));
    }

}
