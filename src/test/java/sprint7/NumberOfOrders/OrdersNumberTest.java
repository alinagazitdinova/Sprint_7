package sprint7.NumberOfOrders;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class OrdersNumberTest {

    private final String ROOT = "/api/v1/orders";
    @Before
    public void setUp() {

        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void getOrderNumberByTrack(){ //этот тест для получения всех заказов, проверяем, что статус 200
    ValidatableResponse numberOrders = given()
            .get(ROOT)
            .then().statusCode(200);


    }

}
