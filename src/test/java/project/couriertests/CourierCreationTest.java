package project.couriertests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.CoourierAssertions;
import model.Courier;
import client.CourierClient;
import util.CourierGenerator;



public class CourierCreationTest {
    protected final CourierGenerator generator = new CourierGenerator();
    private final String ROOT = "/api/v1/courier";
    private final CourierClient client = new CourierClient();
    private final CoourierAssertions check = new CoourierAssertions();
    private Courier courier;
    private int courierId;
    private String messageInBody;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check the status code when creating a courier, ok: true in the response body")

    public void courierCreatedSuccesfully() { //Этот тест проверяет, что при создании курьера статус код 201, в ответе есть ok: true
        var courier = generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        messageInBody = check.createdSuccessfully(creationResponse);

    }

    @Test
    @DisplayName("Check the status code when creating a courier without password indication in body")

    public void creationFailsWithoutPassword() { //"Этот тест проверяет, что нельзя создать курьера, не указав пароль
        var courier = generator.generic();
        courier.setPassword(null);
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailed(loginResponse);
    }

    @Test
    @DisplayName("Check the status code when creating a courier without login indication in body")
    public void creationFailsWithoutLogin() { //"Этот тест проверяет, что нельзя создать курьера, не указав логин
        var courier = generator.generic();
        courier.setLogin(null);
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailed(loginResponse);
        ;
    }

    @Test
    @DisplayName("Check the status code when creating a courier with the same login")
    public void creationFailsWithTheSameLogin() { //"Этот тест проверяет, что нельзя создать 2х курьеров с одинаковым логином
        var courier = generator.generic();
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailedTheSameLogin(loginResponse);
    }

    @After
    public void deleteCourier() { //Удаляем курьера
        if (courierId > 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccesfully(response);
        }
    }
}
