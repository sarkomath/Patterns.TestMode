import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.*;

class User {
    private final String login;
    private final String password;
    private final String status;

    User(String login, String password, String status) {
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }
}

public class UserGenerator {

    static Faker faker = new Faker(new Locale("en"));

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void request(User user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    // Блок статусов

    // пользователь есть

    public static User active() {
        String status = "active";
        User user = new User(
                faker.name().firstName(),
                faker.internet().password(),
                status);
        request(user);
        return user;
    }
    // пользователь заблокирован

    public static User blocked() {
        String status = "blocked";
        User user = new User(
                faker.name().firstName(),
                faker.internet().password(),
                status);
        request(user);
        return user;
    }

    // неверный логин
    public static User wrongLogin() {
        String status = "active";
        String login = "destroyer666";
        User user = new User(
                login,
                faker.internet().password(),
                status);
        request(user);
        return new User(
                "login",
                faker.internet().password(),
                status);
    }

    // неверный пароль

    public static User wrongPassword() {
        String status = "active";
        String password = "invalid password";
        User user = new User(
                faker.name().firstName(),
                password,
                status);
        request(user);
        return new User(
                faker.name().firstName(),
                "password",
                status);
    }

}
