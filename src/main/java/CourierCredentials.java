import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {
    public String login;
    public String password;

    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials getVariablesForAuthorization(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getPasswordWithoutLogin(Courier courier) {
        return new CourierCredentials(null, courier.password);
    }

    public static CourierCredentials getLoginWithoutPassword(Courier courier) {
        return new CourierCredentials(courier.login, null);
    }

    public static CourierCredentials getPasswordAndIncorrectLogin(Courier courier) {
        courier.login = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getLoginAndIncorrectPassword(Courier courier) {
        courier.password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(courier.login, courier.password);
    }

    public static CourierCredentials getIncorrectLoginAndPassword(Courier courier) {
        courier.login = RandomStringUtils.randomAlphabetic(10);
        courier.password = RandomStringUtils.randomAlphabetic(10);
        return new CourierCredentials(courier.login, courier.password);
    }
}