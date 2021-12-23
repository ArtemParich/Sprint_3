import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier extends RestAssuredParameters {

    @Override
    public String toString() {
        return "Courier{}";
    }

    public static final String COURIER_URL = "/api/v1/courier/";

    @Step("Sending POST request to" + COURIER_URL)
    public Response createCourier(VariablesCreate variablesCreate) {

        return given()
                .spec(getBaseParameters())
                .body(variablesCreate)
                .post(COURIER_URL);
    }

    @Step ("Sending POST request to " + COURIER_URL)
    public Response loginCourier(VariablesLogin variablesLogin) {

        return given()
                .spec(getBaseParameters())
                .body(variablesLogin)
                .post(COURIER_URL + "login");
    }

    @Step ("Sending DELETE request to " + COURIER_URL)
    public Response deleteCourier(int courierId) {

        return given()
                .spec(getBaseParameters())
                .delete(COURIER_URL + courierId);
    }

    @Step ("Sending DELETE request to " + COURIER_URL + " without courier id")
    public Response deleteCourierWithoutId() {

        return given()
                .spec(getBaseParameters())
                .delete(COURIER_URL);
    }

    @Step ("Getting courier id")
    public int getCourierId(Courier courier, VariablesCreate variablesCreate) {
        return courier.loginCourier(VariablesLogin.getVariableLogin(variablesCreate)).then().extract().body().path("id");
    }
}
