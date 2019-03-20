package com.example.cms.config;

import com.example.cms.dao.SessionRedisDao;
import com.example.cms.interceptor.SSOFilter;
import com.example.cms.shiro.MyRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    @Bean
    public SessionRedisDao sessionRedisDao() {
        SessionRedisDao sessionRedisDao = new SessionRedisDao();
        return sessionRedisDao;
    }

    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(43200000); //12小时
        sessionManager.setDeleteInvalidSessions(true);

        sessionManager.setSessionDAO(sessionRedisDao());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        securityManager.setSessionManager(defaultWebSessionManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, String> filterMap = new HashMap<>();
        //filterMap.put("/index","authc");
        //filterMap.put("/index","anon");
        filterMap.put("/jsp/**", "authc");
        filterMap.put("/resources/**", "anon");
        filterMap.put("/error", "anon");
        filterMap.put("/**", "authc");
        filterMap.put("/sso/doLogin", "anon");
        //filterMap.put("/**","user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        HashMap<String, Filter> filters = new HashMap();
        filters.put("user", new SSOFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthor");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }
}
