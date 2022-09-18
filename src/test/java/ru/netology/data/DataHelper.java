package ru.netology.data;
import lombok.*;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class TransferInfo {
        private String card;
    }

    public static TransferInfo getFirstCardNumber() {
        return new TransferInfo("5559 0000 0000 0001");
    }

    public static TransferInfo getSecondCardNumber() {
        return new TransferInfo("5559 0000 0000 0002");
    }

    public static TransferInfo getEmptyCardNumber() {
        return new TransferInfo("");
    }

    public static TransferInfo getInvalidCardNumber() {
        return new TransferInfo("5559 0000 0000 0003");
    }

    public static int getExpectedBalanceIfBalanceIncreased(int balance, int amount) {
        return balance + amount;
    }

    public static int getExpectedBalanceIfBalanceDecreased(int balance, int amount) {
        return balance - amount;
    }
}

