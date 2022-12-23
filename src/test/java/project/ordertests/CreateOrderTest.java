package project.ordertests;

import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.OrderNoColor;
import org.junit.Before;
import org.junit.Test;
import util.OrderAssertions;

public class CreateOrderTest {
    private final OrderClient client = new OrderClient();
    private final OrderAssertions check = new OrderAssertions();
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
        ValidatableResponse creationResponse = client.getOrderResponse(orderNoColor).then();
        check.orderCreatedSuccessfully(creationResponse);
    }


}
