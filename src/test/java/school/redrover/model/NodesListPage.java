package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NodesListPage extends BasePage {

    @FindBy(xpath = "//a[@href='new']")
    private WebElement newNodeButton;

    @FindBy(xpath = "//tr/td/a")
    private List<WebElement> nodesList;

    @FindBy(xpath = "//th[@initialsortdir='down']")
    private WebElement sortByNameButton;

    public NodesListPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getNodeList() {
        return getWait2().until(ExpectedConditions.visibilityOfAllElements(nodesList)).stream().map(WebElement::getText).toList();
    }

    public NodeCreatePage clickNewNodeButton() {
        newNodeButton.click();

        return new NodeCreatePage(getDriver());
    }

    public NodeDetailsPage clickNodeByName(String nodeName) {
        getDriver().findElement(By.xpath("//tr[@id='node_" + nodeName + "']/td/a")).click();

        return new NodeDetailsPage(getDriver());
    }

    public boolean elementIsNotPresent(String xpath){
        return getDriver().findElements(By.xpath(xpath)).isEmpty();
    }

    public String getCurrentURL(){
        return getDriver().getCurrentUrl();
    }

    public NodesListPage clickSortByNameButton() {
        getWait2().until(ExpectedConditions.elementToBeClickable(sortByNameButton)).click();

        return this;
    }
}
