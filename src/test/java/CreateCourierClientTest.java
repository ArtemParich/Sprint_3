import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;

public class CreateCourierClientTest {

    public CourierClient courierClient;
    private int courierId;

    @Before
    public void start() {
        courierClient = new CourierClient();
        courierId = 0;
    }

    @Test
    @DisplayName("Checking create courier")
    public void canCreateCourierTest() {
        Courier courier = Courier.getAllVariables();

        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier)
                .then().statusCode(SC_CREATED);
        assertTrue("The response body should be: true", responseCreateCourier.extract().body().path("ok"));

        courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");
    }

    @Test
    @DisplayName("Checking create two identical couriers")
    public void cannotCreateTwoIdenticalCouriersTest() {
        Courier courier = Courier.getAllVariables();
        courierClient.createCourier(courier);

        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier)
                .then().statusCode(SC_CONFLICT);
        String expectedMessage = "Этот логин уже используется";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.extract().body().path("message"));

        courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");
    }

    @Test
    @DisplayName("Checking create courier without login")
    public void cannotCreateCourierWithoutLoginTest() {
        Courier courier = Courier.getPasswordAndName();

        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier)
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The response body should be: " + expectedMessage,expectedMessage,
                responseCreateCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier without password")
    public void cannotCreateCourierWithoutPasswordTest() {
        Courier courier = Courier.getLoginAndName();

        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier)
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The response body should be: " + expectedMessage,expectedMessage,
                responseCreateCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier without first name")
    public void cannotCreateCourierWithoutFirstNameTest() {
        Courier courier = Courier.getLoginAndPassword();

        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier)
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier with busy login")
    public void cannotCreateCourierWithBusyLoginTest() {
        Courier courier = Courier.getAllVariables();
        courierClient.createCourier(courier);

        ValidatableResponse responseCreateCourier = courierClient.createCourier(Courier.getNewPasswordAndName(courier.login))
                .then().statusCode(SC_CONFLICT);
        String expectedMessage = "Этот логин уже используется";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.extract().body().path("message"));

        courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");
    }

    @After
    public void finish() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
         }
    }
}
