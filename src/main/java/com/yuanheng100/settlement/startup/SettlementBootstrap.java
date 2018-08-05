package com.yuanheng100.settlement.startup;

import com.yuanheng100.settlement.common.conts.CommonDef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.security.AccessControlException;
import java.util.Random;

/**
 * Created by jlqian on 2016/8/4.
 */
public class SettlementBootstrap {

    static {
        System.setProperty("settlement.dir", CommonDef.ROOT_DIR);
    }

    private static final Log log = LogFactory.getLog(SettlementBootstrap.class);

    private static SettlementBootstrap daemon = null;

    private static boolean stopAwait = false;

    private static String address = "localhost";

    private static int port = 65530;

    private volatile Thread awaitThread = null;

    private volatile ServerSocket awaitSocket = null;

    private static String shutdown = "SHUTDOWN";


    /**
     * 启动与关闭
     * 启动加参数: start
     * 关闭加参数：stop
     *
     * @param args
     */
    public static void main(String args[])
    {

        daemon = new SettlementBootstrap();

        try
        {
            String command = "start";
            if (args.length > 0)
            {
                command = args[args.length - 1];
            }
            if (command.equals("start"))
            {
                daemon.start();
            }
            else if (command.equals("stop"))
            {
                daemon.stop();
            }
            else
            {
                log.warn("SettlementBootstrap: command \"" + command + "\" does not exist.");
            }
        }
        catch (Throwable t)
        {
            // Unwrap the Exception for clearer error reporting
            if (t instanceof InvocationTargetException && t.getCause() != null)
            {
                t = t.getCause();
            }
            handleThrowable(t);
            log.error("启动时发生异常", t);
            System.exit(1);
        }
    }

    /**
     * 为使用ClassLoader解决Classpath过长的问题，使用反射进行启动，而不是直接创建对象
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public void start() throws IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException
    {
        // 相同的启动方法名
        String startMethodName = "start";
        // 类加载器获取提供者、消费者的Class
        Class<?> providerClass = this.getClass().getClassLoader().loadClass("com.yuanheng100.settlement.startup.ProviderMain");
        Class<?> consumerClass = this.getClass().getClassLoader().loadClass("com.yuanheng100.settlement.startup.ConsumerJettyMain");
        // 反射获取提供者、消费者的实例
        Object consumerInstance = consumerClass.newInstance();
        Object providerInstance = providerClass.newInstance();
        // 反射获取提供者、消费者的启动方法
        Method providerStartMethod = providerInstance.getClass().getMethod(startMethodName);
        Method consumerStartMethod = consumerInstance.getClass().getMethod(startMethodName);
        // 反射执行提供者、消费者的启动方法
        providerStartMethod.invoke(providerInstance);
        consumerStartMethod.invoke(consumerInstance);

        // 主线程，监听65530端口，收到信息即停止系统
        try
        {
            awaitSocket = new ServerSocket(port, 1, InetAddress.getByName(address));
        }
        catch (IOException e)
        {
            log.error("StandardServer.await: create[" + address + ":" + port + "]: ", e);
            return;
        }

        try
        {
            log.info("关机监听开始" + InetAddress.getByName("localhost") + ":" + port);
        }
        catch (UnknownHostException e)
        {
            log.error("关机时出现UnknownHostException", e);
        }
        log.info("");
        log.info("整个结算系统启动完成！");
        try
        {
            awaitThread = Thread.currentThread();

            // Loop waiting for a connection and a valid command
            while (!stopAwait)
            {
                ServerSocket serverSocket = awaitSocket;
                if (serverSocket == null)
                {
                    break;
                }

                // Wait for the next connection
                Socket socket = null;
                StringBuilder command = new StringBuilder();
                try
                {
                    InputStream stream;
                    long acceptStartTime = System.currentTimeMillis();
                    try
                    {
                        socket = serverSocket.accept();
                        socket.setSoTimeout(10 * 1000); // Ten seconds
                        stream = socket.getInputStream();
                    }
                    catch (SocketTimeoutException ste)
                    {
                        // This should never happen but bug 56684 suggests that
                        // it does.
                        log.warn(System.currentTimeMillis() - acceptStartTime, ste);
                        continue;
                    }
                    catch (AccessControlException ace)
                    {
                        log.warn("StandardServer.accept security exception: " + ace.getMessage(), ace);
                        continue;
                    }
                    catch (IOException e)
                    {
                        if (stopAwait)
                        {
                            // Wait was aborted with socket.close()
                            break;
                        }
                        log.error("StandardServer.await: accept: ", e);
                        break;
                    }
                    // Read a set of characters from the socket
                    int expected = 1024; // Cut off to avoid DoS attack
                    Random random = null;
                    while (expected < shutdown.length())
                    {
                        if (random == null)
                            random = new Random();
                        expected += (random.nextInt() % 1024);
                    }
                    while (expected > 0)
                    {
                        int ch = -1;
                        try
                        {
                            ch = stream.read();
                        }
                        catch (IOException e)
                        {
                            log.warn("StandardServer.await: read: ", e);
                            ch = -1;
                        }
                        if (ch < 32) // Control character or EOF terminates loop
                            break;
                        command.append((char) ch);
                        expected--;
                    }
                }
                finally
                {
                    // Close the socket now that we are done with it
                    try
                    {
                        if (socket != null)
                        {
                            socket.close();
                        }
                    }
                    catch (IOException e)
                    {
                        // Ignore
                    }
                }

                // Match against our command string
                boolean match = command.toString().equals(shutdown);
                if (match)
                {
                    log.info("Server.shutdownViaPort:" + port);
                    break;
                }
                else
                    log.warn("StandardServer.await: Invalid command '" + command.toString() + "' received");
            }
        }
        finally
        {

            ServerSocket serverSocket = awaitSocket;
            awaitThread = null;
            awaitSocket = null;

            // Close the server socket and return
            if (serverSocket != null)
            {
                try
                {
                    serverSocket.close();
                }
                catch (IOException e)
                {
                    // Ignore
                }
            }
        }

        /**
         * 执行停机步骤
         * 1.关闭提供者
         * 2.关闭消费者
         */

        String stopMethodName = "stop";
        Method providerStopMethod = providerInstance.getClass().getMethod(stopMethodName);
        Method consumerStopMethod = consumerInstance.getClass().getMethod(stopMethodName);
        providerStopMethod.invoke(providerInstance);
        consumerStopMethod.invoke(consumerInstance);
        log.info("结算项目停机全部完成！");
    }

    public void stop() {

        Socket socket = null;
        OutputStream stream = null;
        try {
            socket = new Socket(InetAddress.getByName(address), port);
            stream = socket.getOutputStream();
            for (int i = 0; i < shutdown.length(); i++) {
                stream.write(shutdown.charAt(i));
            }
            stream.flush();
        } catch (Exception ce) {
            log.error("创建关机Socket信号失败，程序可能未启动", ce);
            System.exit(1);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }

    // Copied from ExceptionUtils since that class is not visible during start
    private static void handleThrowable(Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath) t;
        }
        if (t instanceof VirtualMachineError) {
            throw (VirtualMachineError) t;
        }
        // All other instances of Throwable will be silently swallowed
    }
}
