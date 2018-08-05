package com.yuanheng100.settlement.startup;

import java.io.File;

import javax.servlet.ServletException;

import com.yuanheng100.util.ConfigFile;
/*import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;*/
import org.apache.log4j.Logger;

/**
 * tomcat启动类
 * 
 * @author 田伟
 * 
 */
public class ConsumerTomcatMain
{
    /*private static final Logger log = Logger.getLogger(ConsumerTomcatMain.class);

    private static final String PORTSTR = ConfigFile.getProperty("system.web.port");
    private static final String CONTEXT_PATH = "/settlement"; // 项目名称
    private static final String PROJECT_PATH = ClassLoader.getSystemClassLoader().getResource("").getPath();
    //private static final String GLOBAL_XML_PATH = PROJECT_PATH + File.separatorChar + "conf" + File.separatorChar + "web.xml";
    private static final String WEB_APP_PATH = PROJECT_PATH + File.separatorChar + "webapp";
    private static final String TEMP_PATH = PROJECT_PATH + File.separatorChar + "temp";
    private static final String WORK_PATH = PROJECT_PATH + File.separatorChar + "work";

    private Tomcat tomcat = new Tomcat();

    public void start()
    {
        log.info("Tomcat starting.");
        tomcat.setBaseDir(TEMP_PATH);
        tomcat.setPort(Integer.parseInt(PORTSTR));
        StandardHost standardHost = (StandardHost) tomcat.getHost();
        //standardHost.setAppBase(WEB_APP_PATH);//默认为webapps(项目只有一个应用)
        standardHost.setWorkDir(WORK_PATH);
        //standardHost.setXmlBase(GLOBAL_XML_PATH);//最后取的是空值，这个设置没什么用

        try
        {
            StandardServer server = (StandardServer) tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            server.addLifecycleListener(listener);
            this.tomcat.addWebapp(CONTEXT_PATH, WEB_APP_PATH);
            //GET请求中文乱码修正为UTF-8字符集
            Connector connector = tomcat.getConnector();
*//*            connector.setScheme("https");
            connector.setSecure(true);
            connector.setProtocol("org.apache.coyote.http11.Http11Protocol");
            connector.setAttribute("SSLEnabled",true);
            connector.setAttribute("sslProtocol","TLS");
            connector.setAttribute("SSLEngine","on");
            connector.setAttribute("clientAuth",false);
            connector.setAttribute("SSLCertificateFile", ConfigFile.getProperty("system.SSLCertificateFile"));
            connector.setAttribute("SSLCertificateKeyFile",ConfigFile.getProperty("system.SSLCertificateKeyFile"));*//*


            //connector.setAttribute("SSLPassword","yuanheng");

            connector.setURIEncoding("UTF-8");
        }
        catch (ServletException e)
        {
            log.error(e.getMessage());
        }
        try
        {
            tomcat.start();
        }
        catch (LifecycleException e)
        {
            log.error(e.getMessage());
        }
        log.info("Tomcat started.");
    }

    public void stop() throws Exception
    {
        log.info("Tomcat stopping.");
        try
        {
            tomcat.stop();
            tomcat.destroy();
        }
        catch (LifecycleException ex)
        {
            log.error(ex.getMessage());
        }
        log.info("Tomcat stoped");
    }*/
}
