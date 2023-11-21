package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class WorkspacePage extends BasePage {

    @FindBy(xpath = "//h1")
    private WebElement header;

    public WorkspacePage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return header.getText();
    }
}
