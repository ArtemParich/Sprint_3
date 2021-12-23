import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order extends RestAssuredParameters{

    @Override
    public String toString() {
        return "Order{}";
    }

    public static final String URL_ORDER = "/api/v1/orders";
    public static final String URL_ORDER_TRACK = "/api/v1/orders/track";
    public static final String URL_ORDER_ACCEPT = "/api/v1/orders/accept/";

    @Step("Sending POST request to " + URL_ORDER)
    public Response createOrder(VariablesOrder variablesOrder) {
         return given()
                 .spec(getBaseParameters())
                 .body(variablesOrder)
                 .post(URL_ORDER);
    }

    @Step("Sending GET request to " + URL_ORDER)
    public Response getOrderList() {
        return given()
                .spec(getBaseParameters())
                .get(URL_ORDER);
    }

    @Step("Sending GET request to " + URL_ORDER_TRACK + " for get courier id")
    public int getOrderId(int trackOrder) {
        return given()
                .spec(getBaseParameters())
                .queryParam("t", trackOrder)
                .get(URL_ORDER_TRACK).then().extract().body().path("order.id");
    }

    @Step("Sending GET request to " + URL_ORDER_TRACK)
    public Response getOrderByNumber(int orderTrack) {
        return given()
                .spec(getBaseParameters())
                .queryParam("t", orderTrack)
                .get(URL_ORDER_TRACK);
    }

    @Step("Sending GET request to " + URL_ORDER_TRACK + " without number")
    public Response getOrderWithoutNumber() {
        return given()
                .spec(getBaseParameters())
                .queryParam("t", (Object) null)
                .get(URL_ORDER_TRACK);
    }

    @Step("Sending PUT request to " + URL_ORDER_ACCEPT)
    public Response acceptOrder(int courierId, int orderId) {
        return given()
                .spec(getBaseParameters())
                .queryParam("courierId", courierId)
                .put(URL_ORDER_ACCEPT + orderId);
    }

    @Step("Sending PUT request to " + URL_ORDER_ACCEPT + " without courier id")
    public Response acceptOrderWithoutCourierId(int orderId) {
        return given()
                .spec(getBaseParameters())
                .queryParam("courierId", (Object) null)
                .put(URL_ORDER_ACCEPT + orderId);
    }

    @Step("Sending PUT request to " + URL_ORDER_ACCEPT + " without order id")
    public Response acceptOrderWithoutOrderId(int courierId) {
        return given()
                .spec(getBaseParameters())
                .queryParam("courierId", courierId)
                .put(URL_ORDER_ACCEPT);
    }

    @Step ("Getting order track")
    public int getOrderTrack(Order order) {
        return order.createOrder(VariablesOrder.getVariablesOrder()).then().extract().body().path("track");
    }
}
