package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static ru.netology.data.DataGenerator.*;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldActiveUser(){
        UserInfo user = DataGenerator.Registration.generateUser("active");
        activeUser(user);
        $("[type=\"text\"]").setValue(user.getLogin());
        $("[type=\"password\"]").setValue(user.getPassword());
        $(".button").click();
        $(withText("Личный кабинет")).shouldBe((Condition.visible));
    }

    @Test
    void shouldBlockedUser(){
        UserInfo user = DataGenerator.Registration.generateUser("blocked");
        blockedUser(user);
        $("[type=\"text\"]").setValue(user.getLogin());
        $("[type=\"password\"]").setValue(user.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe((Condition.visible));
    }

    @Test
    void shouldInvalidLogin(){
        UserInfo user = Registration.generateUser("active");
        activeUser(user);
        var invalidLogin = setLogin();
        $("[type=\"text\"]").setValue(invalidLogin);
        $("[type=\"password\"]").setValue(user.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe((Condition.visible));
    }

    @Test
    void shouldInvalidPassword(){
        UserInfo user = Registration.generateUser("active");
        activeUser(user);
        var invalidPassword = setPassword();
        $("[type=\"text\"]").setValue(user.getLogin());
        $("[type=\"password\"]").setValue(invalidPassword);
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe((Condition.visible));
    }
}
