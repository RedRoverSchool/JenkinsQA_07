package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class HomePage extends BasePage {

   @FindBy(xpath = "//a[@href='computer/new']")
    private WebElement SetUpAnAgent;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public <T> T clickJobByName(String name, T page) {
        getDriver().findElement(By.xpath("//*[@id='job_" + name + "']/td[3]/a")).click();

        return page;
    }

    public List<String> getJobList() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//tr/td/a[contains(@class, 'jenkins-table__link')]/span[1]"));
        List<String> resultList = elementList.stream().map(WebElement::getText).toList();

        return resultList;
    }

    public <T> T clickNewItem(T page) {
        getDriver().findElement(By.xpath("//a[@href='/view/all/newJob']")).click();

        return page;
    }

    public NodeCreatePage clickSetUpAnAgent() {
        SetUpAnAgent.click();

        return new NodeCreatePage(getDriver());
    }
}
