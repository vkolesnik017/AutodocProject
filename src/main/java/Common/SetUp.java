package Common;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SetUp {

    private String skin;
    private DataBase db;

    public SetUp(){
        this.db = new DataBase();

    }

    public SetUp(String skin){
        this.skin = skin;
        this.db = new DataBase(this.skin);
    }

    private String shopFromJenkins = System.getenv("ShopFromJenkins");
    private String envFromJenkins = System.getenv("EnvFromJenkins");
    private String devBranchFromJenkins = System.getenv("devBranchFromJenkins");

    public String getShopsDesktop() {
        return shopsDesktop;
    }

    private String shopsDesktop = "";


    public static void setUpBrowser(Boolean Selenoid, String browser, String browserVersion, Boolean download) {
        HashMap<String,Object> chromePrefs = new HashMap<>();
        ChromeOptions options = new ChromeOptions();
        Configuration.browser = (browser);
        Configuration.browserVersion = (browserVersion);
        Configuration.startMaximized = true;
        Configuration.holdBrowserOpen = false;
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 60000;
        chromePrefs.put("plugins.always_open_pdf_externally", download);
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("password_manager_enabled", false);
        chromePrefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--dns-prefetch-disable");
        options.addArguments("--disable-gpu");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        Configuration.browserCapabilities = options;
        if (Selenoid) {
            Configuration.remote = "http://192.168.99.100:4444/wd/hub";
//            Configuration.driverManagerEnabled = false;
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            Configuration.browserCapabilities = capabilities;
        }
    }

    //TODO пусть данный метод пока лежит здесь, возможно пригодится в будующем.
    public static void setUpBrowserWithProxy(Boolean Selenoid, String browser, String browserVersion, Boolean download, String directoryPath) {
        setUpBrowser(Selenoid, browser, browserVersion, false);
        Configuration.downloadsFolder = directoryPath;
        Configuration.proxyEnabled = true;
        Configuration.fileDownload = FileDownloadMode.PROXY;
    }

    public Object[] setUpShop(String envFromTest, String shopFromTest) {
        String shop;
        if (!(shopFromJenkins == null)) shop = shopFromJenkins;
        else shop = shopFromTest;
//        if (!(envFromJenkins == null)) envFromTest = envFromJenkins;
        String env = getEnv(envFromTest);
        List<String> finalRouteList = new ArrayList<>();
        try {
            List<String> routeFromDB = new DataBase(skin).getRouteListForMain(shop);
            for (String aRouteFromDB : routeFromDB) {
                finalRouteList.add(env + aRouteFromDB);
            }
        } catch (Exception e) {
            System.out.println("setUpShop failed...");
        }
        return finalRouteList.toArray();
    }

    // Return list routes By Shops and route setUpShopWithRoute("prod", "AT,DE,CH", "lkw_main")
    public Object[] setUpShopsWithMainRoute(String envFromTest, String shopFromTest, String routeName) {
        String shop;
        if (!(shopFromJenkins == null)) shop = shopFromJenkins;
        else shop = shopFromTest;
//        if (!(envFromJenkins == null)) envFromTest = envFromJenkins;
        String env = getEnv(envFromTest);
        List<String> finalRouteList = new ArrayList<>();
        try {
            List<String> routeFromDB = new DataBase(skin).getRouteListByRouteName(shop, routeName);
            for (String aRouteFromDB : routeFromDB) {
                finalRouteList.add(env + aRouteFromDB);
            }
        } catch (Exception e) {
            System.out.println("setUpShop failed...");
        }
        return finalRouteList.toArray();
    }

    // Return list Shop + subroutes By Shop, main route and list subroutes ("prod", "DE", "lkw_main", "lkw_category_car_list,lkw_category_car_list2")
    public Object[] setUpShopWithSubroutes(String envFromTest, String shopFromTest, String routeName, String subRoutes) throws SQLException {
        String env = getEnv(envFromTest);
        List<String> mainRouteList = new ArrayList<>(db.getRouteListByRouteName(shopFromTest, routeName));
        List<String> subRoutesList = new ArrayList<>();
        List<String> finalSubRoutesList = new ArrayList<>();
        List<String> finalList = new ArrayList<>();
        String[] subRoute = subRoutes.split("\\,");
        //Adding String subRoutes in list subRoutesList
        Collections.addAll(subRoutesList, subRoute);
        for (String subRoutesParce : subRoutesList) {
            //Adding subRoutes in list getSubRoutesList
            List<String> getSubRoutesList = db.getRouteListByRouteName(shopFromTest, subRoutesParce);
            if (subRoutesParce.contains("main")) getSubRoutesList = Collections.singletonList("");
            finalSubRoutesList.addAll(getSubRoutesList);
        }
        for (String aSubRoutesList : finalSubRoutesList) {
            for (String aMainRouteList : mainRouteList) {
                finalList.add(env + aMainRouteList + "/" + aSubRoutesList);
            }
        }
        return finalList.toArray();
    }

    // Return list Shops + subroute By Shops, main route and subroute ("prod", "AT,DE", "lkw_main", "lkw_category_car_list")
    public Object[] setUpShopsWithSubroute(String envFromTest, String shopFromTest, String routeName, String subRoutes) throws SQLException {
        String env = getEnv(envFromTest);
        List<String> mainRouteList = new ArrayList<>(db.getRouteListByRouteName(shopFromTest, routeName));
        List<String> subRoutesList = new ArrayList<>(db.getRouteListByRouteName(shopFromTest, subRoutes));
        List<String> finalList = new ArrayList<>();
        for (int i = 0; i < mainRouteList.size(); i++) {
            String route = env + mainRouteList.get(i) + "/" + subRoutesList.get(i);
            finalList.add(route);
        }
        return finalList.toArray();
    }

    // Return list Shops + subroutes By Shops, main route and subroutes ("prod", "AT,DE", "lkw_main", "lkw_category_car_list,lkw_category_car_list2")
    public Object[] setUpShopsWithSubroutes(String envFromTest, String shopsFromTest, String routeName, String subRoutes) throws SQLException {
        List<String> finalList = new ArrayList<>();

        String[] subRoute = subRoutes.split("\\,");
        String[] shops = shopsFromTest.split("\\,");

        for(String fSub : subRoute){
            for (String fShops : shops){
                finalList.add(db.getFullRouteByRouteAndSubroute(envFromTest, fShops, routeName, fSub));
            }
        }
        return finalList.toArray();
    }

    // Return list Shop_param By Shops and String[] list setUpShopWithListParam("prod", "AT,DE,CH", list[])
    public Object[] setUpShopWithListParam(String envFromTest, String shopFromTest, String[] list) {
        Object[] shop = setUpShop(envFromTest, shopFromTest);
        List<String> shopList = new ArrayList<>();
        List<String> finalList = new ArrayList<>();
        for (Object shopPars : shop) {
            shopList.add(String.valueOf(shopPars));
        }
        for (String aList : list) {
            for (String aShopList : shopList) {
                finalList.add(aShopList + "_" + aList);
            }
        }
        return finalList.toArray();
    }


    String getEnv(String envFromTest) {
        if (!(envFromJenkins == null)) envFromTest = envFromJenkins;
        String env = null;
        switch (envFromTest) {
            case ("test"):
                env = "https://test.";
                break;
            case ("prod"):
                env = "https://www.";
                break;
            case ("subprod"):
                env = "https://";
                break;
            case ("mob"):
                env = "https://m.";
                break;
            case ("dev"):
                env = devBranchFromJenkins + ".";
                break;
        }
        return env;
    }

    public String getEnvForAws(String envFromTest) {
        if (!(envFromJenkins == null)) envFromTest = envFromJenkins;
        String awsEnv = null;
        switch (envFromTest) {
            case ("test"):
                awsEnv = "https://taws.";
                break;
            case ("prod"):
                awsEnv = "https://aws.";
                break;
        }
        return awsEnv;
    }
}
