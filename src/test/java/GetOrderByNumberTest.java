import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.apache.http.HttpStatus.*;

public class GetOrderByNumberTest {

    public Courier courier;
    public VariablesCreate variablesCreate;
    private int courierId;
    public Order order;

    @Before
    public void start() {
        courier = new Courier();
        variablesCreate = VariablesCreate.getVariablesCreate();
        courier.createCourier(variablesCreate);
        courierId = courier.getCourierId(courier, variablesCreate);
        order = new Order();
    }

    @Test
    @DisplayName("Checking getting order by number")
    public void canGetOrderByNumberTest() {
        int orderTrack = order.getOrderTrack(order);
        Response responseGetOrderByNumber = order.getOrderByNumber(orderTrack);

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseGetOrderByNumber.statusCode());
        assertNotNull("The response body should be: body order", responseGetOrderByNumber.then().extract().body().path("order"));
    }

    @Test
    @DisplayName("Checking getting order by number without number order")
    public void cannotGetOrderWithoutNumberTest() {
        Response responseGetOrderByNumber = order.getOrderWithoutNumber();

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseGetOrderByNumber.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrderByNumber.then().extract().body().path("message"));
     }

    @Test
    @DisplayName("Checking getting order by number with nonexistent number order")
    public void cannotGetOrderWithNonexistentNumberTest() {
        Response responseGetOrderByNumber = order.getOrderByNumber(Integer.parseInt(RandomStringUtils.randomNumeric(9)));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Заказ не найден";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseGetOrderByNumber.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrderByNumber.then().extract().body().path("message"));
     }

    @After
    public void finish() {
        courier.deleteCourier(courierId);
     }
}
