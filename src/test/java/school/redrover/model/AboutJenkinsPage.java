package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class AboutJenkinsPage extends BasePage {

    @FindBy (css = ".app-about-version")
    private WebElement jenkinsVersionText;

    public AboutJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> actualListsTabBar() {
        return getDriver()
                .findElements(By.xpath("//div[@class='tabBar']//div[contains(@class,'tab')]"));
    }

    public String getJenkinsVersion() {

        return jenkinsVersionText.getText();
    }


    public List<String> getTabBarText() {
        List<WebElement> elementList = getDriver().findElements(By.xpath("//div[@class='tabBar']//div"));
        List<String> textList = elementList.stream().map(WebElement::getText).toList();

        return textList;
    }

    public List<WebElement> getTabBarElements() {

        return getDriver().findElements(By.xpath("//div[@class='tabBar']//div"));
    }
    public List<WebElement> getTabPaneElements() {

        return getDriver().findElements(By.xpath("//div[@class='jenkins-tab-pane']"));
    }

}
