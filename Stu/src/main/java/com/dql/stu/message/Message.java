package com.dql.stu.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Dql
 * @date 2021/6/20   19:57
 * description:公共返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * 状态码
     */
    private long code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回对象
     */
    private Object object;

    /**
     * 成功返回的方法
     * @param object 附带的对象
     * @return 公共返回对象
     */
    public static Message success(Object object){
        return new Message(MessageEnum.SUCCESS.getSTATUS(),
                MessageEnum.SUCCESS.getMESSAGE(),
                object);
    }

    /**
     * 成功返回的方法
     * 无参
     * @return 公共返回对象
     */
    public static Message success(){
        return new Message(MessageEnum.SUCCESS.getSTATUS(),
                MessageEnum.SUCCESS.getMESSAGE(),null);
    }

    /**
     * 失败返回的方法
     * @param object 附带的对象
     * @return 公共返回对象
     */
    public static Message error(MessageEnum messageEnum, Object object){
        return new Message(messageEnum.getSTATUS(),
                messageEnum.getMESSAGE(),
                object);
    }

    /**
     * 失败返回的方法
     * 无参
     * @return 公共返回对象
     */
    public static Message error(MessageEnum messageEnum){
        return new Message(messageEnum.getSTATUS(),
                messageEnum.getMESSAGE(),
                null);
    }


}
