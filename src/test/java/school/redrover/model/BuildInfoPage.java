package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class BuildInfoPage extends BasePage {

    @FindBy (xpath = "//h1/span[contains(text(), 'Edited name')]")
    private WebElement editedName;

    @FindBy (xpath = "//div[contains(text(), 'Edited description')]")
    private WebElement editedDescription;

    public BuildInfoPage(WebDriver driver) {
        super(driver);
    }

    public String getNameOfBuild() {
        return editedName.getText();
    }

    public String getDescriptionOfBuild() {
        return editedDescription.getText();
    }
}
