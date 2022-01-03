package com.dql.stu;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dql.stu.mapper.ChatRecordBOMapper;
import com.dql.stu.mapper.GroupBOMapper;
import com.dql.stu.model.ChatRecordBO;
import com.dql.stu.model.GroupBO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StuApplicationTests {

    @Autowired
    private GroupBOMapper groupBOMapper;

    @Autowired
    private ChatRecordBOMapper recordBOMapper;

    /**
     * 测试ID查询
     */
    @Test
    void contextLoads() {
        List<GroupBO> list = groupBOMapper.selectList(null);
        List<ChatRecordBO> chatRecordBOS = recordBOMapper.selectList(null);
        list.forEach(System.out::println);
        chatRecordBOS.forEach(System.out::println);
    }


}
