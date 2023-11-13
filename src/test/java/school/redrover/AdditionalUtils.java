package school.redrover;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.runner.BaseTest;

import java.util.UUID;

public class AdditionalUtils extends BaseTest{

    public static String generateRandomName() {
        String randomName = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        return randomName;
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