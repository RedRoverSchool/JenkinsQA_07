package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NodesListPage extends BasePage {

    public NodesListPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getNodeList() {
        List<WebElement> nodesList = getDriver().findElements(By.xpath("//tr/td/a"));

        return nodesList.stream().map(WebElement::getText).toList();
    }
}
