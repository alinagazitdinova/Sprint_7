package project.ordertests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import model.OrderNoColor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

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
        Response response =
        given().log().all()
                .header("Content-Type", "application/json")
                .body(orderNoColor)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .statusCode(201)
                .extract()
                .path("track");
       ;
    }
}
