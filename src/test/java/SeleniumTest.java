import com.google.common.base.Strings;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heaerie.passerine.cube.PAS001MTMapper;
import com.heaerie.passerine.pojo.Kural;
import com.heaerie.passerine.pojo.PAS001MT;
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
import java.util.*;
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
   /*
   return wordCounts.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    */

        Map<Integer, TitleOfKural>  sortedMap = new HashMap<>();
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();
//1.10.10.0	1.10.10.255
        driver.get("https://www.projectmadurai.org/pm_etexts/utf8/pmuni0001.html");

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);


        List<WebElement> centers = driver.findElements(By.tagName("center"));
        for (WebElement center : centers) {
            String color = center.getAttribute("color");
            if (!Strings.isNullOrEmpty(color) && "blue".equalsIgnoreCase(color)) {
                System.out.println("center=" + center.getText());
            }
        }
        List<WebElement> headers = driver.findElements(By.tagName("font"));
        List<String> Titles = new ArrayList<>();
        List<TitleOfKural> TitleOfKurals = new ArrayList<>();
        int headerIndex = 0;
        for (WebElement header : headers) {

            String color = header.getAttribute("color");


            if (!Strings.isNullOrEmpty(color) && ("blue".equalsIgnoreCase(color) || "red".equalsIgnoreCase(color))) {

                 List<String>  lines=splitByLine(header.getText());
                lines.forEach(text -> {
                    TypeOfTitle typeOfTitle = new TypeOfTitle();
                    if (isValidType(text, typeOfTitle)) {
                        System.out.println("success :" + text);
                        TitleOfKurals.add(new TitleOfKural(getTitle(text), typeOfTitle));

                    } else {
                        System.err.println("err :" + text);
                    }
                });


            }

            headerIndex= headerIndex + 1;
        }

        TitleOfKurals.forEach(titleOfKural -> {
            sortedMap.put(computeKey(titleOfKural.getTypeOfTitle()),  titleOfKural);
        });
        TypeOfTitle typeOfTitle321 =  new TypeOfTitle();
        typeOfTitle321.setType(TYPE_ADHIGARAM);
        typeOfTitle321.setPal(3);
        typeOfTitle321.setIyal(2);
        typeOfTitle321.setAdhigaram(1);
        TitleOfKural titleOfKural321 = new TitleOfKural("பிரிவாற்றாமை",typeOfTitle321 );
        sortedMap.put(computeKey(titleOfKural321.getTypeOfTitle()), titleOfKural321);
        //அரசியல்
        TypeOfTitle typeOfTitle22 =  new TypeOfTitle();
        typeOfTitle22.setType(TYPE_IYAL);
        typeOfTitle22.setPal(2);
        typeOfTitle22.setIyal(2);
        typeOfTitle22.setAdhigaram(0);
        TitleOfKural titleOfKural22 = new TitleOfKural("அரசியல்",typeOfTitle22 );
        sortedMap.put(computeKey(titleOfKural321.getTypeOfTitle()), titleOfKural22);

        Map<Integer,TitleOfKural> sorted= sortedMap.entrySet()
                .stream()
                .sorted((Map.Entry.<Integer, TitleOfKural>comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println("TitleOfKurals=" + sorted);


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
                    int kuralEn=++enCount;
                    if (kural.getEn() != kuralEn) {
                        System.err.println("Act:" + kural.getEn() + "!= Exp:" + kuralEn);
                        kural.setEn(kuralEn);
                    }
                    kuralMap.put(kuralEn, kural);


                    kuralgal.add(kural);

                } else {
                   /* for (WebElement col : tableColumns) {
                        System.out.println("col=" + col.getText());

                    }*/
                }


            }

        }


        List<TitleOfKural> titleOfKuralsSorted = new ArrayList<>();

        int athigaram = 1;

        for ( Map.Entry<Integer, TitleOfKural>  titleOfKural : sorted.entrySet()) {
            if (TYPE_ADHIGARAM.equals(titleOfKural.getValue().getTypeOfTitle().getType())) {
                List<Kural> kurals = new ArrayList<>();
                for (int i = ((athigaram - 1) * 10) + 1; i <= athigaram * 10; i++) {

                    Kural kural = kuralMap.get(i);
                    if (kural == null) {
                        throw new RuntimeException(i + "|" + kuralMap.toString());
                    }
                    kurals.add(kuralMap.get(i));
                }
                athigaram++;
                titleOfKural.getValue().setKurals(kurals);
            }
            titleOfKuralsSorted.add(titleOfKural.getValue());
        }


       List<TitleOfKural> pals=  titleOfKuralsSorted.stream().filter(titleOfKural ->
           TYPE_PAL.equalsIgnoreCase(titleOfKural.getTypeOfTitle().getType())
        ).collect(Collectors.toUnmodifiableList());


        pals.forEach(pal -> {
             pal.setIyals( titleOfKuralsSorted.stream().filter(titleOfKural ->
                    TYPE_IYAL.equalsIgnoreCase(titleOfKural.getTypeOfTitle().getType())
                     && titleOfKural.getTypeOfTitle().getPal() == pal.getTypeOfTitle().getPal()
            ).collect(Collectors.toUnmodifiableList()));

        });

        List<TitleOfKural> iyals=  titleOfKuralsSorted.stream().filter(titleOfKural ->
                TYPE_IYAL.equalsIgnoreCase(titleOfKural.getTypeOfTitle().getType())
        ).collect(Collectors.toUnmodifiableList());

        iyals.forEach(iayl -> {
            iayl.setAthigarams( titleOfKuralsSorted.stream().filter(titleOfKural ->
                    TYPE_ADHIGARAM
                            .equalsIgnoreCase(titleOfKural.getTypeOfTitle().getType())
                            && titleOfKural.getTypeOfTitle().getPal() == iayl.getTypeOfTitle().getPal()
                            && titleOfKural.getTypeOfTitle().getIyal() == iayl.getTypeOfTitle().getIyal()
            ).collect(Collectors.toUnmodifiableList()));

        });

        File f = new File("C:\\logs\\kural.json");

        FileWriter wr = new FileWriter(f);
        wr.write(gson.toJson(titleOfKuralsSorted));
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

    private Integer computeKey(TypeOfTitle typeOfTitle) {
        return typeOfTitle.getAdhigaram() * 1 + typeOfTitle.getIyal()*100 + typeOfTitle.getPal()*1000;
    }

    private List<String> splitByLine(String text) {
        List<String> lines = new ArrayList<>();
        if  (!Strings.isNullOrEmpty(text)) {
           String[] strArr= text.split("\\n");
           for(int i=0; i< strArr.length; i++) {
                lines.add(strArr[i]);
           }
        }
        return  lines;
    }

    private String getTitle(String text) {

        return text.split("\\n")[0].replaceAll("^[\\s\\.,\\d]+", "");
    }


    public static class TitleOfKural {
        String title;
        TypeOfTitle typeOfTitle;

        List<TitleOfKural> athigarams;
        List<TitleOfKural> iyals;
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


        public List<TitleOfKural> getIyals() {
            return iyals;
        }

        public void setIyals(List<TitleOfKural> iyals) {
            this.iyals = iyals;
        }

        public List<TitleOfKural> getAthigarams() {
            return athigarams;
        }

        public void setAthigarams(List<TitleOfKural> athigarams) {
            this.athigarams = athigarams;
        }

        @Override
        public String toString() {
            return "TitleOfKural{" +
                    "title='" + title + '\'' +
                    ", typeOfTitle=" + typeOfTitle +
                    ", athigarams=" + athigarams +
                    ", iyals=" + iyals +
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
