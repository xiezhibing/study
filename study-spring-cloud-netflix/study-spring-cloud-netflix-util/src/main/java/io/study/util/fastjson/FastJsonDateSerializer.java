package io.study.util.fastjson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import io.study.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastJsonDateSerializer implements ObjectSerializer, ObjectDeserializer {
    private static Logger logger = LoggerFactory.getLogger(FastJsonDateSerializer.class);
    public final static FastJsonDateSerializer instance = new FastJsonDateSerializer();
    public final static String format = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
		if(object != null){
		    if(object instanceof Date) {
                Date date = (Date)object;
                String value = DateUtil.getDateFormat(date);
                serializer.write(value);
            } else {
                String value = object.toString();
                serializer.write(value);
            }
		} else {
			serializer.getWriter().writeNull();
			return;
		}
    }

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

}