package net;

import domain.RpcRequest;
import domain.RpcResponse;

/**
 * @creed: Here be dragons !
 * @author: Ezio
 * @Time: 2019-10-24 19:25
 * @desc:
 */
public interface RpcClient {

    boolean connect(String sip, int port) throws Throwable;        //连接服务器

    boolean sendRequest(RpcRequest request) throws Throwable;        //发送请求

    RpcResponse getResponse() throws Throwable;        //获取回应

    boolean close() throws Throwable;        //关闭连接

    boolean isConnected();





}
