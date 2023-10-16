package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;


import static org.testng.Assert.assertEquals;


public class BrainBuildersTest extends BaseTest {

    @Test
    public void testAskentSearch() {
            getDriver().get("https://www.askent.ru/");

            String title = getDriver().getTitle();
            assertEquals(title, "ASKENT - российский бренд аксессуаров из натуральной кожи");

            WebElement magnifierIcon = getDriver().findElement(By.className("search_icon"));
            magnifierIcon.click();

            WebElement searchTextField = getDriver().findElement(By.name("q"));
            searchTextField.click();
            searchTextField.sendKeys("сумка");

            WebElement magnifierButton = getDriver().findElement(By.xpath("//button[@type='submit']"));
            magnifierButton.click();

            WebElement searchResult = getDriver().findElement(By.cssSelector("h1"));
            String result = searchResult.getText();
            Assert.assertEquals(result, "Результаты поиска");
    }
}
