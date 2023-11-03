package school.redrover;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import static org.testng.Assert.assertEquals;

public class FreestyleProject20Test extends BaseTest {
    private final String PROJECT_NAME = "Freestyle Project";
    private final String DESCRIPTION_TEXT = "Description";


    private void createFreestyleProject(String projectName) {
        getDriver().findElement(By.linkText("New Item")).click();
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.id("ok-button")).click();
    }
    @Test
    public void testAddDescriptionFreestyleProject() {
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id='description']/div[1]")).getText(),
                DESCRIPTION_TEXT);
    }

    @Test
    public void testEditDescriptionFreestyleProject() {
        final String editedDescriptionText = "New description text";

        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.cssSelector("a[id='description-link']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).clear();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(editedDescriptionText);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id='description']//div[1]")).getText(),
                editedDescriptionText);
    }

    @Test
    public void testDeleteDescriptionFreestyleProject() {
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.xpath("//textarea[@class='jenkins-input   ']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).sendKeys(DESCRIPTION_TEXT);
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();
        getDriver().findElement(By.cssSelector("a[id='description-link']")).click();
        getDriver().findElement(By.cssSelector("textarea[name='description']")).clear();
        getDriver().findElement(By.cssSelector("button[class='jenkins-button jenkins-button--primary ']")).click();

        assertEquals(getDriver().findElement(By.xpath("//div[@id = 'description']/div[1]")).getText(),
                "");
    }
}
