package com.xzcode.autoconfig;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.xzcode.socket.core.config.SocketServerConfig;
import com.xzcode.socket.core.executor.SocketServerTaskExecutor;
import com.xzcode.socket.core.serializer.factory.SerializerFactory;
import com.xzcode.socket.core.starter.SocketServerStarter;
import com.xzcode.socket.core.starter.impl.DefaultSocketServerStarter;
import com.xzcode.socket.core.starter.impl.WebSocketServerStarter;
import com.xzcode.socket.core.utils.SocketServerService;

@Configuration
@ConditionalOnProperty(prefix = SocketAutoConfig.PROPERTIES_PREFIX, name = "enabled", havingValue = "true")
@ConditionalOnMissingBean({SocketServerStarter.class})
public class SocketAutoConfig implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketAutoConfig.class);

    public static final String PROPERTIES_PREFIX = "xz.socket";

    private ApplicationContext applicationContext;

    @Bean
    public SocketServerStarter socketServerStarter() {
    	
    	SocketServerStarter starter = null;;

        SocketServerConfig config = socketServerConfig();
        
        config.setTaskExecutor(new SocketServerTaskExecutor(config));
        
        config.setSerializer(SerializerFactory.geSerializer(config.getSerializerType()));

        LOGGER.info(config.toString());


        if (SocketServerConfig.ServerTypeConstants.SOCKET.equals(config.getServerType())) {
        	starter =  new DefaultSocketServerStarter(config);

        } else if (SocketServerConfig.ServerTypeConstants.WEBSOCKET.equals(config.getServerType())) {
        	starter = new WebSocketServerStarter(config);
        } else {
            Assert.state(true, "Unsupported serverType:" + config.getServerType() + "!");
        }
        
        beanDefinitionRegistry(config);
        
        //是否自动运行
        if (config.isAutoRun()) {
        	starter.run();
		}
        
        return starter;
    }
    
    

    @Bean
    @ConfigurationProperties(prefix = SocketAutoConfig.PROPERTIES_PREFIX)
    public SocketServerService socketServerService() {
        return new SocketServerService(socketServerConfig());
    }
    
    
    @Bean
    @ConfigurationProperties(prefix = SocketAutoConfig.PROPERTIES_PREFIX)
    public SocketServerConfig socketServerConfig() {
    	return new SocketServerConfig();
    }

    /**
     * 动态注册bean到spring
     *
     * @author zai
     * 2017-10-30
     */
    public void beanDefinitionRegistry(SocketServerConfig config) {

        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();

        Map<Class<?>, Object> map = config.getComponentObjectManager().getComponentMap();
        for (Class<?> key : map.keySet()) {

            String beanName = StringUtils.uncapitalize(key.getSimpleName());


            //创建bean信息.
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(key);

            //动态注册bean.
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
            Object bean = applicationContext.getBean(beanName);
            LOGGER.info("Socket server bean defind:{}", bean);
            map.put(key, bean);
            
        }
        
        //更新组件对象
        config.getMessageInvokerManager().updateComponentObject(config.getComponentObjectManager());
        config.getEventInvokerManager().updateComponentObject(config.getComponentObjectManager());
        config.getMessageFilterManager().updateComponentObject(config.getComponentObjectManager());


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
