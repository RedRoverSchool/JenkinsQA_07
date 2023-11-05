package school.redrover;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import school.redrover.runner.BaseTest;

import java.util.UUID;

public class AdditionalUtils extends BaseTest {

    public static String generateRandomName() {
        String randomName = UUID.randomUUID()
                .toString()
                .substring(0, 5);
        return randomName;
    }

    public static void jsClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public static String findTextInPseudoElement(WebElement element, String pseudoElement) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String script = "return window.getComputedStyle(arguments[0], arguments[1]).getPropertyValue('content');";
        return (String) js.executeScript(script, element, pseudoElement);
    }
}
