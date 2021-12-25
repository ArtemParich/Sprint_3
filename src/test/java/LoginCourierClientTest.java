import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
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
        courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");
    }

    @Test
    @DisplayName("Checking login courier")
    public void canLoginCourierTest() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK);
        assertNotNull("The response body should be: id courier", Integer.toString(courierId));
    }

    @Test
    @DisplayName("Checking login courier without login")
    public void cannotLoginCourierWithoutLoginTest() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.getPasswordWithoutLogin(courier))
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                    responseLoginCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier without password")
    public void cannotLoginCourierWithoutPasswordTest() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.getLoginWithoutPassword(courier))
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect login")
    public void cannotLoginCourierIncorrectLoginTest() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.getPasswordAndIncorrectLogin(courier))
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect password")
    public void cannotLoginCourierIncorrectPasswordTest() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.getLoginAndIncorrectPassword(courier))
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect user")
    public void cannotLoginCourierIncorrectLoginAndPasswordTest() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.getIncorrectLoginAndPassword(courier))
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.extract().body().path("message"));
    }

    @After
    public void finish() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }
}
