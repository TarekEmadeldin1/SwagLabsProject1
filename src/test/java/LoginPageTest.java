import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginPageTest extends TestBase{
    private LoginPage loginPage;

    @DataProvider(name = "Valid Credentials")
    public static Object [][] Data1()
    {
        return new Object[][]{
                {"standard_user", "secret_sauce" },
                {"visual_user", "secret_sauce" },
                {"problem_user", "secret_sauce" },
                {"performance_glitch_user", "secret_sauce" },
                {"error_user", "secret_sauce" }
        };
    }
    //Valid Login using Valid Usernames and Passwords
    @Test(dataProvider = "Valid Credentials", priority = 1)
    public void ValidLogin(String user , String Pass){
        loginPage = new LoginPage(driver);
        loginPage.Username(user);
        loginPage.Password(Pass);
        loginPage.LoginButton();
        loginPage.Menu();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage.Logout();
    }


    //Valid UserName and Empty Password
    @Test(priority = 2)
    public void InValidLogin2() {
        loginPage = new LoginPage(driver);
        loginPage.Username("standard_user");
        loginPage.Password("");
        loginPage.LoginButton();
        String expected = "Epic sadface: Password is required";
        String actual = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).getText();
        Assert.assertTrue(actual.contains(expected));
        loginPage.ClearUsername();
        loginPage.ClearPassword();
    }


    //InValid login with Locked Username and Valid Password
    @Test(priority = 3)
    public void InValidLogin1(){
        loginPage = new LoginPage(driver);
        loginPage.Username("locked_out_user");
        loginPage.Password("secret_sauce");
        loginPage.LoginButton();
        String actual = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]")).getText();
        String expected = "Epic sadface: Sorry, this user has been locked out.";
        Assert.assertTrue(actual.contains(expected));
        loginPage.ClearUsername();
        loginPage.ClearPassword();
    }


    //Valid Username and InValid Password
    @Test(priority = 4)
    public void InValidLogin3() {
        loginPage = new LoginPage(driver);
        loginPage.Username("standard_user");
        loginPage.Password("Abc");
        loginPage.LoginButton();
        String expected = "Epic sadface: Username and password do not match any user in this service";
        String actual = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3")).getText();
        Assert.assertTrue(actual.contains(expected));
        loginPage.ClearUsername();
        loginPage.ClearPassword();
    }
}
