package com.dql.stu.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * @author 段启龙
 * @date 2021/12/9   0:34
 * description:mybatis-plus 逆向生成器
 */
public class MybatisPlusGenerator {
    final static String dirPath = System.getProperty("user.dir");

    /*final static String  dirPath = "classpath:/com/example/mybatisP/";*/

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    /**
     * <p>
     * MySQL 生成演示
     * </p>
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(dirPath + "/Stu/src/main/java/");
        System.out.println("dir is : " + gc.getOutputDir());
        gc.setAuthor("段启龙");
        gc.setFileOverride(true); //是否覆盖
        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
        dsc.setUsername("stu_system");
        dsc.setPassword("123456");
//        dsc.setUrl("jdbc:mysql://localhost:3306/stu_system?serverTimezone=UTC");
        dsc.setUrl("jdbc:mysql://175.24.229.51:3306/stu_system?serverTimezone=UTC");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setEntityLombokModel(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//列名规则
        strategy.setEntityLombokModel(true);//是否生成lombok注解
        //自动填充的配置
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);//设置时的生成策略
        TableFill update_time = new TableFill("update_time", FieldFill.INSERT_UPDATE);//设置更新时间的生成策略
        ArrayList<TableFill> list = new ArrayList<>();
        list.add(create_time);
        list.add(update_time);
        strategy.setTableFillList(list);
        strategy.setRestControllerStyle(true);//开启驼峰命名
        strategy.setInclude(scanner("table name，splite by ',' :").split(","));
        strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com");
        pc.setModuleName("dql.stu");
        pc.setController("controller");
        pc.setEntity("model");
//        pc.setMapper("mapper");
        pc.setService("service.func");
        pc.setServiceImpl("service.impl");

        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.zwh 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("段启龙", this.getConfig().getGlobalConfig().getAuthor() + "的模板生成完成！");
                this.setMap(map);
            }
        };

        // 自定义 xxList.jsp 生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();

        //自定义配置会优先输出
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return dirPath + "/Stu/src/main/resources/mapper/"
                        //+ tableInfo.getEntityName() + "Mapper.xml";
                        + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 执行生成
        mpg.execute();

        // 打印注入设置【可无】
        System.err.println(mpg.getCfg().getMap().get("段启龙"));
    }
}
