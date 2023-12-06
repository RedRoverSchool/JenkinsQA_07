package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class AboutJenkinsPage extends BasePage {
    @FindBy(css = ".app-about-version")
    private WebElement jenkinsVersionText;

    @FindBy(xpath = "//div[@class='jenkins-tab-pane']")
    private List<WebElement> tabPanelElements;

    @FindBy(xpath = "//div[@class='tabBar']//div")
    private List<WebElement> tabBarElements;

    public AboutJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public String getJenkinsVersion() {
        return jenkinsVersionText.getText();
    }

    public List<String> getTabBarText() {
        return tabBarElements.stream().map(WebElement::getText).toList();
    }

    public List<WebElement> getTabBarElements() {
        return tabBarElements;
    }

    public List<WebElement> getTabPaneElements() {
        return tabPanelElements;
    }
}
