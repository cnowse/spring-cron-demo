package cn.cnowse;

import cn.cnowse.captcha.CaptchaDTO;
import cn.cnowse.captcha.CaptchaMathTextCreator;
import cn.cnowse.captcha.UseCaptcha;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringConDemoApplicationTests {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UseCaptcha useCaptcha;

    @Test
    void contextLoads() throws JsonProcessingException {
        String str = "{\"str\": \"<h1>replace</h1>hello world!<a>https://www.baidu.com</a>\"}";
        TestModel testModel = om.readValue(str, TestModel.class);
        System.out.println("testModel = " + testModel);
    }

    @Test
    public void test() {
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
        // Assertions.assertEquals(Convert.toInt(Calculator.conversion(safeExpression)) + "", expressionAndResult[1]);
    }

}
