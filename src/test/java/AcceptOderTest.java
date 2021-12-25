import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;

public class AcceptOderTest {

    public CourierClient courierClient;
    public Courier courier;
    private int courierId;
    public OrderClient orderClient;
    int orderTrack;
    int orderId;
    private final List<String> color = List.of("BLACK");

    @Before
    public void start() {
        courierClient = new CourierClient();
        courier = Courier.getAllVariables();
        courierClient.createCourier(courier);
        courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");
        orderClient = new OrderClient();
        orderTrack = orderClient.createOrder(Order.getVariablesParamOrder(color))
                .then().statusCode(SC_CREATED)
                .extract().body().path("track");
        orderId = orderClient.getOrderId(orderTrack)
                .then().statusCode(SC_OK)
                .extract().body().path("order.id");
    }

    @Test
    @DisplayName("Checking accept order")
    public void canAcceptOrderTest() {
        ValidatableResponse responseAcceptOrder = orderClient.acceptOrder(courierId, orderId)
                .then().statusCode(SC_OK);
        assertTrue("The response body should be: ", responseAcceptOrder.extract().body().path("ok"));
    }

    @Test
    @DisplayName("Checking accept order without courier id")
    public void cannotAcceptOrderWithoutCourierIdTest() {
        ValidatableResponse responseAcceptOrder = orderClient.acceptOrderWithoutCourierId(orderId)
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking accept order without order id")
    public void cannotAcceptOrderWithoutOrderIdTest() {
        ValidatableResponse responseAcceptOrder = orderClient.acceptOrderWithoutOrderId(courierId)
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking accept order with nonexistent courier id")
    public void cannotAcceptOrderWithNonexistentCourierIdTest() {
        int randomCourierId = Integer.parseInt(RandomStringUtils.randomNumeric(9));

        ValidatableResponse responseAcceptOrder = orderClient.acceptOrder(randomCourierId, orderId)
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Курьера с таким id не существует";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.extract().body().path("message"));
    }

    @Test
    @DisplayName("Checking accept order with nonexistent order id")
    public void cannotAcceptOrderWithNonexistentOrderIdTest() {
        int randomOrderId = Integer.parseInt(RandomStringUtils.randomNumeric(9));

        ValidatableResponse responseAcceptOrder = orderClient.acceptOrder(courierId, randomOrderId)
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Заказа с таким id не существует";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseAcceptOrder.extract().body().path("message"));
    }

    @After
    public void finish() {
        courierClient.deleteCourier(courierId);
     }
}
