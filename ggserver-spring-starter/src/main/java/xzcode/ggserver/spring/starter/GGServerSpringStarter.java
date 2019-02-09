package xzcode.ggserver.spring.starter;

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

import xzcode.ggserver.core.GGServer;
import xzcode.ggserver.core.config.GGServerConfig;
import xzcode.ggserver.core.handler.serializer.factory.SerializerFactory;
import xzcode.ggserver.core.message.receive.executor.RequestMessageTaskExecutor;
import xzcode.ggserver.core.starter.IGGServerStarter;
import xzcode.ggserver.core.starter.impl.DefaultSocketServerStarter;
import xzcode.ggserver.core.starter.impl.WebSocketServerStarter;

@Configuration
@ConditionalOnProperty(prefix = GGServerSpringStarter.PROPERTIES_PREFIX, name = "enabled", havingValue = "true")
@ConditionalOnMissingBean({IGGServerStarter.class})
public class GGServerSpringStarter implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(GGServerSpringStarter.class);

    public static final String PROPERTIES_PREFIX = "xz.socket";

    private ApplicationContext applicationContext;

    @Bean
    public IGGServerStarter iGGServerStarter() {
    	
    	IGGServerStarter starter = null;;

        GGServerConfig config = gGServerConfig();
        
        config.setTaskExecutor(new RequestMessageTaskExecutor(config));
        
        config.setSerializer(SerializerFactory.geSerializer(config.getSerializerType()));

        LOGGER.info(config.toString());


        if (GGServerConfig.ServerTypeConstants.SOCKET.equals(config.getServerType())) {
        	starter =  new DefaultSocketServerStarter(config);

        } else if (GGServerConfig.ServerTypeConstants.WEBSOCKET.equals(config.getServerType())) {
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
    @ConfigurationProperties(prefix = GGServerSpringStarter.PROPERTIES_PREFIX)
    public GGServer gGServer() {
        return new GGServer(gGServerConfig());
    }
    
    
    @Bean
    @ConfigurationProperties(prefix = GGServerSpringStarter.PROPERTIES_PREFIX)
    public GGServerConfig gGServerConfig() {
    	return new GGServerConfig();
    }

    /**
     * 动态注册bean到spring
     *
     * @author zai
     * 2017-10-30
     */
    public void beanDefinitionRegistry(GGServerConfig config) {

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
