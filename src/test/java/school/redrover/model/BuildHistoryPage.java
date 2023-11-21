package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class BuildHistoryPage extends BasePage {

    @FindBy(id = "main-panel")
    private WebElement mainPanel;

    @FindBy(id = "button-icon-legend")
    private WebElement iconLegendButton;

    @FindBy(xpath = "//h2[contains(text(),'Status')]/following::dl")
    private List<WebElement> iconLegendHeaders;

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getMainPanel() {
        return mainPanel;
    }

    public List<WebElement> getIconLegendHeaders() {
        return iconLegendHeaders;
    }

    public BuildHistoryPage clickIconLegendButton() {
        iconLegendButton.click();
        return this;
    }

    public String timeSinceTableLinkText() {
        WebElement timeSince = getDriver().findElement(By.xpath("//tr/td[3]/button[1]"));

        new Actions(getDriver()).
                moveToElement(timeSince).
                perform();

        return  getDriver().findElement(By.cssSelector("div[class='tippy-content']"))
                .getText();
    }
}