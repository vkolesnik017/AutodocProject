package ATD.QC_445_Listing;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> projectName = Arrays.asList("ios_atd_se", "ios_atd_de", "ios_atd_uk");
        List<String> searchLanguage = Arrays.asList("uk", "de", "at");
        String phrase = searchElement(projectName, searchLanguage);
        System.out.println(phrase);
    }

    public static String searchElement(List<String> firstList, List<String> secondList) {
        int sumOfСoincidences = 0;
        for (int i = 0; i < firstList.size(); i++) {
            for (int j = 0; j < secondList.size(); j++) {
                if (firstList.get(i).matches("^.+\\_" + secondList.get(j))) { // ЭТО УСЛОВИЕ БУДЕТ ОТРАБАТЫВАТЬ ТОЛЬКО В ТОМ СЛУЧАЕ, ЕСЛИ У ТЕБЯ ВСЕ ЗНАЧЕНИЯ БУДУТ В ФОРМАТЕ ios_atd_de
                    sumOfСoincidences++;
                }
            }
        }
        if (sumOfСoincidences >= 2) {
            return "It's a big digit";
        } else
            return "It's a small digit";
    }
}
