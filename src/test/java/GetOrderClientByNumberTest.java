import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
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
        courierId = courierClient.loginCourier(CourierCredentials.getVariablesForAuthorization(courier))
                .then().statusCode(SC_OK)
                .extract().body().path("id");
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Checking getting order by number")
    public void canGetOrderByNumberTest() {
        List<String> color = List.of("GREY");
        int orderTrack = orderClient.createOrder(Order.getVariablesParamOrder(color))
                .then().statusCode(SC_CREATED)
                .extract().body().path("track");

        ValidatableResponse responseGetOrderByNumber = orderClient.getOrderByNumber(orderTrack)
                .then().statusCode(SC_OK);
        assertNotNull("The response body should be: body order", responseGetOrderByNumber.extract().body().path("order"));
    }

    @Test
    @DisplayName("Checking getting order by number without number order")
    public void cannotGetOrderWithoutNumberTest() {
        ValidatableResponse responseGetOrderByNumber = orderClient.getOrderWithoutNumber()
                .then().statusCode(SC_BAD_REQUEST);
        String expectedMessage = "Недостаточно данных для поиска";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrderByNumber.extract().body().path("message"));
     }

    @Test
    @DisplayName("Checking getting order by number with nonexistent number order")
    public void cannotGetOrderWithNonexistentNumberTest() {
        ValidatableResponse responseGetOrderByNumber = orderClient.getOrderByNumber(Integer.parseInt(RandomStringUtils.randomNumeric(9)))
                .then().statusCode(SC_NOT_FOUND);
        String expectedMessage = "Заказ не найден";
        assertEquals("The response body should be: " + expectedMessage, expectedMessage,
                responseGetOrderByNumber.extract().body().path("message"));
     }

    @After
    public void finish() {
        courierClient.deleteCourier(courierId);
     }
}
