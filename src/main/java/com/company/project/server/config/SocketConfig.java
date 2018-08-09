package com.company.project.server.config;

/**
 * TCP配置
 *
 * @author wangzhj
 */
public class SocketConfig {

    /* 是否启用Nagle算法 */
    private boolean tcpNoDelay = true;
    /* TCP数据发送缓冲区大小，即TCP发送滑动窗口 */
    private int tcpSendBufferSize = -1;
    /*  */
    private int tcpReceiveBufferSize = -1;
    /* 是否使用TCP的心跳机制 */
    private boolean tcpKeepAlive = false;
    /* 延迟关闭的时间 */
    private int soLinger = -1;
    /*  */
    private boolean reuseAddress = false;
    /*  */
    private int acceptBackLog = 1024;

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public int getTcpSendBufferSize() {
        return tcpSendBufferSize;
    }

    public void setTcpSendBufferSize(int tcpSendBufferSize) {
        this.tcpSendBufferSize = tcpSendBufferSize;
    }

    public int getTcpReceiveBufferSize() {
        return tcpReceiveBufferSize;
    }

    public void setTcpReceiveBufferSize(int tcpReceiveBufferSize) {
        this.tcpReceiveBufferSize = tcpReceiveBufferSize;
    }

    public boolean isTcpKeepAlive() {
        return tcpKeepAlive;
    }

    public void setTcpKeepAlive(boolean tcpKeepAlive) {
        this.tcpKeepAlive = tcpKeepAlive;
    }

    public int getSoLinger() {
        return soLinger;
    }

    public void setSoLinger(int soLinger) {
        this.soLinger = soLinger;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public int getAcceptBackLog() {
        return acceptBackLog;
    }

    public void setAcceptBackLog(int acceptBackLog) {
        this.acceptBackLog = acceptBackLog;
    }
}
