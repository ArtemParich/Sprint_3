import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
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

        Response responseCreateCourier = courierClient.createCourier(courier);

        int expectedCode = SC_CREATED;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertTrue("The response body should be: true", responseCreateCourier.then().extract().body().path("ok"));

        courierId = courierClient.getCourierId(courierClient, courier);
    }

    @Test
    @DisplayName("Checking create two identical couriers")
    public void cannotCreateTwoIdenticalCouriersTest() {
        Courier courier = Courier.getAllVariables();

        courierClient.createCourier(courier);
        Response responseCreateCourier = courierClient.createCourier(courier);

        int expectedCode = SC_CONFLICT;
        String expectedMessage = "Этот логин уже используется";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));

        courierId = courierClient.getCourierId(courierClient, courier);
    }

    @Test
    @DisplayName("Checking create courier without login")
    public void cannotCreateCourierWithoutLoginTest() {
        Courier courier = Courier.getPasswordAndName();

        Response responseCreateCourier = courierClient.createCourier(courier);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage,expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier without password")
    public void cannotCreateCourierWithoutPasswordTest() {
        Courier courier = Courier.getLoginAndName();

        Response responseCreateCourier = courierClient.createCourier(courier);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage,expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier without first name")
    public void cannotCreateCourierWithoutFirstNameTest() {
        Courier courier = Courier.getLoginAndPassword();

        Response responseCreateCourier = courierClient.createCourier(courier);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier with busy login")
    public void cannotCreateCourierWithBusyLoginTest() {
        Courier courier = Courier.getAllVariables();

        courierClient.createCourier(courier);
        Response responseCreateCourier = courierClient.createCourier(Courier.getNewPasswordAndName(courier.login));

        int expectedCode = SC_CONFLICT;
        String expectedMessage = "Этот логин уже используется";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));

        courierId = courierClient.getCourierId(courierClient, courier);
    }

    @After
    public void finish() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
         }
    }
}
