package vitalii.Junit5.tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static vitalii.Junit5.tests.DataTests.bookAuthor;
import static vitalii.Junit5.tests.DataTests.bookName;

@Owner("velichkovv")
@Feature("Parameterized Test")
@Description("тесты для страницы https://polka.academy/")

public class PolkaTest extends TestBase {

    @Tag("lvl_1")
    @DisplayName("Search Anna Karenina book")
    @Tag("Web")
    @Test
    public void searchBookTest() {
        open("https://polka.academy/");
        $(".wCizZ svg").click();
        $(".AKPsr").setValue(bookName).pressEnter();
        $("#searchInput input").shouldHave(value(bookName)).shouldBe(visible);
        $(".mkXom").click();
        $("._1Vhx8").shouldHave(text(bookAuthor)).shouldBe(visible);
        $("._3j3bx").shouldHave(text(bookName)).shouldBe(visible);
    }

    @Tag("lvl_2")
    @ValueSource(strings = {"Анна Каренина", "Война и мир", "Вишнёвый сад"}) //аргумент провайдер
    @ParameterizedTest(name = "Search {0} book")
    public void searchBookTest(String selectBook) {
        open("https://polka.academy/");
        $(".wCizZ svg").click();
        $(".AKPsr").setValue(selectBook).pressEnter();
        $("#searchInput input").shouldHave(value(selectBook)).shouldBe(visible);
        $(".mkXom").click();
        $(".uO74Z h1").shouldHave(text(selectBook)).shouldBe(visible);
    }

    @Tag("lvl_3")
    @EnumSource(EnumClass.class)
    @ParameterizedTest(name = "Search {0} just word?")
    public void searchBookTest(EnumClass enumClass) {
        open("https://polka.academy/");
        $(".wCizZ svg").click();
        $(".AKPsr").setValue(enumClass.name()).pressEnter();
        $("#searchInput input").shouldHave(value(enumClass.name())).shouldBe(visible);
        $(".mkXom").click();
        $(".uO74Z h1").shouldHave(text(enumClass.name())).shouldBe(visible);
    }

    @Tag("lvl_4")
    @CsvSource(value = {
            "Анна Каренина | Лев Толстой",
            "Война и мир | Лев Толстой",
            "Вишнёвый сад | Антон Чехов"
    },
            delimiter = '|')
    @ParameterizedTest(name = "Search {0} book by {1}")
    public void searchBookTest(String selectBook, String selectAuthor) {
        open("https://polka.academy/");
        $(".wCizZ svg").click();
        $(".AKPsr").setValue(selectBook).pressEnter();
        $("#searchInput input").shouldHave(value(selectBook)).shouldBe(visible);
        $(".mkXom").click();
        $("._1Vhx8").shouldHave(text(selectAuthor)).shouldBe(visible);
        $(".uO74Z h1").shouldHave(text(selectBook)).shouldBe(visible);
    }

    //@MethodSource - самый мощный инструмент. Заменяет все предыдущие
    //возвращает список аргументов любых типов
    static Stream<Arguments> testSearchBook() {
        return Stream.of(
                Arguments.of("Анна Каренина", "Лев Толстой"),
                Arguments.of("Война и мир", "Лев Толстой"),
                Arguments.of("Вишнёвый сад", "Антон Чехов")
        );
    }

    @Tag("lvl_5_HARD")
    @MethodSource("testSearchBook")
    @ParameterizedTest(name = "Search {0} book by {1}")
    void testSearchBook(String selectBook, String selectAuthor) {
        open("https://polka.academy/");
        $(".wCizZ svg").click();
        $(".AKPsr").setValue(selectBook).pressEnter();
        $("#searchInput input").shouldHave(value(selectBook)).shouldBe(visible);
        $(".mkXom").click();
        $("._1Vhx8").shouldHave(text(selectAuthor)).shouldBe(visible);
        $(".uO74Z h1").shouldHave(text(selectBook)).shouldBe(visible);
    }

    //входные данные из таблицы csv (exel)
    @ParameterizedTest(name = "Search {0} book name by author name{1}")
    @DisplayName("Ищем книгу и проверяем её авора используя входные данные из таблицы")
    @CsvFileSource(resources = "/DataBooks.csv", numLinesToSkip = 1)
    public void searchBookFromCsvTest(String selectBook, String selectAuthor) {
        open("https://polka.academy/");
        $(".wCizZ svg").click();
        $(".AKPsr").setValue(selectBook).pressEnter();
        $("#searchInput input").shouldHave(value(selectBook)).shouldBe(visible);
        $(".mkXom").click();
        $("._1Vhx8").shouldHave(text(selectAuthor)).shouldBe(visible);
        $(".uO74Z h1").shouldHave(text(selectBook)).shouldBe(visible);
    }
}
