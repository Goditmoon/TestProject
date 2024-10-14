package org.example.interview.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.interview.anno.State;

public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){

        /**
         * 校验给定的值是否为预设的合法状态之一。
         *
         * @param value 要验证的字符串值
         * @param context 验证上下文
         * @return 如果值为null返回false；否则如果值等于"已发布"或"草稿"则返回true，否则返回false
         */
        //提供校验规则
        if (value == null){
            return false;
        }
        if (value.equals("已发布") || value.equals("草稿")){
            return true;
        }
        return false;
    }
}
