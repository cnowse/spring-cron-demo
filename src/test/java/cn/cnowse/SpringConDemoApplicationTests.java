package cn.cnowse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.cnowse.captcha.CaptchaDTO;
import cn.cnowse.captcha.CaptchaMathTextCreator;
import cn.cnowse.captcha.UseCaptcha;
import cn.cnowse.redis.RTHelper;

/**
 * 测试方法，每个测试方法都是独立的。加了事务，每个测试方法之间是不可见的。<br/>
 * 在一个测试方法中保存了数据，在另一个测试方法中是无法访问到这条数据的。
 *
 * @author Jeong Geol
 */
@Transactional // 测试类加上事务，在操作数据库时，不会在数据库中留下测试数据，但是如果表包含自增 id，这个自增 id 仍然会被影响到
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 可以对测试方法进行排序，按顺序执行，配合 @Order() 使用
class SpringConDemoApplicationTests {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UseCaptcha useCaptcha;

    @Autowired
    private RTHelper rtHelper;

    @Test
    @Order(1) // 搭配 @TestMethodOrder(MethodOrderer.OrderAnnotation.class) 可实现测试方法顺序执行
    void name2() {
        TestModel testModel = new TestModel();
        testModel.setStr("aaabbb");
        rtHelper.set("v1:v2:123", testModel);
    }

    @Test
    void contextLoads() throws JsonProcessingException {
        String str = "{\"str\": \"<h1>replace</h1>hello world!<a>https://www.baidu.com</a>\"}";
        TestModel testModel = om.readValue(str, TestModel.class);
        System.out.println("testModel = " + testModel);
    }

    @Test
    void test() {
        CaptchaMathTextCreator captchaMathTextCreator = new CaptchaMathTextCreator();
        for (int i = 0; i < 50; i++) {
            validateExpressionAndResult(captchaMathTextCreator.getText());
        }
    }

    @Test
    void name() {
        CaptchaDTO math = useCaptcha.example("char");
        System.out.println("math = " + math);
    }

    private void validateExpressionAndResult(String expression) {
        String[] expressionAndResult = expression.split("@");
        Assertions.assertEquals(expressionAndResult.length, 2);
        System.out.println(expressionAndResult[0] + "  answer is " + expressionAndResult[1]);
        // String safeExpression = StrUtil.removeSuffix(expressionAndResult[0], "=?");
        // Assertions.assertEquals(Convert.toInt(Calculator.conversion(safeExpression))
        // + "", expressionAndResult[1]);
    }

}
