package com.riege.onerecord.jsonutils;

import java.time.OffsetDateTime;
import java.util.Date;

class TestPojo {
    String name;
    int answer;
    Date date;
    OffsetDateTime offsetDateTime;

    public String getName() {
        return name;
    }

    public int getAnswer() {
        return answer;
    }

    public Date getDate() {
        return date;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }
}
