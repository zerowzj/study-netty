package com.company.project.server.config;

/**
 * 配置
 *
 * @author wangzhj
 */
public class Configuration {

    /* 线程数量 */
    private int bossThreads = 0;
    /* 线程数量 */
    private int workerThreads = 0;
    /* 主机 */
    private String hostName;
    /* 端口 */
    private int port = -1;
    /* Socket配置 */
    private SocketConfig socketConfig = new SocketConfig();

    public Configuration() {
    }

    public int getBossThreads() {
        return bossThreads;
    }

    public void setBossThreads(int bossThreads) {
        this.bossThreads = bossThreads;
    }

    public int getWorkerThreads() {
        return workerThreads;
    }

    public void setWorkerThreads(int workerThreads) {
        this.workerThreads = workerThreads;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SocketConfig getSocketConfig() {
        return socketConfig;
    }

    public void setSocketConfig(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
