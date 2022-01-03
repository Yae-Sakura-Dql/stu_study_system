package com.dql.stu.exception;

import com.dql.stu.message.MessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Dql
 * @date 2021/6/21   9:17
 * description:全局异常类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    public BusinessException(MessageEnum messageBeanEnum) {
        super(messageBeanEnum.getMESSAGE());
        this.messageBeanEnum = messageBeanEnum;
    }

    /**
     * 异常信息
     */
    private MessageEnum messageBeanEnum;
}
