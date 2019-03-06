package io.study.util.fastjson;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * TODO JSON序列化转化器
 *
 * @author XieZhibing
 * @date 2017年3月8日 下午6:17:58
 * @version <b>1.0.0</b>
 */
public class DateFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    private static Logger logger = LoggerFactory.getLogger(DateFastJsonHttpMessageConverter.class);

    public DateFastJsonHttpMessageConverter() {
        super();
        this.getFastJsonConfig().getSerializeConfig().put(Date.class, FastJsonDateSerializer.instance);
    }

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        super.writeInternal(obj, outputMessage);
    }

}