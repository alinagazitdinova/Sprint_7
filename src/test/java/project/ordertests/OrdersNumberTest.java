package project.ordertests;

import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import util.OrderAssertions;

public class OrdersNumberTest {
    private final OrderAssertions check = new OrderAssertions();
    private final OrderClient client = new OrderClient();
    private final String ROOT = "/api/v1/orders";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check the status code when getting all orders")
    public void getOrderNumberByTrack() { //этот тест для получения всех заказов, проверяем, что статус 200
        ValidatableResponse receivingResponse = client.getAllOrdersResponse().then();
        check.ordersNumberReceivedSuccesfully(receivingResponse);
    }
}
