package cn.zhujie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) //���������˼��  ���ڽӿڻ���
@Retention(RetentionPolicy.RUNTIME)  //����ʱ����
public @interface Table {
	String value();
}
