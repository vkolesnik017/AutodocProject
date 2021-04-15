package ATD.QC_445_Listing;

import ATD.Category_car_list_page_Logic;
import Common.SetUp;
import io.qameta.allure.Description;
import io.qameta.allure.Flaky;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static ATD.CommonMethods.openPage;
import static Common.SetUp.setUpBrowser;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class QC_2061 {
    List<String> expectedGenerics = Arrays.asList("Luftmassenmesser",
            "Impulsgeber, Kurbelwelle", "Sensor, Nockenwellenposition", "Sensor, Kühlmitteltemperatur",
            "Sensor, Ladedruck", "Sensor, Zündimpuls", "Sensor, Fahrpedalstellung",
            "Klopfsensor", "Sensor, Ansauglufttemperatur", "Drehzahlsensor, Motormanagement",
            "Öldruckschalter", "Steuergerät, Motormanagement", "Luftmengenmesser",
            "Sensor, Kühlmitteltemperatur", "Sensor, Ladedruck", "Sensor, Öldruck",
            "Sensor, Öltemperatur", "Steuergerät, Kraftstoffeinspritzung", "Sensor, Kühlmitteltemperatur",
            "Steuergerät, Zündanlage", "Sensor, Kühlmitteltemperatur", "Steuergerät, Einspritzanlage",
            "Schalter, Kupplungsbetätigung (Motorsteuerung)", "Sensor, Öltemperatur / -druck", "Gehäuse, Luftmengenmesser",
            "Schalter, Bremsbetätigung (Motorsteuerung)", "Sensor, Zylinderkopftemperatur", "Lambdasondensatz",
            "Elektromotor, Gebläse Steuergerätebox");
    @BeforeClass
    void setUp() {
        setUpBrowser(false, "chrome", "77.0", false);
    }

    @DataProvider(name = "route")
    Object[] dataProvider() throws SQLException {
        return new SetUp("ATD").setUpShopWithSubroutes("prod", "DE", "main", "category_car_list31");
    }

    @Test(dataProvider = "route")
    @Flaky
    @Owner(value = "kolesnik")
    @Description(value = "Checks Ridex prioritization  and another products in TecDoc listing")
    public void testCheckRidexPrioritizationInTecDocListing(String route) {
        openPage(route);
        new Category_car_list_page_Logic()
                .checkTecDocListing(expectedGenerics);
    }

    @AfterMethod
    private void close() {
        closeWebDriver();
    }
}
