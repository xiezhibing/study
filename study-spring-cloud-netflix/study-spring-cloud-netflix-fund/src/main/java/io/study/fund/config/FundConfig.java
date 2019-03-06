package io.study.fund.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import io.study.util.converter.DateConverter;
import io.study.util.fastjson.DateFastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/2/20 0020.
 */
@Configuration
public class FundConfig {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 增加字符串转日期的功能
     */
    @PostConstruct
    public void addDateConverter() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService()!=null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(new DateConverter());
        }
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        DateFastJsonHttpMessageConverter fastJsonHttpMessageConverter = new DateFastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = fastJsonHttpMessageConverter.getFastJsonConfig();
        List<MediaType> fastMediaTypes = new ArrayList();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.SkipTransientField
        );

        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastJsonHttpMessageConverter;
        return new HttpMessageConverters(converter);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

}
