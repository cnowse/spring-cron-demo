package cn.cnowse.captcha;

import java.util.concurrent.ThreadLocalRandom;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

/**
 * 数学验证码生成器，此类的使用在 {@link CaptchaConfig#getKaptchaBeanMath()}
 *
 * @author Jeong Geol 2023-12-26
 */
public class CaptchaMathTextCreator extends DefaultTextCreator {

    @Override
    public String getText() {
        int x = ThreadLocalRandom.current().nextInt(13);
        int y = ThreadLocalRandom.current().nextInt(13);
        Operand[] enumConstants = Operand.class.getEnumConstants();
        Operand randomOperand = enumConstants[ThreadLocalRandom.current().nextInt(4)];
        StringBuilder mathExpression = new StringBuilder();
        int result = randomOperand.generateMathExpression(x, y, mathExpression);
        mathExpression.append("=?@").append(result);
        return mathExpression.toString();
    }

    enum Operand {

        /**
         * 加减乘除操作 用来生成验证码的图片表达式
         */
        ADD {

            @Override
            public int generateMathExpression(int x, int y, StringBuilder expression) {
                expression.append(x);
                expression.append("+");
                expression.append(y);
                return x + y;
            }

        },
        MINUS {

            @Override
            public int generateMathExpression(int x, int y, StringBuilder expression) {
                expression.append(Math.max(x, y));
                expression.append("-");
                expression.append(Math.min(x, y));
                return Math.abs(x - y);
            }

        },
        MULTIPLE {

            @Override
            public int generateMathExpression(int x, int y, StringBuilder expression) {
                expression.append(x);
                expression.append("*");
                expression.append(y);
                return x * y;
            }

        },
        DIVIDE {

            @Override
            public int generateMathExpression(int x, int y, StringBuilder expression) {
                // Judge whether an integer can be divided
                if (x != 0 && y % x == 0) {
                    expression.append(y);
                    expression.append("/");
                    expression.append(x);
                    return y / x;
                } else {
                    // use add addition instead
                    return Operand.ADD.generateMathExpression(x, y, expression);
                }
            }

        };

        public abstract int generateMathExpression(int x, int y, StringBuilder expression);

    }

}
