import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    public String login;
    public String password;
    public String firstName;

    @Override
    public String toString() {
        return "VariablesCreate{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Getting all variables for creating a courier")
    public static Courier getAllVariables() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    @Step("Getting password and first name for creating a courier")
    public static Courier getPasswordAndName() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(null, password, firstName);
    }

    @Step("Getting login and first name for creating a courier")
    public static Courier getLoginAndName() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, null, firstName);
    }

    @Step("Getting login and password for creating a courier")
    public static Courier getLoginAndPassword() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, null);
    }

    @Step("Getting new password and first name for creating a courier")
    public static Courier getNewPasswordAndName(String login) {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }
}
