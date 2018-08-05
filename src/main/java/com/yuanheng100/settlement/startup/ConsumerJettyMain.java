package com.yuanheng100.settlement.startup;

import java.io.File;
import java.security.KeyStore;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

import com.yuanheng100.util.ConfigFile;

/**
 * tomcat启动类
 *
 * @author 田伟
 */
public class ConsumerJettyMain
{
    private static final Logger log = Logger.getLogger(ConsumerJettyMain.class);

    private static final String HTTP_PORT = ConfigFile.getProperty("system.web.port");
    private static final String HTTPS_PORT = ConfigFile.getProperty("system.https.port");
    private static final String CONTEXT_PATH = "/settlement"; // 项目名称
    private static final String PROJECT_PATH = ClassLoader.getSystemClassLoader().getResource("").getPath();
    //private static final String GLOBAL_XML_PATH = PROJECT_PATH + File.separatorChar + "conf" + File.separatorChar + "web.xml";
    private static final String WEB_APP_PATH = PROJECT_PATH + File.separatorChar + "webapp";
    private static final String TEMP_PATH = PROJECT_PATH + File.separatorChar + "temp";
//    private static final String CONF_PATH = PROJECT_PATH + File.separatorChar + "conf/";
    private static final String CONF_PATH = PROJECT_PATH + File.separatorChar;
    private static final String KEY_STORE_FILE = ConfigFile.getProperty("ghbank.keystore.file");
    private static final String TRUST_STORE_FILE = ConfigFile.getProperty("ghbank.truststore.file");
    private static final String KEY_STORE_PASSWORD = ConfigFile.getProperty("ghbank.keystore.password");
    private static final String TRUST_STORE_PASSWORD = ConfigFile.getProperty("ghbank.truststore.password");
   

    private Server server;

    public static void main(String[] args)
    {
        new ConsumerJettyMain().start();
    }

    public void start()
    {
        log.info("");
        log.info("Jetty 开始启动...");

        server = new Server();

        // http通道
        SelectChannelConnector connector_http = new SelectChannelConnector();
        connector_http.setPort(Integer.parseInt(HTTP_PORT));
        connector_http.setMaxIdleTime(30000);

        // https通道
        SslSelectChannelConnector connector_https = new SslSelectChannelConnector();
        connector_https.setPort(Integer.parseInt(HTTPS_PORT));
        SslContextFactory cf = connector_https.getSslContextFactory();
        cf.setKeyStore(CONF_PATH + KEY_STORE_FILE);
        cf.setKeyStoreType("PKCS12");
        cf.setKeyStorePassword(KEY_STORE_PASSWORD);
        cf.setKeyManagerPassword(KEY_STORE_PASSWORD);
        
        cf.setTrustStore(CONF_PATH + TRUST_STORE_FILE);
        cf.setTrustStorePassword(TRUST_STORE_PASSWORD);
        cf.setTrustStoreType("JKS");

        WebAppContext context = new WebAppContext(WEB_APP_PATH, CONTEXT_PATH);
        context.setDescriptor(WEB_APP_PATH + "/WEB-INF/web.xml");
        context.setResourceBase(WEB_APP_PATH);
        context.setContextPath(CONTEXT_PATH);
        context.setTempDirectory(new File(TEMP_PATH));
        context.setParentLoaderPriority(true);
        context.setClassLoader(Thread.currentThread().getContextClassLoader());

//        server.setConnectors(new Connector[] { connector_http, connector_https });
        server.setConnectors(new Connector[] { connector_http });
        server.setHandler(context);
        try
        {
            server.start();
//            server.join();
        }
        catch (Exception e)
        {
            log.error("Jetty启动时发生异常", e);
        }
        log.info("Jetty 启动完毕");
    }

    public void stop() throws Exception
    {
        log.info("Jetty stopping...");
        server.stop();
        server.destroy();
        log.info("Jetty stoped");
    }
}
