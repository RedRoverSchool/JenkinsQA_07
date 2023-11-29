package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class RestAPIPage extends BasePage {
    @FindBy(tagName = "h1")
    private WebElement heading;

    public RestAPIPage(WebDriver driver) {
        super(driver);
    }

    public String getHeadingText() {

        return heading.getText();
    }
}
