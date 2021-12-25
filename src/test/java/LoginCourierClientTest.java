import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.apache.http.HttpStatus.*;

public class LoginCourierClientTest {

    public CourierClient courierClient;
    public Courier courier;
    private int courierId;

    @Before
    public void start() {
        courierClient = new CourierClient();
        courier = Courier.getAllVariables();
        courierClient.createCourier(courier);
        courierId = courierClient.getCourierId(courierClient, courier);
    }

    @Test
    @DisplayName("Checking login courier")
    public void canLoginCourierTest() {
        Response responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariableLogin(courier));

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertNotNull("The response body should be: id courier", Integer.toString(courierId));
    }

    @Test
    @DisplayName("Checking login courier without login")
    public void cannotLoginCourierWithoutLoginTest() {
        Response responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariableLoginWithoutLog(courier));

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                    responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier without password")
    public void cannotLoginCourierWithoutPasswordTest() {
        Response responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariableLoginWithoutPas(courier));

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect login")
    public void cannotLoginCourierIncorrectLoginTest() {
        Response responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariableLoginIncorrectLog(courier));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect password")
    public void cannotLoginCourierIncorrectPasswordTest() {
        Response responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariableLoginIncorrectPas(courier));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect user")
    public void cannotLoginCourierIncorrectLoginAndPasswordTest() {
        Response responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariableLoginIncorrectLogAndPas(courier));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @After
    public void finish() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}
