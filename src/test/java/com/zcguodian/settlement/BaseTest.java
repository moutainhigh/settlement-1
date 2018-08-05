package com.zcguodian.settlement;

import com.yuanheng100.settlement.common.conts.CommonDef;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/resources/applicationContext-provider.xml"})
public class BaseTest
{

    public static <T> T getBean(Class<T> clazz)
    {
        System.setProperty("settlement.dir", CommonDef.ROOT_DIR);

        @SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
                "file:src/main/resources/applicationContext-provider.xml");
        return context.getBean(clazz);
    }
}
