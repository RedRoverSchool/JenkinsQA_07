package school.redrover;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.runner.BaseTest;

import java.security.SecureRandom;
import java.util.UUID;

public class AdditionalUtils extends BaseTest{

    public static String generateRandomName() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public static String generateRandomPassword(int length) {
        final String charLower = "abcdefghijklmnopqrstuvwxyz";
        final String charUpper = charLower.toUpperCase();
        final String number = "0123456789";

        final String randomString = charLower + charUpper + number;
        SecureRandom random = new SecureRandom();

        if (length <= 0) {
            throw new IllegalArgumentException("Length must be a positive number");
        }

        StringBuilder randomPassword = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(randomString.length());
            char rndChar = randomString.charAt(rndCharAt);
            randomPassword.append(rndChar);
        }
        return randomPassword.toString();
    }


    public static void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static String findTextInPseudoElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = "return window.getComputedStyle(arguments[0], '::before').getPropertyValue('content');";
        return (String) js.executeScript(script, element);
    }
}