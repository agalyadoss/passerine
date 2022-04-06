import com.heaerie.passerine.cube.PAS001MTMapper;
import com.heaerie.passerine.pojo.PAS001MT;
import com.heaerie.passerine.service.CubeFactory;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    CubeFactory cubeFactory;
    static {
    //    System.setProperty("webdriver.chrome.driver", "C:\\Users\\agalya\\Downloads\\chromedriver_win32_100\\chromedriver.exe");

        System.setProperty("webdriver.edge.driver", "C:\\Users\\agalya\\Downloads\\edgedriver_win64\\msedgedriver.exe");

    }
    @Before
    public  void init() throws SQLException {
        cubeFactory= CubeFactory.getInstance();
    }
    @Test
     public void test1() {



        PAS001MTMapper pas001mt=   CubeFactory.getPAS001MTMapper();
        PAS001MT obj= new PAS001MT();
        obj.setRespCode(200);
        try {
            List<PAS001MT> recds=pas001mt.get(obj);

            recds.parallelStream().forEach(pas001MT -> {

                if (pas001MT.getContent() != null ) {
                    WebDriver driver = new EdgeDriver();
                    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

                    driver.manage().window().maximize();
//1.10.10.0	1.10.10.255
                    driver.get("https://" + pas001MT.getIp()  + "/");

                    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                    WebElement email = driver.findElement(By.name("email"));
                    if (email != null) {
                        email.sendKeys("durai", Keys.TAB);
                        WebElement password = driver.findElement(By.name("password"));

                        password.sendKeys("1qaz2wsx", Keys.TAB, Keys.TAB);

                        WebElement submit = driver.findElement(By.className("btnAction"));
                        submit.click();


                    }
                }

            });




        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//driver.close();

    }
}
