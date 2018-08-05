package com.yuanheng100.settlement.common.listener;

import com.yuanheng100.settlement.common.conts.CommonDef;
import com.yuanheng100.util.SystemDetails;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SystemStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SystemDetails.outputDetails();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
