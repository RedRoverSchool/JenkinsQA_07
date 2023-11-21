package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class BreadcrumbPage extends BasePage {

    @FindBy(xpath = "//img[@id=\"jenkins-name-icon\"]")
    private WebElement jenkinsIcon;

    public BreadcrumbPage(WebDriver driver) {
        super(driver);
    }

    public HomePage clickJenkinsIcon() {
        jenkinsIcon.click();
        return new HomePage(getDriver());
    }
}
