package project.ordertests;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import project.helpers.OrderNoColor;

import static io.restassured.RestAssured.given;

public class CreateOrderTest {
    public OrderNoColor orderNoColor;
    public String color = null;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        orderNoColor = new OrderNoColor("alina", "Blinchik", "Lenina, 8", "6", "+7 800 355 35 35", "3", "2022-12-31", "the fastest, please", color);
    }

    @Test
    @DisplayName("Check creation of order without color")
    public void orderCreation() { // этом тесте создаем заказ без указания цвета , проверяем, что в ответе есть track
        int created = given().log().all()
                .header("Content-Type", "application/json")
                .body(orderNoColor)
                .when()
                .post("/api/v1/orders")
                .then().log().all()
                .statusCode(201)
                .extract()
                .path("track");
        assert created != 0;
    }
}
