import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredParameters {

    @Override
    public String toString() {
        return "Courier{}";
    }

    public static final String COURIER_URL = "/courier/";

    @Step("Sending POST request to" + COURIER_URL)
    public Response createCourier(Courier courier) {

        return given()
                .spec(getBaseParameters())
                .body(courier)
                .post(COURIER_URL);
    }

    @Step ("Sending POST request to " + COURIER_URL)
    public Response loginCourier(CourierCredentials courierCredentials) {

        return given()
                .spec(getBaseParameters())
                .body(courierCredentials)
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
    public int getCourierId(CourierClient courierClient, Courier courier) {
        return courierClient.loginCourier(CourierCredentials.getVariableLogin(courier)).then().extract().body().path("id");
    }
}
