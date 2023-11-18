package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class MultibranchPipelineRenameErrorsPage extends BasePage {

    @FindBy(tagName = "p")
    private WebElement error;

    public MultibranchPipelineRenameErrorsPage (WebDriver driver) { super(driver); }

    public String getErrorMessage() {
        return error.getText();

    }
}
