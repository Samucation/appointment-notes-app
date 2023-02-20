package com.lambda.appointment.notes;

import com.lambda.appointment.notes.dto.response.CodeExtractedFromGoogleUrlResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class GoogleCloudAuth2Test {

    private static final String GOOGLE_FULL_AUTHENTICATED_URL_WITH_CODE = "https://9a13-191-221-193-141.sa.ngrok.io/?code=4%2F0AWtgzh64nc6bOg0_Fr0L14wf9-56NZ-dqONHagSDjzN7PDhMGtroGXXs7rMzkPkh_uJqAA&scope=email+profile+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
    String googleFullAuthenticatedUrlWithCode = URLDecoder.decode(GOOGLE_CODE_EXPECTED_RESPONSE, StandardCharsets.UTF_8);
    private static final String GOOGLE_CODE_EXPECTED_RESPONSE = "4%2F0AWtgzh64nc6bOg0_Fr0L14wf9-56NZ-dqONHagSDjzN7PDhMGtroGXXs7rMzkPkh_uJqAA";


    //@Test
    public void testExtractOfGoogleCodeUrlForAuthenticate() {
        CodeExtractedFromGoogleUrlResponse response =
                given()
                        .header("accept", "*/*")
                        .queryParam("urlWithCode", GOOGLE_FULL_AUTHENTICATED_URL_WITH_CODE)
                        .when().get("/auth/google/code")
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().body().as(CodeExtractedFromGoogleUrlResponse.class);
        //assertThat(response, is(googleFullAuthenticatedUrlWithCode));
        assertThat("That code is not correct for expected value.",
                response.getCodeExtractedFromUrl(), equalTo(googleFullAuthenticatedUrlWithCode));
    }


}