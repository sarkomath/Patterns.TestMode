import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;

public class TestMode {

    @BeforeEach
    void opening() {
        open("http://localhost:9999");
    }

    @Test
    // пользователь есть
    void activeUserTest() {
        User user = UserGenerator.active();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(byText("Продолжить")).click();
        $(withText("Личный кабинет")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    // пользователь заблокирован
    void userStatusTest() {
        User user = UserGenerator.blocked();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Пользователь заблокирован")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    // неверный логин
    void invalidUsernameTest() {
        User user = UserGenerator.wrongLogin();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }

    @Test
    // неверный пароль
    void invalidPasswordTest() {
        User user = UserGenerator.wrongPassword();
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $(withText("Продолжить")).click();
        $(withText("Неверно указан логин или пароль")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}
