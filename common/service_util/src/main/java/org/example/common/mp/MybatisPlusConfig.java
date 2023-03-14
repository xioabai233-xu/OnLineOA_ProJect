package org.example.common.mp;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.auth.mapper")
public class MybatisPlusConfig {

    /**
     * æ°çåé¡µæä»¶,ä¸ç¼åäºç¼éµå¾ªmybatisçè§å,éè¦è®¾ç½® MybatisConfiguration#useDeprecatedExecutor = false é¿åç¼å­åºç°é®é¢(è¯¥å±æ§ä¼å¨æ§æä»¶ç§»é¤åä¸åç§»é¤)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
