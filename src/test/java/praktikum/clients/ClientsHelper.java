package praktikum.clients;

import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import praktikum.api_models.create_user.CreateUserRequest;

import static io.restassured.RestAssured.given;
import static praktikum.helpers.Constants.*;

public class ClientsHelper {

    @Step("Delete test user after creating")
    public static void deleteUserApi(String token) {
        given().auth().oauth2(token).when().delete(ENDPOINT_DELETE).then().statusCode(HttpStatus.SC_ACCEPTED);
    }

    @Step("Login test user and get authToken for delete")
    public static String userAuthToken(String email, String password) {
        String jsonBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return given()
                .header("Content-type", "application/json")
                .body(jsonBody)
                .when()
                .post(ENDPOINT_LOGIN)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getString("accessToken")
                .split(" ")[1];
    }

    @Step("Register test user and get authToken for delete")
    public static String createUserAndGetToken(String name, String email, String password) {
        return given()
                .header("Content-type", "application/json")
                .body(new CreateUserRequest(email, password, name))
                .when()
                .post(ENDPOINT_REGISTER)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getString("accessToken")
                .split(" ")[1];
    }

}
