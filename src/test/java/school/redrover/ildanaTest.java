package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.JenkinsUtils;

import java.time.Duration;

@Ignore
public class ildanaTest extends BaseTest {

    @Test
    public void testSearch() {

        getDriver().get("https://www.coursera.org/");

        WebElement input = getDriver().findElement(By.xpath("//input[@placeholder='What do you want to learn?']"));
        input.sendKeys("Automation", Keys.ENTER);

        WebElement text = getDriver().findElement(By.xpath("//div[@class='cds-9 css-1cs9i33']//div//h3"));
        String value = text.getText();
        Assert.assertEquals(value, "Filter by");

    }

    @Test
    public void testFilter() {

        getDriver().get("https://www.coursera.org/search?query=Automation&");

        WebElement filter = getDriver().findElement(By.xpath("//span[text()='Computer Science']"));
        filter.click();

        WebElement clearbutton = getDriver().findElement(By.xpath("//div[@data-testid='active-filter-items']//button[@aria-label='Clear Computer Science filter']"));
        String buttonText = clearbutton.getText();

        Assert.assertEquals(buttonText, "Computer Science\n" + ",delete");

    }

    @Test
    public void testJenkins() {

        WebElement newItem =getDriver().findElement(By.xpath("//span[@ class='task-link-wrapper ']//a[@ href='/view/all/newJob']"));

        newItem.click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("//div[@class='add-item-name']//label[@for='name']")).getText(), "Enter an item name");
    }
}





