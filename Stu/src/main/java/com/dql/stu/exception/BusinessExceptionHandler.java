package com.dql.stu.exception;

import com.dql.stu.message.Message;
import com.dql.stu.message.MessageEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Dql
 * @date 2021/6/21   9:18
 * description:全局异常处理器
 */
@RestControllerAdvice
public class BusinessExceptionHandler {

    /**
     * 异常信息处理
     * @param e 异常
     * @return 公共返回对象
     */
    @ExceptionHandler(Exception.class)
    public Message exceptionHandler(Exception e){
        if(e instanceof BusinessException){
            BusinessException ex = (BusinessException) e;
            return Message.error(ex.getMessageBeanEnum(),null);
        }else if(e instanceof BindException){
            BindException ex = (BindException) e;
            Message messageBean = Message.error(MessageEnum.PROPERTIES_ERROR,null);
            messageBean.setMessage("错误 : "+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
            return messageBean;

        }
        return Message.error(MessageEnum.ERROR,null);
    }
}
