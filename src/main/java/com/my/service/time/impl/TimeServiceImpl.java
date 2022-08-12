package com.my.service.time.impl;

import com.my.service.time.TimeService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeServiceImpl implements TimeService {

    @Override
    public String now() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(TIME_FORMAT);
        return fmt.print(DateTime.now());
    }
}
