import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.apache.http.HttpStatus.*;

public class LoginCourierTest {

    public Courier courier;
    public VariablesCreate variablesCreate;
    private int courierId;

    @Before
    public void start() {
        courier = new Courier();
        variablesCreate = VariablesCreate.getVariablesCreate();
        courier.createCourier(variablesCreate);
        courierId = courier.getCourierId(courier, variablesCreate);
    }

    @Test
    @DisplayName("Checking login courier")
    public void canLoginCourierTest() {
        Response responseLoginCourier = courier.loginCourier(VariablesLogin.getVariableLogin(variablesCreate));

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertNotNull("The response body should be: id courier", Integer.toString(courierId));
    }

    @Test
    @DisplayName("Checking login courier without login")
    public void cannotLoginCourierWithoutLoginTest() {
        Response responseLoginCourier = courier.loginCourier(VariablesLogin.getVariableLoginWithoutLog(variablesCreate));

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                    responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier without password")
    public void cannotLoginCourierWithoutPasswordTest() {
        Response responseLoginCourier = courier.loginCourier(VariablesLogin.getVariableLoginWithoutPas(variablesCreate));

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для входа";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect login")
    public void cannotLoginCourierIncorrectLoginTest() {
        Response responseLoginCourier = courier.loginCourier(VariablesLogin.getVariableLoginIncorrectLog(variablesCreate));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect password")
    public void cannotLoginCourierIncorrectPasswordTest() {
        Response responseLoginCourier = courier.loginCourier(VariablesLogin.getVariableLoginIncorrectPas(variablesCreate));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking login courier with incorrect user")
    public void cannotLoginCourierIncorrectLoginAndPasswordTest() {
        Response responseLoginCourier = courier.loginCourier(VariablesLogin.getVariableLoginIncorrectLogAndPas(variablesCreate));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Учетная запись не найдена";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseLoginCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseLoginCourier.then().extract().body().path("message"));
    }

    @After
    public void finish() {
        if (courierId != 0) {
            courier.deleteCourier(courierId);
        }
    }
}
