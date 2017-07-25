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

    public TimeDescriptionBuilder withEvaluationDate(DateTime evaluationDate) {
        this.evaluationDate = evaluationDate;
        return this;
    }

    public TimeDescriptionBuilder withEvaluationDate(Date evaluationDate) {
        this.evaluationDate = new DateTime().withMillis(evaluationDate.getTime());
        return this;
    }

    public String build() {
        if (this.evaluationDate == null) return null;
        if (evaluationDate.getDayOfYear() == this.startTime.getDayOfYear()) {
            final int minutes = Minutes.minutesBetween(evaluationDate, this.startTime).getMinutes();
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
                return "A few moments ago...";
            }
        } else {
            int days = Days.daysBetween(evaluationDate.withTimeAtStartOfDay(), this.startTime.withTimeAtStartOfDay()).getDays();
            if (days == 1) {
                return "Yesterday";
            } else if (days == 0) {
                return "Today";
            } else {
                return evaluationDate.toString("dd MM yyyy");
            }
        }
    }
}
