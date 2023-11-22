package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import school.redrover.model.base.BasePage;

public class GlobalViewPage extends BasePage {

    public GlobalViewPage(WebDriver driver) {
        super(driver);
    }

    public GlobalViewConfigPage clickEditView() {
        getDriver().findElement(By.xpath("//a[contains(@href,'/configure')]")).click();

        return new GlobalViewConfigPage(getDriver());
    }

    public GlobalViewPage clickAddOrEditDescription() {
        getDriver().findElement(By.xpath("//a[@id = 'description-link']")).click();

        return this;
    }

    public GlobalViewPage typeNewDescription(String newDescriptionForTheView) {
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).sendKeys(newDescriptionForTheView);

        return this;
    }

    public GlobalViewPage clearDescriptionField() {
        getDriver().findElement(By.xpath("//textarea[@name = 'description']")).clear();

        return this;
    }

    public GlobalViewPage clickSaveDescription() {
        getDriver().findElement(By.xpath("//button[@name = 'Submit']")).click();

        return this;
    }

    public String getDescription() {
        return getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText();
    }
}
