package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import school.redrover.model.base.BasePage;

import java.util.List;

public class SearchResultQueryPage extends BasePage {
    public SearchResultQueryPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getSearchResultQueryText() {

        return getDriver().findElements(By.cssSelector("li[id]")).stream().map(WebElement::getText).toList();
    }

    public <T> T clickSearchResultQuery(String nameResult, T page) {
        getDriver().findElement(By.linkText(nameResult)).click();

        return page;
    }
}
