package cn.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.zhujie.Column;
import cn.zhujie.Filter;
import cn.zhujie.Table;

public class Test {
	public static void main(String[] args) {
		
	}
	private static String query(Filter f){
		StringBuilder sb = new StringBuilder();
		//1.获取到class
		Class c = f.getClass();
		//2.获取到table的名字
		boolean exist = c.isAnnotationPresent(Table.class);
		if(!exist){
			return null;
		}
		Table table =(Table) c.getAnnotation(Table.class);
		String tableName = table.value();
		sb.append("select * from").append(tableName).append("where 1=1");
		//3.遍历所有的字段
		Field[] fields = c.getDeclaredFields();
		for(Field field:fields){
			//4.处理每个字段对应的sql
			//4.1 拿到字段名
			boolean fExists = field.isAnnotationPresent(Column.class);
			if(!fExists){
				continue;
			}
			Column column = field.getAnnotation(Column.class);
			String columnName = column.value();
			//4.2拿到字段值
			String filedName = field.getName();
			String getMethodName = "get"+filedName.substring(0,1).toUpperCase()+filedName.substring(1);
			String fieldValue = null;
			try {
				Method getMethod = c.getMethod(getMethodName);
			    fieldValue = (String) getMethod.invoke(f, null);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//4.3拼装sql
			sb.append("and").append(filedName).append("=").append(fieldValue);
			
		}
		return sb.toString();
	}
}
