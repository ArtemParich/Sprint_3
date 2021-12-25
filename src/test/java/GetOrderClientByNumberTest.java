import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;
import static org.apache.http.HttpStatus.*;

public class GetOrderClientByNumberTest {

    public CourierClient courierClient;
    public Courier courier;
    private int courierId;
    public OrderClient orderClient;

    @Before
    public void start() {
        courierClient = new CourierClient();
        courier = Courier.getAllVariables();
        courierClient.createCourier(courier);
        courierId = courierClient.getCourierId(courierClient, courier);
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Checking getting order by number")
    public void canGetOrderByNumberTest() {
        List<String> color = List.of("GREY");
        int orderTrack = orderClient.getOrderTrack(orderClient, color);
        Response responseGetOrderByNumber = orderClient.getOrderByNumber(orderTrack);

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseGetOrderByNumber.statusCode());
        assertNotNull("The response body should be: body order", responseGetOrderByNumber.then().extract().body().path("order"));
    }

    @Test
    @DisplayName("Checking getting order by number without number order")
    public void cannotGetOrderWithoutNumberTest() {
        Response responseGetOrderByNumber = orderClient.getOrderWithoutNumber();

        int expectedCode = SC_BAD_REQUEST;
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseGetOrderByNumber.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrderByNumber.then().extract().body().path("message"));
     }

    @Test
    @DisplayName("Checking getting order by number with nonexistent number order")
    public void cannotGetOrderWithNonexistentNumberTest() {
        Response responseGetOrderByNumber = orderClient.getOrderByNumber(Integer.parseInt(RandomStringUtils.randomNumeric(9)));

        int expectedCode = SC_NOT_FOUND;
        String expectedMessage = "Заказ не найден";
        assertEquals("The code should be: " + expectedCode, expectedCode, responseGetOrderByNumber.statusCode());
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrderByNumber.then().extract().body().path("message"));
     }

    @After
    public void finish() {
        courierClient.deleteCourier(courierId);
     }
}
