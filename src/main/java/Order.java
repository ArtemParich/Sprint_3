import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.valueOf;

public class Order {
    @Override
    public String toString() {
        return "VariablesOrder{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", metroStation='" + metroStation + '\'' +
                ", phone='" + phone + '\'' +
                ", renTime=" + renTime +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", comment='" + comment + '\'' +
                ", color=" + color +
                '}';
    }

    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int renTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;

    public Order(String firstName, String lastName, String address, String metroStation,
                 String phone, int renTime, String deliveryDate, String comment, List<String> color) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.renTime = renTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Step("Getting all variables for creating a order / parameterized test")
    public static Order getVariablesParamOrder(List<String> color) {
        // динамические переменные для теста
        String firstName = RandomStringUtils.randomAlphabetic(10); //имя
        String lastName = RandomStringUtils.randomAlphabetic(10); // фамилия
        String address = RandomStringUtils.randomAlphabetic(15); // адрес
        String metroStation = valueOf(ThreadLocalRandom.current().nextInt(1,225)); // станция метро (рандомный выбор станции из 224 имеющихся)
        String phone = RandomStringUtils.randomNumeric(12); // номер телефона
        int renTime = ThreadLocalRandom.current().nextInt(1,7); // время аренды (1-6 дней)
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, +ThreadLocalRandom.current().nextInt(1,8));
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); // дата доставки: прибавляем от 1 до 7 дней от даты создания заказа
        String comment = RandomStringUtils.randomAlphabetic(20); // комментарий
        return new Order(firstName, lastName, address, metroStation, phone, renTime, deliveryDate, comment, color);
    }

    @Step("Getting all variables for creating a order")
    public static Order getVariablesOrder() {
        // динамические переменные для теста
        String firstName = RandomStringUtils.randomAlphabetic(10); //имя
        String lastName = RandomStringUtils.randomAlphabetic(10); // фамилия
        String address = RandomStringUtils.randomAlphabetic(15); // адрес
        String metroStation = valueOf(ThreadLocalRandom.current().nextInt(1,225)); // станция метро (рандомный выбор станции из 224 имеющихся)
        String phone = RandomStringUtils.randomNumeric(12); // номер телефона
        int renTime = ThreadLocalRandom.current().nextInt(1,7); // время аренды (1-6 дней)
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, +ThreadLocalRandom.current().nextInt(1,8));
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()); // дата доставки: прибавляем от 1 до 7 дней от даты создания заказа
        String comment = RandomStringUtils.randomAlphabetic(20); // комментарий
        final List<String> twoColors = List.of("BLACK", "GREY"); //  рандомный выбор цвета из списка вариантов
        final List<String> blackColors = List.of("BLACK");
        final List<String> greyColors = List.of("GREY");
        final List<String> nullColors = List.of("");
        final List<List<String>> allColors = List.of(twoColors, blackColors, greyColors, nullColors);
        Random random = new Random();
        List<String> color = allColors.get(random.nextInt(allColors.size()));
        return new Order(firstName, lastName, address, metroStation, phone, renTime, deliveryDate, comment, color);
    }
}
