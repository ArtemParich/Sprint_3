import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class VariablesCreate {
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

    public VariablesCreate(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Step("Getting all variables for creating a courier")
    public static VariablesCreate getVariablesCreate() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new VariablesCreate(login, password, firstName);
    }

    @Step("Getting password and first name for creating a courier")
    public static VariablesCreate getPasAndName() {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new VariablesCreate(null, password, firstName);
    }

    @Step("Getting login and first name for creating a courier")
    public static VariablesCreate getLogAndName() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new VariablesCreate(login, null, firstName);
    }

    @Step("Getting login and password for creating a courier")
    public static VariablesCreate getLogAndPas() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        return new VariablesCreate(login, password, null);
    }

    @Step("Getting new password and first name for creating a courier")
    public static VariablesCreate getNewPasAndName(String login) {
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = RandomStringUtils.randomAlphabetic(10);
        return new VariablesCreate(login, password, firstName);
    }
}
