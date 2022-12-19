package sprint7.Helpers;

import io.restassured.response.ValidatableResponse;

import static org.hamcrest.Matchers.*;

public class CoourierAssertions {
    public String createdSuccessfully(ValidatableResponse response){
        return response.assertThat()
                .statusCode(201)
                .body("ok", is(true)).toString()


        ;
    }
    public int loggedInSuccessfully(ValidatableResponse response){
       return response.assertThat()
                .statusCode(200)
                .body("id", greaterThan(0))
                .extract().path("id")
        ;
    }

    public void loggedInFailed(ValidatableResponse response) {
        response.assertThat().statusCode(409);
    }

    public String creationFailed(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"))
                .extract().path("message");
    }
    public String creationFailedTheSameLogin(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."))
                .extract().path("message");
    }

    public void deletedSuccesfully(ValidatableResponse response) {
         response.assertThat()
                .statusCode(200)
                .body("ok", is(true))
         ;

    }
}
