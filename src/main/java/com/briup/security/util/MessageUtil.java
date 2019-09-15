package com.briup.security.util;

import java.util.Date;

/**
 * @program: security-demo
 * @description: 消息工具
 * @author: wangzh
 * @create: 2019-03-13 21:01
 **/

public class MessageUtil {
    /**
     * 成功并返回数据实体类
     * @param o
     * @param <E>
     * @return
     */
    public static <E>Message<E> success(E o){
        return new Message<>(200, "success", o, new Date().getTime());
    }

    /**
     * 成功，但无数据实体类返回
     * @return
     */
    public static <E>Message<E> success(){
        return new Message<>(200, "success", null, new Date().getTime());
    }

    /**
     * 失败，有自定义异常返回
     * @param code
     * @param msg
     * @return
     */
    public static <E>Message<E> error(Integer code,String msg){
        return new Message<>(code, msg, null, new Date().getTime());
    }
}
