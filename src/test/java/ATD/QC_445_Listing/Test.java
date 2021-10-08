package ATD.QC_445_Listing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        String str = "GB 123456789";

        String str2= str.replaceAll("\\s","").substring(1,str.length()-1);
        System.out.println(str2);

        boolean label = str.matches("^\\D{2}\\s\\d{9}") ? true:false;

        System.out.println(label
        );

        List<String> shop = Arrays.asList("DE", "BE", "NO", "FR", "IT");
        List<String> expectedArray = new ArrayList<>();
        while (expectedArray.size() != 3) {
            int i = 1 + (int) (Math.random() * shop.size());
            if (expectedArray.contains(shop.get(i))) {
                continue;
            } else {
                expectedArray.add(shop.get(i));
            }
        }
        expectedArray.forEach(System.out::println);
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
