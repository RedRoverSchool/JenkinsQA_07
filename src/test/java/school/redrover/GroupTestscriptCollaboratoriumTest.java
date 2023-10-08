package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GroupTestscriptCollaboratoriumTest {
    @Test
    public void getGuru() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.guru99.com/");

        String title = driver.getTitle();
        assertEquals("Meet Guru99 – Free Training Tutorials & Video for IT Courses", title);


        WebElement JUnitButton = driver.findElement(By.xpath("//*[@data-lasso-id='147439']"));
        JUnitButton.click();

        Thread.sleep(900);

        WebElement textButton = driver.findElement(By.xpath("//*[@id='post-862']/div/div/h2[2]"));
        Assert.assertEquals(textButton.getText(),"JUnit Tutorial Syllabus");

        driver.quit();
    }
    @Test
    public void testSubscription(){

        WebDriver driver = new FirefoxDriver();
        try {

            driver.get("https://murzilka.org/");

            String title = driver.getTitle();
            Assert.assertEquals(title, "Журнал \"Мурзилка\"");

            WebElement textButton = driver.findElement(By.xpath("//*[@class=\"mrb-btn-item-text\"]"));
            String valueButton = textButton.getText();
            Assert.assertEquals(valueButton, "Подписаться на журнал");


            textButton.click();
            WebElement message = driver.findElement(By.xpath("//h1[@class=\"category-name\"]"));
            String valueH1 = message.getText();
            Assert.assertEquals(valueH1, "РЕДАКЦИОННАЯ ПОДПИСКА");

        } finally {
            driver.quit();
        }
    }
    @Test
    public void testAddToBasket(){

        WebDriver driver = new FirefoxDriver();
        try {

            driver.get("https://murzilka.org/products/category/redaktsionnaya-podpiska");
            WebElement addButton = driver.findElement(By.xpath("//button[@class=\"button product-item__button button_for_product-card cart-btn js-order-product js-cart-btn\"]"));
            addButton.click();

            WebElement inBasket = driver.findElement(By.xpath("//*[@class=\"quantity-items top-cart__quantity\"]"));
            String valueBasket = inBasket.getText();
            Assert.assertEquals(valueBasket, "1");

        }finally {
            driver.quit();
        }
    }
}
