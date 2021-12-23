import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class VariablesLogin {
    public String login;
    public String password;

    @Override
    public String toString() {
        return "VariablesLogin{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public VariablesLogin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("Getting login and password for login a courier")
    public static VariablesLogin getVariableLogin(VariablesCreate variablesCreate) {
        return new VariablesLogin(variablesCreate.login, variablesCreate.password);
    }

    @Step("Getting password for login a courier")
    public static VariablesLogin getVariableLoginWithoutLog(VariablesCreate variablesCreate) {
        return new VariablesLogin(null, variablesCreate.password);
    }

    @Step("Getting login for login a courier")
    public static VariablesLogin getVariableLoginWithoutPas(VariablesCreate variablesCreate) {
        return new VariablesLogin(variablesCreate.login, null);
    }

    @Step("Getting password and incorrect login for login a courier")
    public static VariablesLogin getVariableLoginIncorrectLog(VariablesCreate variablesCreate) {
        variablesCreate.login = RandomStringUtils.randomAlphabetic(10);
        return new VariablesLogin(variablesCreate.login, variablesCreate.password);
    }

    @Step("Getting login and incorrect password for login a courier")
    public static VariablesLogin getVariableLoginIncorrectPas(VariablesCreate variablesCreate) {
        variablesCreate.password = RandomStringUtils.randomAlphabetic(10);
        return new VariablesLogin(variablesCreate.login, variablesCreate.password);
    }

    @Step("Getting incorrect login and incorrect password for login a courier")
    public static VariablesLogin getVariableLoginIncorrectLogAndPas(VariablesCreate variablesCreate) {
        variablesCreate.login = RandomStringUtils.randomAlphabetic(10);
        variablesCreate.password = RandomStringUtils.randomAlphabetic(10);
        return new VariablesLogin(variablesCreate.login, variablesCreate.password);
    }
}