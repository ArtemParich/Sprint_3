import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {
    public String login;
    public String password;

    @Override
    public String toString() {
        return "VariablesLogin{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("Getting login and password for login a courier")
    public static CourierCredentials getVariableLogin(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    @Step("Getting password for login a courier")
    public static CourierCredentials getVariableLoginWithoutLog(Courier courier) {
        return new CourierCredentials(null, courier.password);
    }

    @Step("Getting login for login a courier")
    public static CourierCredentials getVariableLoginWithoutPas(Courier courier) {
        return new CourierCredentials(courier.login, null);
    }

    @Step("Getting password and incorrect login for login a courier")
    public static CourierCredentials getVariableLoginIncorrectLog(Courier courier) {
        courier.login = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(courier.login, courier.password);
    }

    @Step("Getting login and incorrect password for login a courier")
    public static CourierCredentials getVariableLoginIncorrectPas(Courier courier) {
        courier.password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(courier.login, courier.password);
    }

    @Step("Getting incorrect login and incorrect password for login a courier")
    public static CourierCredentials getVariableLoginIncorrectLogAndPas(Courier courier) {
        courier.login = RandomStringUtils.randomAlphabetic(10);
        courier.password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(courier.login, courier.password);
    }
}