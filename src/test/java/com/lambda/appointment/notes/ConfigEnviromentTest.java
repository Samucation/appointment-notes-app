package com.lambda.appointment.notes;

import com.lambda.appointment.notes.dto.ConfigEnvironmentDTO;
import com.lambda.appointment.notes.dto.GoogleAuthConfigDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class ConfigEnviromentTest {

    static final String SQL_SERVER_USER = "sa";
    static final String SQL_SERVER_PASS = "Pass_123";
    static final String SQL_SERVER_DB_URL = "jdbc:sqlserver://localhost:1433;encrypt=false;databaseName=spine;";
    static final String SCHEMA_DB_NAME = "appointmentNotes";
    static final String ERROR_MSG = "Test Enviroment values. That code is not correct for expected value.";


    @Test
    public void testGetRedirectUri() {
        ConfigEnvironmentDTO configEnvironmentDTO =
                given()
                        .header("accept", "*/*")
                        .when().get("/config/environments")
                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().body().as(ConfigEnvironmentDTO.class);

        String redirectUri = configEnvironmentDTO.getSqlServerDbUrl();

        assertThat(ERROR_MSG, configEnvironmentDTO.getSqlServerDataBaseUser(), equalTo(SQL_SERVER_USER));
        assertThat(ERROR_MSG , configEnvironmentDTO.getSqlServerDataBasePass(), equalTo(SQL_SERVER_PASS));
        assertThat(ERROR_MSG, configEnvironmentDTO.getSqlServerDbUrl(), equalTo(SQL_SERVER_DB_URL));
        assertThat(ERROR_MSG, configEnvironmentDTO.getSqlServerDataBaseSchemaName(), equalTo(SCHEMA_DB_NAME));
    }

}
