package calendarautomation.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public Response getUser(int userId) {
        RestAssured.baseURI = "https://reqres.in";

        return given()
                .header("x-api-key", ConfigReader.get("reqres.api.key"))
                .pathParam("userId", userId)
                .when()
                .get("/api/users/{userId}")
                .then()
                .extract()
                .response();
    }


}
