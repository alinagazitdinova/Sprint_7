package project.ordertests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import project.helpers.Order;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {
    private final List<String> colors;
    public Order order;

    public CreateOrderParametrizedTest(List<String> color) {
        this.colors = color;
    }

    @Parameterized.Parameters(name = "Тестируемый цвет {0}")
    public static Object[][] getData() {
        return new Object[][]{
                {Arrays.asList("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        order = new Order("alina", "Blinchik", "Lenina, 8", "6", "+7 800 355 35 35", "3", "2022-12-31", "the fastest, please", colors);
    }

    @Test
    @DisplayName("Check creating orders with different variations of colors")
    public void orderCreationWithColors() { // этом тесте создаем заказ c казанием двух цветов сразу, также цветов по отдельности, проверяем, что в ответе есть track
        Response response = given().log().all()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .statusCode(201)
                .extract()
                .path("track");
    }

}
