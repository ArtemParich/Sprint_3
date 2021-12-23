import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;

public class CreateCourierTest {

    public Courier courier;
    private int courierId;

    @Before
    public void start() {
        courier = new Courier();
        courierId = 0;
    }

    @Test
    @DisplayName("Checking create courier")
    public void canCreateCourierTest() {
        VariablesCreate variablesCreate = VariablesCreate.getVariablesCreate();

        Response responseCreateCourier = courier.createCourier(variablesCreate);

        int expectedCode = SC_CREATED;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertTrue("The response body should be: true", responseCreateCourier.then().extract().body().path("ok"));

        courierId = courier.getCourierId(courier, variablesCreate);
    }

    @Test
    @DisplayName("Checking create two identical couriers")
    public void cannotCreateTwoIdenticalCouriersTest() {
        VariablesCreate variablesCreate = VariablesCreate.getVariablesCreate();

        courier.createCourier(variablesCreate);
        Response responseCreateCourier = courier.createCourier(variablesCreate);

        int expectedCode = SC_CONFLICT;
        String expectedMessage = "Этот логин уже используется";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));

        courierId = courier.getCourierId(courier, variablesCreate);
    }

    @Test
    @DisplayName("Checking create courier without login")
    public void cannotCreateCourierWithoutLoginTest() {
        VariablesCreate variablesCreate = VariablesCreate.getPasAndName();

        Response responseCreateCourier = courier.createCourier(variablesCreate);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage,expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier without password")
    public void cannotCreateCourierWithoutPasswordTest() {
        VariablesCreate variablesCreate = VariablesCreate.getLogAndName();

        Response responseCreateCourier = courier.createCourier(variablesCreate);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage,expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier without first name")
    public void cannotCreateCourierWithoutFirstNameTest() {
        VariablesCreate variablesCreate = VariablesCreate.getLogAndPas();

        Response responseCreateCourier = courier.createCourier(variablesCreate);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking create courier with busy login")
    public void cannotCreateCourierWithBusyLoginTest() {
        VariablesCreate variablesCreate = VariablesCreate.getVariablesCreate();

        courier.createCourier(variablesCreate);
        Response responseCreateCourier = courier.createCourier(VariablesCreate.getNewPasAndName(variablesCreate.login));

        int expectedCode = SC_CONFLICT;
        String expectedMessage = "Этот логин уже используется";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateCourier.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseCreateCourier.then().extract().body().path("message"));

        courierId = courier.getCourierId(courier, variablesCreate);
    }

    @After
    public void finish() {
        if (courierId != 0) {
            courier.deleteCourier(courierId);
         }
    }
}
