package school.redrover.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class BuildWithParametersPage extends BasePage {

    @FindBy(xpath = "//select[@name='value']/option")
    private List<WebElement> choiceParameterOptions;

    public BuildWithParametersPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getChoiceParameterOptions() {
        return choiceParameterOptions
                .stream()
                .map(WebElement::getText)
                .toList();
    }
}
