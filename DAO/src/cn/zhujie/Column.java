package cn.zhujie;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) //���������˼��  �����ֶ�
@Retention(RetentionPolicy.RUNTIME)  //����ʱ����

public @interface Column {
	String value();
}
