package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;
import model.OrderNoColor;

import static io.restassured.RestAssured.given;

public class OrderClient {

    @Step("Оформление заказа без указания цвета")
    public Response getOrderResponse(OrderNoColor orderNoColor) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderNoColor)
                .when()
                .post("/api/v1/orders");

    }

    @Step("Оформление заказа с указанием разных цветов")
    public Response getOrderResponseWithColor(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");

    }

    @Step("Получение всех заказов")
    public Response getAllOrdersResponse() {
        return given()
                .get("/api/v1/orders");

    }

}


