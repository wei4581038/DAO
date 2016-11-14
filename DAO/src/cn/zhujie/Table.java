package cn.zhujie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) //作用域的意思吧  用于接口或类
@Retention(RetentionPolicy.RUNTIME)  //运行时解析
public @interface Table {
	String value();
}
