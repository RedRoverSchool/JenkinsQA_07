package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class ListViewConfigurationPage extends BasePage {

    @FindBy(xpath = "//button[@name = 'Submit']")
    private WebElement configurationOKButton;

    public ListViewConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public ListViewConfigurationPage checkSelectedJobCheckbox(String jobName) {
        getDriver().findElement(By.xpath("//label[@title = '" + jobName + "']")).click();

        return this;
    }

    public ListViewPage clickOKButton() {
        configurationOKButton.click();

        return new ListViewPage(getDriver());
    }
}
