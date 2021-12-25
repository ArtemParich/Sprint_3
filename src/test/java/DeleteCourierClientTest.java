import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;

public class DeleteCourierClientTest {

    public CourierClient courierClient;

    @Before
    public void start() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Checking delete courier")
    public void canDeleteCourierTest() {
        courierClient = new CourierClient();
        Courier courier = Courier.getAllVariables();
        courierClient.createCourier(courier);
        int courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");

        ValidatableResponse responseDeleteCourier = courierClient.deleteCourier(courierId)
                .then().statusCode(SC_OK);
        assertTrue("The response body should be: true", responseDeleteCourier.extract().body().path("ok"));
    }

    @Test
    @DisplayName("Checking delete courier without courier id")
    public void cannotDeleteCourierWithoutIdTest() {
        courierClient = new CourierClient();

        ValidatableResponse responseDeleteCourierWithoutId = courierClient.deleteCourierWithoutCourierId()
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для удаления курьера";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseDeleteCourierWithoutId.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking delete courier with nonexistent courier id")
    public void cannotDeleteCourierWithNonexistentIdTest() {
        int courierId = Integer.parseInt(RandomStringUtils.randomNumeric(9));

        ValidatableResponse responseDeleteCourierWithoutId = courierClient.deleteCourier(courierId)
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Курьера с таким id нет";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseDeleteCourierWithoutId.extract().body().path("message"));
    }
}
