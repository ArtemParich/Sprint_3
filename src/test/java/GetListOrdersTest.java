import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_OK;

public class GetListOrdersTest {

    @Test
    @DisplayName("Checking getting orders list")
    public void canGetOrdersListTest() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse responseCreateOrder = orderClient.getOrderList()
                .then().statusCode(SC_OK);
        assertNotNull("The response body should be: number track", responseCreateOrder.extract().body().path("orders"));
    }
}
