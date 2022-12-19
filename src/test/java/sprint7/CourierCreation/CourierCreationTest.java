package sprint7.CourierCreation;
import io.restassured.RestAssured;
// импортируем Before
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
// импортируем Test
import org.junit.Test;
import sprint7.Helpers.CoourierAssertions;
import sprint7.Helpers.Courier;
import sprint7.Helpers.CourierClient;
import sprint7.Helpers.CourierGenerator;


public class CourierCreationTest {
    private final String ROOT = "/api/v1/courier";
    private Courier courier;
    protected final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CoourierAssertions check = new CoourierAssertions();
    private int courierId;
    private String messageInBody;



    @Before
    public void setUp() {

        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    public void courierCreatedSuccesfully() { //Этот тест проверяет, что при создании курьера статус код 201, в ответе есть ok: true
        var courier =  generator.random();
        ValidatableResponse creationResponse = client.create(courier);
        messageInBody = check.createdSuccessfully(creationResponse);
        assert !messageInBody.contains("ok: true");

    }

    @Test
    public void creationFailsWithoutPassword() { //"Этот тест проверяет, что нельзя создать курьера, не указав пароль
        var courier = generator.generic();
        courier.setPassword(null);
        ValidatableResponse loginResponse = client.create(courier);
       String message = check.creationFailed(loginResponse);
       assert !message.isBlank();

    }
    @Test
    public void creationFailsWithoutLogin() { //"Этот тест проверяет, что нельзя создать курьера, не указав логин
        var courier = generator.generic();
        courier.setLogin(null);
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailed(loginResponse);
        assert !message.isBlank();
    }

    @Test
    public void creationFailsWithTheSameLogin() { //"Этот тест проверяет, что нельзя создать 2х курьеров с одинаковым логином
        var courier = generator.generic();
        ValidatableResponse loginResponse = client.create(courier);
        String message = check.creationFailedTheSameLogin(loginResponse);
        assert message.contains("Этот логин уже используется. Попробуйте другой.");
    }
    @After public void deleteCourier(){ //Удаляем курьера
        if (courierId > 0){
        ValidatableResponse response = client.delete(courierId);
        check.deletedSuccesfully(response);
    }}

}
