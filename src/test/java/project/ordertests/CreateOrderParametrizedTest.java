package project.ordertests;

import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.OrderAssertions;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {
    private final List<String> colors;
    private final OrderClient client = new OrderClient();
    private final OrderAssertions check = new OrderAssertions();
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
        ValidatableResponse creationResponse = client.getOrderResponseWithColor(order).then();
        check.orderCreatedSuccessfully(creationResponse);
    }

}
