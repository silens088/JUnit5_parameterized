package vitalii.Junit5.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static vitalii.Junit5.tests.DataTests.*;

@Owner("velichkovv")
@Feature("параметризованные тесты")

public class PolkaTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1366x768";
    }

    @DisplayName("Search Anna Karenina book")
    @Tag("Web")
    @Test
    public void searchBookTest() {
        open("https://polka.academy/");

        $(".wCizZ svg").click();
        $(".AKPsr").setValue(bookName).pressEnter();
        $("#searchInput input").shouldHave(value(bookName)).shouldBe(visible);

        $(".mkXom").click();
        //$$(".k1t8h a").get(0).click();

        $("._1Vhx8").shouldHave(text(bookAuthor)).shouldBe(visible);
        $("._3j3bx").shouldHave(text(bookName)).shouldBe(visible);
    }

    @Tag("param")
    //@DisplayName("Search book")
    @ValueSource(strings = {"Анна Каренина", "Война и мир", "Вишнёвый сад"} )
    @ParameterizedTest(name = "Search {0} book")
    public void searchBookTest(String selectBook) {
        open("https://polka.academy/");

        $(".wCizZ svg").click();
        $(".AKPsr").setValue(selectBook).pressEnter();
        $("#searchInput input").shouldHave(value(selectBook)).shouldBe(visible);
        $(".mkXom").click();

        //$("._1Vhx8").shouldHave(text(selectAuthor)).shouldBe(visible);
        $(".uO74Z h1").shouldHave(text(selectBook)).shouldBe(visible);

    }
}
