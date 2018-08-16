package com.shengc.mtranslater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import static com.shengc.mtranslater.utils.ConsoleUtil.print;
import static com.shengc.mtranslater.utils.FileUtil.readFile;


/**
 * @author sc
 * @create 2018/8/14
 * @desc 启动类
 **/

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class MtranslaterApplication {

    //C:\Users\Administrator\Desktop\test.docx
    //C:\Users\Administrator\Desktop\test.md
    public static void main(String[] args){
        SpringApplication.run(MtranslaterApplication.class, args);

        doAction();
    }

    public static void doAction(){
        try {
            readFile();
            print("转换成功！");
        } catch (Exception e) {
            e.printStackTrace();
            print("转换失败！");
        }
    }
}
