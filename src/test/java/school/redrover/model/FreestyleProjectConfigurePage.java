package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

public class FreestyleProjectConfigurePage extends BasePage {
    @FindBy(css = "a[helpurl='/descriptor/jenkins.model.BuildDiscarderProperty/help']")
    private WebElement helpButtonDiscardOldBuilds;

    @FindBy(xpath = "//button[@name='Submit']")
    private WebElement saveButton;

    public FreestyleProjectConfigurePage(WebDriver driver) {
        super(driver);
    }


    public boolean tooltipDiscardOldBuildsIsVisible() {
         boolean tooltipIsVisible = true;
        new Actions(getDriver())
                .moveToElement(helpButtonDiscardOldBuilds)
                .perform();
        if (helpButtonDiscardOldBuilds.getAttribute("title").equals("Help for feature: Discard old builds")) {
            tooltipIsVisible = false;
        }
        return tooltipIsVisible;
    }

    public FreestyleProjectDetailsPage clickSaveButton() {
        saveButton.click();

        return new FreestyleProjectDetailsPage(getDriver());
    }
}
