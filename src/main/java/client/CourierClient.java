package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.*;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private final String ROOT = "/api/v1/courier";

    @Step("Создан курьер")
    public ValidatableResponse create(Courier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Курьер авторизован данными созданного курьера")
    public ValidatableResponse login(Credentials creds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(creds)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }

    @Step("Курьер удален")
    public ValidatableResponse delete(int courierId) {
        String json = String.format("{\"id\":\"%d\"}", courierId);
        return given().log().all()

                .body(json)
                .when()
                .delete(ROOT + "/" + courierId)
                .then().log().all();
    }

    @Step("Авторизация курьера нечуществующими данными")
    public ValidatableResponse loginFakeCreds(FakeCreds fakeCreds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(fakeCreds)
                .when()
                .post(ROOT + "/login")
                .then().log().all();
    }

    @Step("Авторизация курьера без логина")
    public ValidatableResponse loginNoLogin(NoLoginCreds noLoginCreds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(noLoginCreds)
                .when()
                .post(ROOT + "/login").then();
    }

    @Step("авторизация курьера без пароля")
    public ValidatableResponse loginNoPassword(NoPasswordCreds noPasswordCreds) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(noPasswordCreds)
                .when()
                .post(ROOT + "/login").then();
    }
}
