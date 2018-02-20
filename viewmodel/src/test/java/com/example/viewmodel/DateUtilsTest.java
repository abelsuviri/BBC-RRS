package com.example.viewmodel;

import com.example.viewmodel.util.DateUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Abel Suviri
 */

public class DateUtilsTest {
    @Test
    public void testParseDate_behaves_correct() {
        String date = "Wed, 21 Feb 2018 14:35:58 GMT";
        String formattedDate = "21 Feb 2018 14:35";
        Assert.assertEquals(DateUtils.parseDate(date), formattedDate);
    }

    @Test
    public void testParseDate_behaves_incorrect() {
        String date = "21 February 2018 14:35:58 GMT";
        String formattedDate = "21 Feb 2018 14:35";
        Assert.assertNotEquals(DateUtils.parseDate(date), formattedDate);
    }
}
