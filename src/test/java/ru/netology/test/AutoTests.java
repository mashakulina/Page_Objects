package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.DataHelper.*;


public class AutoTests {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Перевод денег на первую карту")
    void Test1() {
        val dashboardPage = new DashboardPage();
        val amount = 500;
        val expectedBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val expectedBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getSecondCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        val balanceFistCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceFirstCard, amount);
        val balanceSecondCard = getExpectedBalanceIfBalanceDecreased(expectedBalanceSecondCard, amount);
        val finalBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val finalBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        assertEquals(balanceFistCard, finalBalanceFirstCard);
        assertEquals(balanceSecondCard, finalBalanceSecondCard);
    }

    @Test
    @DisplayName("Перевод денег на вторую карту")
    void Test2() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val expectedBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val expectedBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        val transferPage = dashboardPage.transferToSecondCard();
        val transferInfo = getFirstCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        val balanceFistCard = getExpectedBalanceIfBalanceDecreased(expectedBalanceFirstCard, amount);
        val balanceSecondCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceSecondCard, amount);
        val finalBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val finalBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        assertEquals(balanceFistCard, finalBalanceFirstCard);
        assertEquals(balanceSecondCard, finalBalanceSecondCard);
    }

    @Test
    @DisplayName("Перевод всех денег на первую карту")
    void Test3() {
        val dashboardPage = new DashboardPage();
        val amount = dashboardPage.getCurrentBalanceSecondCard();
        val expectedBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val expectedBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getSecondCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        val balanceFistCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceFirstCard, amount);
        val balanceSecondCard = getExpectedBalanceIfBalanceDecreased(expectedBalanceSecondCard, amount);
        val finalBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val finalBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        assertEquals(balanceFistCard, finalBalanceFirstCard);
        assertEquals(balanceSecondCard, finalBalanceSecondCard);
    }

    @Test
    @DisplayName("Перевод не происходит, если в строчке сумма указан 0")
    void Test4() {
        val dashboardPage = new DashboardPage();
        val amount = 0;
        val expectedBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val expectedBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getSecondCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        val balanceFistCard = getExpectedBalanceIfBalanceIncreased(expectedBalanceFirstCard, amount);
        val balanceSecondCard = getExpectedBalanceIfBalanceDecreased(expectedBalanceSecondCard, amount);
        val finalBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val finalBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        assertEquals(balanceFistCard, finalBalanceFirstCard);
        assertEquals(balanceSecondCard, finalBalanceSecondCard);
    }

    @Test
    @DisplayName("Отмена перевода")
    void Test5() {
        val dashboardPage = new DashboardPage();
        val amount = 1000;
        val expectedBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val expectedBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        val transferPage = dashboardPage.transferToSecondCard();
        val transferInfo = getFirstCardNumber();
        transferPage.cancelMoneyTransfer(transferInfo, amount);
        val finalBalanceFirstCard = dashboardPage.getCurrentBalanceFirstCard();
        val finalBalanceSecondCard = dashboardPage.getCurrentBalanceSecondCard();
        assertEquals(expectedBalanceFirstCard, finalBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, finalBalanceSecondCard);
    }

    @Test
    @DisplayName("Ошибка при пустой строке суммы перевода")
    void Test6() {
        val dashboardPage = new DashboardPage();
        val amount = "";
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getSecondCardNumber();
        transferPage.moneyTransfer(transferInfo, Integer.parseInt(amount));
        transferPage.invalidMoneyTransfer();
    }

    @Test
    @DisplayName("Ошибка при переводе денег c несуществующей карты")
    void Test7() {
        val dashboardPage = new DashboardPage();
        val amount = 500;
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getInvalidCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        transferPage.invalidMoneyTransfer();
    }

    @Test
    @DisplayName("Ошибка при переводе средств с незаполненной строкой карты")
    void Test8() {
        val dashboardPage = new DashboardPage();
        val amount = 500;
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getEmptyCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        transferPage.invalidMoneyTransfer();
    }

    @Test
    @DisplayName("Ошибка при переводе средств превышающих баланс карты")
    void Test9() {
        val dashboardPage = new DashboardPage();
        val amount = dashboardPage.getCurrentBalanceSecondCard() + 10000;
        val transferPage = dashboardPage.transferToFirstCard();
        val transferInfo = getSecondCardNumber();
        transferPage.moneyTransfer(transferInfo, amount);
        transferPage.invalidMoneyTransfer();
    }
}

