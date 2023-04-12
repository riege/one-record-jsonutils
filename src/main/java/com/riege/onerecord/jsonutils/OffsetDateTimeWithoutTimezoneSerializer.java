package com.riege.onerecord.jsonutils;

import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;

final class OffsetDateTimeWithoutTimezoneSerializer extends OffsetDateTimeSerializer {

    OffsetDateTimeWithoutTimezoneSerializer() {
        super(OffsetDateTimeSerializer.INSTANCE, null, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
