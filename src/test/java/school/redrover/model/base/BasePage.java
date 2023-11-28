package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import school.redrover.model.HomePage;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();

        return new HomePage(getDriver());
    }

    public <T> T refreshPage(T page) {
        getDriver().navigate().refresh();

        return page;
    }

    public <T> T quickSearch(T page, String str) {
        new Actions(getDriver())
                .keyDown(Keys.CONTROL)
                .sendKeys("k")
                .keyUp(Keys.CONTROL)
                .sendKeys(str)
                .keyDown(Keys.ENTER)
                .perform();

        return page;
    }
}
