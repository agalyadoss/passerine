import com.google.common.base.Strings;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.passerine.cube.PAS001MTMapper;
import com.heaerie.passerine.pojo.Kural;
import com.heaerie.passerine.pojo.PAS001MT;
import com.heaerie.passerine.pojo.ThiruKural;
import com.heaerie.passerine.service.CubeFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SeleniumTest {
    static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            //.excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .setPrettyPrinting()
            .create();

    static final Logger logger = LogManager.getLogger();
    CubeFactory cubeFactory;
    static final Pattern p = Pattern.compile("^(([0-9]+).)(([0-9]+).)?(([0-9]+).)?(\\s)?");
    //3..2. 9 உறுப்புநலனழிதல்
    static final Pattern p1 = Pattern.compile("^(([0-9]+)..)(([0-9]+).)(\\s)(([0-9]+)?.)(\\s)?");


    static {
        //    System.setProperty("webdriver.chrome.driver", "C:\\Users\\agalya\\Downloads\\chromedriver_win32_100\\chromedriver.exe");

        System.setProperty("webdriver.edge.driver", "C:\\Users\\agalya\\Downloads\\edgedriver_win64\\msedgedriver.exe");

    }

    @Before
    public void init() throws SQLException {
        cubeFactory = CubeFactory.getInstance();
    }

    @Test
    public void test2() throws IOException {


        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();
//1.10.10.0	1.10.10.255
        driver.get("https://www.projectmadurai.org/pm_etexts/utf8/pmuni0001.html");

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);


        List<WebElement> centers = driver.findElements(By.tagName("center"));
        for (WebElement center : centers) {
            System.out.println("center=" + center.getText());
        }
        List<WebElement> headers = driver.findElements(By.tagName("font"));
        List<String> Titles = new ArrayList<>();
        List<TitleOfKural> TitleOfKurals = new ArrayList<>();
        for (WebElement header : headers) {
            String color = header.getAttribute("color");
            TypeOfTitle typeOfTitle = new TypeOfTitle();
            if (!Strings.isNullOrEmpty(color) && "blue".equalsIgnoreCase(color)) {

                if (isValidType(header.getText(), typeOfTitle)) {
                    TitleOfKurals.add(new TitleOfKural(getTitle(header.getText()), typeOfTitle));
                }

            } else if (!Strings.isNullOrEmpty(color) && "red".equalsIgnoreCase(color)) {
                if (isValidType(header.getText(), typeOfTitle)) {
                    TitleOfKurals.add(new TitleOfKural(getTitle(header.getText()), typeOfTitle));
                }
            }
            if (TYPE_IYAL.equalsIgnoreCase(typeOfTitle.getType())) {
                System.out.println("TYPE_IYAL typeOfTitle=" + typeOfTitle + ", color=" + color + "header.getText()=" +  header.getText());


            }
        }


        System.out.println("TitleOfKurals=" + TitleOfKurals);


        List<Kural> kuralgal = new ArrayList<>();

        Map<Integer, Kural> kuralMap = new HashMap<>();
        int enCount = 0;
        List<WebElement> tables = driver.findElements(By.tagName("table"));
        for (WebElement table : tables) {
            // System.out.println("table=" + table.getText());
            List<WebElement> tableRows = table.findElements(By.tagName("tr"));
            for (WebElement tr : tableRows) {
                List<WebElement> tableColumns = tr.findElements(By.tagName("td"));
                if (tableColumns.size() == 2) {

                    Kural kural = new Kural();
                    kural.setEn(Integer.parseInt(tableColumns.get(1).getText()));
                    List<String> varigal = new ArrayList<>();
                    String varigals = tableColumns.get(0).getText();
                    String[] varigalArr = varigals.split("\n");
                    for (int i = 0; i < varigalArr.length; i++) {
                        varigal.add(varigalArr[i]);
                    }
                    kural.setVarigal(varigal);

                    kuralMap.put(++enCount, kural);

                    kuralgal.add(kural);

                } else {
                   /* for (WebElement col : tableColumns) {
                        System.out.println("col=" + col.getText());

                    }*/
                }


            }
        }

        List<TitleOfKural> iyals = TitleOfKurals.stream().filter(titleOfKural -> TYPE_IYAL.equalsIgnoreCase(titleOfKural.getTypeOfTitle().getType())).collect(Collectors.toUnmodifiableList());
        System.out.println(" iyals=" + iyals);
        iyals.forEach(iyal -> {

            List<TitleOfKural> firstAthiGaram = TitleOfKurals.stream().filter(titleOfKural -> TYPE_ADHIGARAM.equalsIgnoreCase(titleOfKural.getTypeOfTitle().getType())
                    && iyal.getTypeOfTitle().getPal() == titleOfKural.getTypeOfTitle().getPal()
                    && iyal.getTypeOfTitle().getIyal() == titleOfKural.getTypeOfTitle().getIyal()
                    && titleOfKural.getTypeOfTitle().getAdhigaram() == 1).collect(Collectors.toUnmodifiableList());
            System.out.println("iyal=" + iyal + ", firstAthiGaram=" + firstAthiGaram);
            if (firstAthiGaram.isEmpty()) {

                int i = TitleOfKurals.indexOf(iyal);
                TypeOfTitle typeOfTitle = new TypeOfTitle();
                typeOfTitle.setAdhigaram(1);
                typeOfTitle.setPal(iyal.getTypeOfTitle().getPal());
                typeOfTitle.setType(TYPE_ADHIGARAM);

                TitleOfKural athigaram = new TitleOfKural(iyal.title, typeOfTitle);
                TitleOfKurals.add(i, athigaram);


            }

        });

        int athigaram = 1;

        for (TitleOfKural titleOfKural : TitleOfKurals) {
            if (TYPE_ADHIGARAM.equals(titleOfKural.getTypeOfTitle().getType())) {
                List<Kural> kurals = new ArrayList<>();
                for (int i = ((athigaram - 1) * 10) + 1; i <= athigaram * 10; i++) {

                    Kural kural = kuralMap.get(i);
                    if (kural == null) {
                        throw new RuntimeException(i + "|" + kuralMap.toString());
                    }
                    kurals.add(kuralMap.get(i));
                }
                athigaram++;
                titleOfKural.setKurals(kurals);
            }
        }

        File f = new File("C:\\logs\\kural.json");

        FileWriter wr = new FileWriter(f);
        wr.write(gson.toJson(TitleOfKurals));
        wr.close();


                    /*
                    if (email != null) {
                        email.sendKeys("durai", Keys.TAB);
                        WebElement password = driver.findElement(By.name("password"));

                        password.sendKeys("1qaz2wsx", Keys.TAB, Keys.TAB);

                        WebElement submit = driver.findElement(By.className("btnAction"));
                        submit.click();


                    }
                      *
                     */


    }

    private String getTitle(String text) {

        return text.split("\\n")[0].replaceAll("^[\\s\\.,\\d]+", "");
    }


    public static class TitleOfKural {
        String title;
        TypeOfTitle typeOfTitle;
        List<Kural> kurals;

        public List<Kural> getKurals() {
            return kurals;
        }

        public void setKurals(List<Kural> kurals) {
            this.kurals = kurals;
        }

        public TitleOfKural(String title, TypeOfTitle typeOfTitle) {
            this.title = title;
            this.typeOfTitle = typeOfTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public TypeOfTitle getTypeOfTitle() {
            return typeOfTitle;
        }

        public void setTypeOfTitle(TypeOfTitle typeOfTitle) {
            this.typeOfTitle = typeOfTitle;
        }

        @Override
        public String toString() {
            return "TitleOfKural{" +
                    "title='" + title + '\'' +
                    ", typeOfTitle=" + typeOfTitle +
                    ", kurals=" + kurals +
                    '}';
        }
    }


    @Test
    public void test3() {

        //final Pattern p = Pattern.compile("^(([0-9]+).)(([0-9]+).)?(([0-9]+).)?(\\s)?");
        final Pattern p1 = Pattern.compile("^(([0-9]+)..)(([0-9]+).)?(\\s)?(([0-9]+)?.)?(\\s)?");

        // Matcher m = p1.matcher("1.1.3. நீத்தார் பெருமை");
        Matcher m = p1.matcher("3..2. 9 உறுப்புநலனழிதல்");

        // if an occurrence if a pattern was found in a given string...
        if (m.find()) {
            // ...then you can use group() methods.
            System.out.println("0=" + m.group(0)); // whole matched expression
            System.out.println("1=" + m.group(1)); // first expression from round brackets (Testing)
            System.out.println("2=" + m.group(2)); // second one (123)
            System.out.println("3=" + m.group(3)); // third one (Testing)
            System.out.println("4=" + m.group(4)); // third one (Testing)
            System.out.println("5=" + m.group(5)); // third one (Testing)
            System.out.println("6=" + m.group(6)); // third one (Testing)
        } else {
            System.out.println(m.find());
        }

    }


    @Test
    public void test4() {
        String athigaram = "1.1.3. நீத்தார் பெருமை";

        TypeOfTitle typeOfTitle = new TypeOfTitle();
        isValidType(athigaram, typeOfTitle);
        System.out.println("typeOfTitle=" + typeOfTitle);

    }

    public static final String TYPE_PAL = "PAL";
    public static final String TYPE_IYAL = "IYAL";
    public static final String TYPE_ADHIGARAM = "ADHIGARAM";

    public static class TypeOfTitle {
        String type;
        int pal;
        int iyal;
        int adhigaram;

        /*

                public TypeOfTitle(String type) {
                    this.type = type;
                }

                public TypeOfTitle(String type, int pal) {
                    this.type = type;
                    this.pal = pal;
                }

                public TypeOfTitle(String type, int pal, int iyal) {
                    this.type = type;
                    this.pal = pal;
                    this.iyal = iyal;
                }


                public TypeOfTitle(String type, int pal, int iyal, int adhigaram) {
                    this.type = type;
                    this.pal = pal;
                    this.iyal = iyal;
                    this.adhigaram = adhigaram;
                }
        */
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getPal() {
            return pal;
        }

        public void setPal(int pal) {
            this.pal = pal;
        }

        public int getIyal() {
            return iyal;
        }

        public void setIyal(int iyal) {
            this.iyal = iyal;
        }

        public int getAdhigaram() {
            return adhigaram;
        }

        public void setAdhigaram(int adhigaram) {
            this.adhigaram = adhigaram;
        }

        @Override
        public String toString() {
            return "TypeOfTitle{" +
                    "type='" + type + '\'' +
                    ", pal=" + pal +
                    ", iyal=" + iyal +
                    ", adhigaram=" + adhigaram +
                    '}';
        }
    }

    public static boolean isValidType(String title, TypeOfTitle typeOfTitle) {

        Matcher m = p.matcher(title);
        Matcher m1 = p1.matcher(title);

        // if an occurrence if a pattern was found in a given string...
        boolean matchFound = m.find();
        boolean match1Found = m1.find();
        if (match1Found) {
            m = m1;
        }
        if (match1Found) {
            // ...then you can use group() methods.
            String matchLine = m.group(0); // whole matched expression
            if (Strings.isNullOrEmpty(m.group(6)) && Strings.isNullOrEmpty(m.group(4)) && Strings.isNullOrEmpty(m.group(1))) {
                return false;
            }

            if (Strings.isNullOrEmpty(m.group(6)) && Strings.isNullOrEmpty(m.group(4)) && !Strings.isNullOrEmpty(m.group(1))) {
                typeOfTitle.setType(TYPE_PAL);
                typeOfTitle.setPal(Integer.parseInt(m.group(2)));
                return true;
            }

            if (Strings.isNullOrEmpty(m.group(6)) && !Strings.isNullOrEmpty(m.group(4)) && !Strings.isNullOrEmpty(m.group(1))) {
                typeOfTitle.setType(TYPE_IYAL);
                typeOfTitle.setPal(Integer.parseInt(m.group(2)));
                typeOfTitle.setIyal(Integer.parseInt(m.group(4)));
                return true;
            }

            if (!Strings.isNullOrEmpty(m.group(6)) && !Strings.isNullOrEmpty(m.group(4)) && !Strings.isNullOrEmpty(m.group(1))) {
                typeOfTitle.setType(TYPE_ADHIGARAM);
                typeOfTitle.setPal(Integer.parseInt(m.group(2)));
                typeOfTitle.setIyal(Integer.parseInt(m.group(4)));
                typeOfTitle.setAdhigaram(Integer.parseInt(m.group(6).trim()));
                return true;
            }
        } else if (matchFound) {


            if (Strings.isNullOrEmpty(m.group(6)) && Strings.isNullOrEmpty(m.group(4)) && Strings.isNullOrEmpty(m.group(1))) {
                return false;
            }

            if (Strings.isNullOrEmpty(m.group(6)) && Strings.isNullOrEmpty(m.group(4)) && !Strings.isNullOrEmpty(m.group(1))) {
                typeOfTitle.setType(TYPE_PAL);
                typeOfTitle.setPal(Integer.parseInt(m.group(2)));
                return true;
            }

            if (Strings.isNullOrEmpty(m.group(6)) && !Strings.isNullOrEmpty(m.group(4)) && !Strings.isNullOrEmpty(m.group(1))) {
                typeOfTitle.setType(TYPE_IYAL);
                typeOfTitle.setPal(Integer.parseInt(m.group(2)));
                typeOfTitle.setIyal(Integer.parseInt(m.group(4)));
                return true;
            }

            if (!Strings.isNullOrEmpty(m.group(6)) && !Strings.isNullOrEmpty(m.group(4)) && !Strings.isNullOrEmpty(m.group(1))) {
                typeOfTitle.setType(TYPE_ADHIGARAM);
                typeOfTitle.setPal(Integer.parseInt(m.group(2)));
                typeOfTitle.setIyal(Integer.parseInt(m.group(4)));
                typeOfTitle.setAdhigaram(Integer.parseInt(m.group(6)));
                return true;
            }
        }

        return false;
    }


    @Test
    public void test1() {


        PAS001MTMapper pas001mt = CubeFactory.getPAS001MTMapper();
        PAS001MT obj = new PAS001MT();
        obj.setRespCode(200);
        try {
            List<PAS001MT> recds = pas001mt.get(obj);

            // recds.parallelStream().forEach(pas001MT -> {


            WebDriver driver = new EdgeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            driver.manage().window().maximize();
//1.10.10.0	1.10.10.255
            driver.get("https://www.projectmadurai.org/pm_etexts/utf8/pmuni0001.html");

            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            WebElement email = driver.findElement(By.name("email"));
            if (email != null) {
                email.sendKeys("durai", Keys.TAB);
                WebElement password = driver.findElement(By.name("password"));

                password.sendKeys("1qaz2wsx", Keys.TAB, Keys.TAB);

                WebElement submit = driver.findElement(By.className("btnAction"));
                submit.click();


            }
            //}


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
