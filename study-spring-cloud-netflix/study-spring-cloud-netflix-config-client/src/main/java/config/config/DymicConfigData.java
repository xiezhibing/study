package config.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/2/21 0021.
 */
@Configuration
@RefreshScope
public class DymicConfigData {

    @Value("${config.code}")
    public String code;
    @Value("${config.name}")
    public String name;
    @Value("${config.version}")
    public String version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
