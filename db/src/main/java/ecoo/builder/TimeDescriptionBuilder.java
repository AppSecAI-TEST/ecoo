package ecoo.builder;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;

import java.util.Date;

/**
 * @author Justin Rundle
 * @since April 2017
 */
public class TimeDescriptionBuilder {

    private DateTime startTime = DateTime.now();

    private DateTime evaluationDate;

    private TimeDescriptionBuilder() {
    }

    public static TimeDescriptionBuilder aTimeDescription() {
        return new TimeDescriptionBuilder();
    }


    public TimeDescriptionBuilder withStartTime(Date startTime) {
        this.startTime = new DateTime().withMillis(startTime.getTime());
        return this;
    }

    public TimeDescriptionBuilder witEvaluationDate(DateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
        return this;
    }

    public TimeDescriptionBuilder witEvaluationDate(Date evaluationDate) {
        this.evaluationDate = new DateTime().withMillis(evaluationDate.getTime());
        return this;
    }

    public String build() {
        if (this.evaluationDate == null) return null;
        if (evaluationDate.getDayOfYear() == this.startTime.getDayOfYear()) {
            final int minutes = Minutes.minutesBetween(this.startTime, evaluationDate).getMinutes();
            if (minutes >= 240) {
                return "Earlier today...";
            } else if (minutes >= 180) {
                return "About 3 hours ago...";
            } else if (minutes >= 120) {
                return "About 2 hours ago...";
            } else if (minutes >= 60) {
                return "About 1 hour ago...";
            } else if (minutes > 10) {
                return "More than 10 minutes...";
            } else if (minutes > 5) {
                return "More than 5 minutes...";
            } else if (minutes > 2) {
                return "More than 2 minutes...";
            } else if (minutes > 1) {
                return "More than 1 minute...";
            } else {
                return "Less than 1 minute...";
            }
        } else {
            int days = Days.daysBetween(evaluationDate.withTimeAtStartOfDay(), this.startTime.withTimeAtStartOfDay()).getDays();
            if (days >= 400) {
                return "Like forever ago..";
            }
            if (days >= 365) {
                return "More than 1 year ago...";
            } else if (days >= 180) {
                return "More than 6 months ago...";
            } else if (days >= 90) {
                return "More than 3 months ago...";
            } else if (days >= 60) {
                return "More than 2 months ago...";
            } else if (days >= 28) {
                return "About 1 month ago...";
            } else if (days >= 21) {
                return "About 3 weeks ago...";
            } else if (days >= 14) {
                return "About 2 weeks ago...";
            } else if (days >= 7) {
                return "About 1 week ago...";
            } else if (days == 1) {
                return "About 1 day ago...";
            } else {
                return "About " + days + " days ago...";
            }
        }
    }
}
