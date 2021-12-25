import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_CREATED;

    @RunWith(Parameterized.class)
    public class CreateOrderClientParameterizationTest {

        public final List<String> color;

        public CreateOrderClientParameterizationTest(List<String> color) {
            this.color = color;
        }

        @Parameterized.Parameters
        public static Object[][] getColor() {

            return new Object[][]{
                    {List.of("BLACK","GREY")},
                    {List.of("BLACK")},
                    {List.of("GREY")},
                    {List.of("")},
            };
        }

        @Test
        @DisplayName("Check create order with different colors / parameterized test")
        public void canCreateOrderWithDifferentColorsParameterizedTest() {
            OrderClient orderClient = new OrderClient();
            Response responseCreateOrder = orderClient.createOrder(Order.getVariablesParamOrder(color));

            int expectedCode = SC_CREATED;
            assertEquals("The code should be: " + expectedCode, expectedCode, responseCreateOrder.statusCode());
            assertNotNull("The response body should be: number track", responseCreateOrder.then().extract().body().path("track"));
         }
    }
