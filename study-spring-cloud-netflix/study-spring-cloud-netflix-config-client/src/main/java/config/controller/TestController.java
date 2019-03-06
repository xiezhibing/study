package config.controller;

import config.config.DymicConfigData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2019/2/21 0021.
 */
@RestController
@RefreshScope
public class TestController {


    @Value("${config.code}")
    public String code;
    @Value("${config.name}")
    public String name;
    @Value("${config.version}")
    public String version;

    @RequestMapping("/config")
    public String test(){

        return "code: "+code + ", name: "+name + ", version:"+version;

    }
}
