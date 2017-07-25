package ecoo.builder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ecoo-db-test-context.xml"})
public class TimeDescriptionBuilderTest {
    @Test
    public void testMoreThan1Minute() {
        final String description = TimeDescriptionBuilder.aTimeDescription()
                .withStartTime(DateTime.parse("20170725 06:22", DateTimeFormat.forPattern("yyyyMMdd HH:mm")).toDate())
                .withEvaluationDate(DateTime.parse("20170725 06:20", DateTimeFormat.forPattern("yyyyMMdd HH:mm")))
                .build();

        Assert.assertEquals("More than 1 minute...", description);
    }

    @Test
    public void testMoreThan2Minute() {
        final String description = TimeDescriptionBuilder.aTimeDescription()
                .withStartTime(DateTime.parse("20170725 06:24", DateTimeFormat.forPattern("yyyyMMdd HH:mm")).toDate())
                .withEvaluationDate(DateTime.parse("20170725 06:20", DateTimeFormat.forPattern("yyyyMMdd HH:mm")))
                .build();

        Assert.assertEquals("More than 2 minutes...", description);
    }
}