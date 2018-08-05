package com.yuanheng100.settlement;

import com.yuanheng100.settlement.common.conts.CommonDef;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/resources/applicationContext-provider.xml"})
public class BaseTest
{
    protected static final String BAISONG_ID = "10020";
    protected static final String BAISONG_NAME = "柏松";
    protected static final BigDecimal BAISONG_INVEST_AMOUNT = new BigDecimal("1.10");
    protected static final String BAISONG_IDCARD = "370102198004222910";

    protected static final String BAISONG_ACC_BANK = "中国工商银行股份有限公司";
    protected static final Short BAISONG_ACC_BANK_CODE = (short) 102;
    protected static final String BAISONG_ACC_NUM = "9558800200141836664";


    protected static final String QIANJIALIANG_ID = "10021";
    protected static final String QIANJIALIANG_NAME = "钱家亮";
    protected static final String QIANJIALIANG_IDCARD = "370481199003245711";
    protected static final BigDecimal QIANJIALIANG_INVEST_AMOUNT = new BigDecimal("0.90");

    protected static final String WANGGUANGSHUO_ID = "10023";
    protected static final String WANGGUANGSHUO_NAME = "王光硕";

    protected static final String BORROWER_ID = "10030";
    protected static final String BORROWER_NAME = "李缺钱";
    protected static final String BORROWER_ID_CARD = "211081197707137618";


    protected static final String LOAN_ID = "104";


    public static <T> T getBean(Class<T> clazz)
    {
        System.setProperty("settlement.dir", CommonDef.ROOT_DIR);

        ApplicationContext context = new ClassPathXmlApplicationContext(
                "file:src/main/resources/applicationContext-provider.xml");
        return context.getBean(clazz);
    }
}
