package project.couriertests;

import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.CoourierAssertions;
import util.CourierGenerator;

public class CourierLoginTest {
    protected final CourierGenerator generator = new CourierGenerator();
    private final String ROOT = "/api/v1/courier";
    private final CourierClient client = new CourierClient();
    private final CoourierAssertions check = new CoourierAssertions();

    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check the status code when logging in a courier with creds, the id in response body")
    public void loggedInReturnId() { //в этом тесте мы создаем и залогиниваем курьера этими данными, проверяем, что вернулось id
        var courier = generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        check.createdSuccessfully(creationResponse);
        Credentials creds = Credentials.from(courier);
        ValidatableResponse loginResponse = client.login(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

    }

    @Test
    @DisplayName("Check the status code when logging in the non-existing courier")
    public void fakeCredsLoggInFailed() { //в этом тесте мы проверяем, что нельзя залогинить несуществующего курьера
        FakeCreds fakeCreds = new FakeCreds();
        ValidatableResponse failResponse = client.loginFakeCreds(fakeCreds);
        check.loggedInFailed(failResponse);

    }

    @Test
    @DisplayName("Check the status code when logging in a courier without login indication in body")
    public void loginFailsWithoutLogin() { //этот тест проверяет, без login нельзя залогиниться возвращается текст ошибки
        NoLoginCreds noLoginCreds = new NoLoginCreds();
        ValidatableResponse noLoginResponse = client.loginNoLogin(noLoginCreds);
        check.logginFailedWithourLogin(noLoginResponse);
    }

    @Test
    @DisplayName("Check the status code when logging in a courier without password indication in body")
    public void loginFailsWithoutPassword() { //этот тест проверяет, без password нельзя залогиниться возвращается текст ошибки
        NoPasswordCreds noPasswordCreds = new NoPasswordCreds();
        ValidatableResponse noPasswordResponse = client.loginNoPassword(noPasswordCreds);
        check.logginFailedWithourLogin(noPasswordResponse);
    }

    @After
    public void deleteCourier() { //Удаляем курьера
        if (courierId > 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccesfully(response);
        }
    }
}
