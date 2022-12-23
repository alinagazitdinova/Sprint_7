package util;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class CoourierAssertions {
    @Step("Создание курьера, проверка кода ответа")
    public String createdSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(201)
                .body("ok", is(true)).toString();
    }

    @Step("Авторизация курьера курьера, проверка кода ответа")
    public int loggedInSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract().path("id");
    }

    @Step("Создание курьера при недостаточных данных")
    public String creationFailed(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"))
                .extract().path("message");
    }

    @Step("Создание курьера с одинаковым логином")
    public String creationFailedTheSameLogin(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."))
                .extract().path("message");
    }

    @Step("Удаление курьера")
    public void deletedSuccesfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("ok", is(true));
    }

    @Step("Создание курьера при недостаточных данных")
    public void loggedInFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Step("Создание курьера при недостаточных данных")
    public void logginFailedWithourLogin(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"))
        ;
    }
}
