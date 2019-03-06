package io.study.util.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateConverter implements Converter<String, Date> {
    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);
    private static final String datePatten = "yyyy-MM-dd";
    private static final String dateTimePatten = "yyyy-MM-dd HH:mm:ss";
    private static final String dateTimePatten1 = "yyyy-MM-dd HH:mm";
    private static final String dateTimePatten2 = "yyyy-MM-dd HH";

    @Override
    public Date convert(String value) {

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        value = value.trim();

        try {
            Date date = null;
            String pattern = "";
            SimpleDateFormat formatter;
            if (value.matches("^\\d{4}\\D+\\d{2}\\D+\\d{2}$")) {
                pattern = datePatten;
            } else if (value.matches("^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$")) {
                pattern = dateTimePatten;
            } else if (value.matches("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$")){
                pattern = dateTimePatten1;
            } else if(value.matches("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$")){
                pattern = dateTimePatten2;
            }

            if(datePatten.equals(pattern)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                dateFormat.setTimeZone(TimeZone.getDefault());
                date = dateFormat.parse(value);
            }

            logger.debug("value:{}, date:{}", value, date);
            return date;
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", value));
        }

    }



}