package com.riege.onerecord.jsonutils;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Set;

class TestPojo {
    Set<String> types;
    String name;
    int answer;
    Date date;
    OffsetDateTime offsetDateTime;

    public Set<String> getTypes() {
        return types;
    }

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
