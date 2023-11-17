package school.redrover.model.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.HomePage;

public abstract class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goHomePage() {
        getDriver().findElement(By.xpath("//a[@id = 'jenkins-home-link']")).click();

        return new HomePage(getDriver());
    }
}
