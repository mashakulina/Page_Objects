package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountInput = $("[data-test-id=amount] input");
    private SelenideElement fromCardInput = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement cancelButton = $("[data-test-id=action-cancel]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public TransferPage() {
        SelenideElement heading = $(byText("Пополнение карты"));
        heading.shouldBe(visible);
    }

    public void moneyTransfer(DataHelper.TransferInfo TransferInfo, int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromCardInput.setValue(TransferInfo.getCard());
        transferButton.click();
        new DashboardPage();
    }

    public void invalidMoneyTransfer() {
        error.shouldBe(visible);
    }

    public void cancelMoneyTransfer(DataHelper.TransferInfo TransferInfo, int amount) {
        amountInput.setValue(String.valueOf(amount));
        fromCardInput.setValue(TransferInfo.getCard());
        cancelButton.click();
        new DashboardPage();
    }
}
