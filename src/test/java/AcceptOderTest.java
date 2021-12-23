import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;

public class AcceptOderTest {

    public Courier courier;
    public VariablesCreate variablesCreate;
    private int courierId;
    public Order order;
    int orderTrack;
    int orderId;

    @Before
    public void start() {
        courier = new Courier();
        variablesCreate = VariablesCreate.getVariablesCreate();
        courier.createCourier(variablesCreate);
        courierId = courier.getCourierId(courier, variablesCreate);
        order = new Order();
        orderTrack = order.getOrderTrack(order);
        orderId = order.getOrderId(orderTrack);
    }

    @Test
    @DisplayName("Checking accept order")
    public void canAcceptOrderTest() {
        Response responseAcceptOrder = order.acceptOrder(courierId, orderId);

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseAcceptOrder.statusCode());
        assertTrue("The response body should be: ", responseAcceptOrder.then().extract().body().path("ok"));
    }

    @Test
    @DisplayName("Checking accept order without courier id")
    public void cannotAcceptOrderWithoutCourierIdTest() {
        Response responseAcceptOrder = order.acceptOrderWithoutCourierId(orderId);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseAcceptOrder.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking accept order without order id")
    public void cannotAcceptOrderWithoutOrderIdTest() {
        Response responseAcceptOrder = order.acceptOrderWithoutOrderId(courierId);

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseAcceptOrder.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking accept order with nonexistent courier id")
    public void cannotAcceptOrderWithNonexistentCourierIdTest() {
        int randomCourierId = Integer.parseInt(RandomStringUtils.randomNumeric(9));
        int orderId = order.getOrderId(orderTrack);

        Response responseAcceptOrder = order.acceptOrder(randomCourierId, orderId);

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Курьера с таким id не существует";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseAcceptOrder.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.then().extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking accept order with nonexistent order id")
    public void cannotAcceptOrderWithNonexistentOrderIdTest() {
        int randomOrderId = Integer.parseInt(RandomStringUtils.randomNumeric(9));

        Response responseAcceptOrder = order.acceptOrder(courierId, randomOrderId);

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Заказа с таким id не существует";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseAcceptOrder.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.then().extract().body().path("message"));
    }

    @After
    public void finish() {
        courier.deleteCourier(courierId);
     }
}
