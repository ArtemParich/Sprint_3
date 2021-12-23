import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;

public class DeleteCourierTest {

    public Courier courier;

    @Before
    public void start() {
        courier = new Courier();
    }

    @Test
    @DisplayName("Checking delete courier")
    public void canDeleteCourierTest() {
        courier = new Courier();
        VariablesCreate variablesCreate = VariablesCreate.getVariablesCreate();
        courier.createCourier(variablesCreate);
        int courierId = courier.getCourierId(courier, variablesCreate);
        Response responseDeleteCourier = courier.deleteCourier(courierId);

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseDeleteCourier.statusCode());
        assertTrue("The response body should be: true", responseDeleteCourier.then().extract().body().path("ok"));
    }

    @Test
    @DisplayName("Checking delete courier without courier id")
    public void cannotDeleteCourierWithoutIdTest() {
        courier = new Courier();
        Response responseDeleteCourierWithoutId = courier.deleteCourierWithoutId();

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для удаления курьера";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseDeleteCourierWithoutId.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseDeleteCourierWithoutId.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking delete courier with nonexistent courier id")
    public void cannotDeleteCourierWithNonexistentIdTest() {
        int courierId = Integer.parseInt(RandomStringUtils.randomNumeric(9));
        Response responseDeleteCourierWithoutId = courier.deleteCourier(courierId);

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Курьера с таким id нет";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseDeleteCourierWithoutId.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseDeleteCourierWithoutId.then().extract().body().path("message"));
    }
}
