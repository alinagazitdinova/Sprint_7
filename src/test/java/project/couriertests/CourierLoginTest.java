package project.couriertests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import project.helpers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CourierLoginTest {
    protected final CourierGenerator generator = new CourierGenerator();
    private final String ROOT = "/api/v1/courier";
    private final CourierClient client = new CourierClient();
    private final CoourierAssertions check = new CoourierAssertions();
    private final String noLoginCreds = "{\"login\": \"\", \"password\": \"12347\" }";
    private final String noPasswordCreds = "{\"login\": \"blabla\", \"password\": \"\" }";
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
        assert courierId != 0;
    }

    @Test
    @DisplayName("Check the status code when loggin in the non-existing courier")
    public void fakeCredsLoggInFailed() { //в этом тесте мы проверяем, что нельзя залогинить несуществующего курьера
        String fakeCreds = "{\"login\": \"unknown\", \"password\": \"12347\" }";
        String message = given().log().all()
                .header("Content-Type", "application/json")
                .body(fakeCreds)
                .when()
                .post(ROOT + "/login")
                .then().log().all()
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"))
                .extract().path("message");
        assert message.contains("Учетная запись не найдена");
    }

    @Test
    @DisplayName("Check the status code when logging in a courier without login indication in body")
    public void loginFailsWithoutLogin() { //этот тест проверяет, без login нельзя залогиниться возвращается текст ошибки
        String message = given().log().all()
                .header("Content-Type", "application/json")
                .body(noLoginCreds)
                .when()
                .post(ROOT + "/login")
                .then().log().all()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"))
                .extract().path("message");
        assert message.contains("Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Check the status code when logging in a courier without password indication in body")
    public void loginFailsWithoutPassword() { //этот тест проверяет, без password нельзя залогиниться возвращается текст ошибки
        String message = given().log().all()
                .header("Content-Type", "application/json")
                .body(noPasswordCreds)
                .when()
                .post(ROOT + "/login")
                .then().log().all()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"))
                .extract().path("message");
        assert message.contains("Недостаточно данных для входа");
    }

    @After
    public void deleteCourier() { //Удаляем курьера
        if (courierId > 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccesfully(response);
        }
    }
}
