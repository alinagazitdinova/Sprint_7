package util;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAssertions {
    @Step("Создание заказа, проверка кода ответа, что в отвеет есть track")
    public String orderCreatedSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(201)
                .body("track", notNullValue()).toString();
    }

    @Step("Получение заказа, проверка кода ответа")
    public ValidatableResponse orderNumberReceivedSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(201);
    }

    @Step("Получение ответа при получении всего кол-ва заказаов")
    public ValidatableResponse ordersNumberReceivedSuccesfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(200);
    }
}
