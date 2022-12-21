package project.helpers;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
public class CourierClient {
    private final String ROOT = "/api/v1/courier";
    public ValidatableResponse create(Courier courier){
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }
    public ValidatableResponse login(Credentials creds){
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(creds)
                .when()
                .post(ROOT +"/login")
                .then().log().all();
    }
    public ValidatableResponse delete(int courierId) {
        String json = String.format("{\"id\":\"%d\"}", courierId);
       return given().log().all()

                .body(json)
                .when()
                .delete(ROOT + "/" + courierId)
                .then().log().all();
    }}
