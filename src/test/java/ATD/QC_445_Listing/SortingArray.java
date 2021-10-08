package ATD.QC_445_Listing;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortingArray {
    public static void main(String[] args) {
        List<Integer> firstList = Arrays.asList(1, 2, 3);
        List<Integer> secondList = new ArrayList<>(firstList);
        Collections.sort(secondList);
        Assert.assertEquals(firstList,secondList);
    }
}
