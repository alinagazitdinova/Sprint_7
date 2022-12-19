package sprint7.CourierLogin;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint7.Helpers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class CourierLoginTest {
    private Courier courier;
    private final String ROOT = "/api/v1/courier";
    protected final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CoourierAssertions check = new CoourierAssertions();
    private int courierId;
    private final String noLoginCreds = "{\"login\": \"\", \"password\": \"12347\" }";
    private final String noPasswordCreds = "{\"login\": \"blabla\", \"password\": \"\" }";
    @Before
    public void setUp() {

        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }
    @Test
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
    public void fakeCredsLoggInFailed() { //в этом тесте мы проверяем, что нельзя залогинить несуществующего курьера
        String fakeCreds = "{\"login\": \"unknown\", \"password\": \"12347\" }";
        String message = given().log().all()
                .header("Content-Type", "application/json")
                .body(fakeCreds)
                .when()
                .post(ROOT +"/login")
                .then().log().all()
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"))
                .extract().path("message")
                ;

        assert message.contains("Учетная запись не найдена");
    }
    @Test
    public void loginFailsWithoutLogin() { //этот тест проверяет, без login нельзя залогиниться возвращается текст ошибки

        String message = given().log().all()
                .header("Content-Type", "application/json")
                .body(noLoginCreds)
                .when()
                .post(ROOT +"/login")
                .then().log().all()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"))
                .extract().path("message")
                ;

        assert message.contains("Недостаточно данных для входа");
}
    @Test
    public void loginFailsWithoutPassword() { //этот тест проверяет, без password нельзя залогиниться возвращается текст ошибки

        String message = given().log().all()
                .header("Content-Type", "application/json")
                .body(noPasswordCreds)
                .when()
                .post(ROOT +"/login")
                .then().log().all()
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"))
                .extract().path("message")
                ;

        assert message.contains("Недостаточно данных для входа");
    }
    @After
    public void deleteCourier(){ //Удаляем курьера
        if (courierId > 0){
            ValidatableResponse response = client.delete(courierId);
            check.deletedSuccesfully(response);
        }}

    }
