package project.ordertests;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import project.helpers.Order;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {
    public Order order;
    private final List<String> colors;
    public CreateOrderParametrizedTest(List<String> color) {
                this.colors = color;
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        order = new Order("alina", "Blinchik", "Lenina, 8", "6", "+7 800 355 35 35", "3",  "2022-12-31", "the fastest, please", colors);
    }
    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
        };}
    @Test
    public void orderCreationWithColors() { // этом тесте создаем заказ c казанием двух цветов сразу, также цветов по отдельности, проверяем, что в ответе есть track
        int created = given().log().all()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().log().all()
                .statusCode(201)
                .extract()
                .path("track");
        assert created != 0;
    }
}
