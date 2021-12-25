import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_OK;

public class GetListOrdersTest {

    @Test
    @DisplayName("Checking getting orders list")
    public void canGetOrdersListTest() {
        OrderClient orderClient = new OrderClient();
        Response responseCreateOrder = orderClient.getOrderList();

        int expectedCode = SC_OK;
        assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateOrder.statusCode());
        assertNotNull("The response body should be: number track", responseCreateOrder.then().extract().body().path("orders"));
    }
}
