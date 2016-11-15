package cn.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.zhujie.Column;
import cn.zhujie.Filter;
import cn.zhujie.Table;

public class Test {
	public static void main(String[] args) {
		Filter f1 = new Filter();
		f1.setId(10);//查询ID为10的用户
		
		Filter f2 = new Filter();
		f2.setUserName("lucy");//模糊查询用户名为lucy的用户名
		f2.setAge(18);
		Filter f3 = new Filter();
		f3.setEmail("wei@sian.com,qinjiwei@163.com");//查询邮箱为任意一个的
		
		String sql1 = query(f1);
		String sql2 = query(f2);
		String sql3 = query(f3);
		
		System.out.println(sql1);
		System.out.println(sql2);
		System.out.println(sql3);
	}
	private static String query(Filter f){
		StringBuilder sb = new StringBuilder();
		//1.获取到class
		Class c = f.getClass();
		//2.获取到table的名字 ,判断Table.class是否存在
		boolean exist = c.isAnnotationPresent(Table.class);
		if(!exist){
			return null;
		}
		//如果Table.class存在,则通过getAnnotation拿到Table实例
		Table table =(Table) c.getAnnotation(Table.class);
		//拿到Table实例
		Object tableName = table.value();
		sb.append("select * from ").append(tableName).append(" where 1=1 ");
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
			Object fieldValue = null;
			try {
				Method getMethod = c.getMethod(getMethodName);
			    fieldValue = getMethod.invoke(f);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//4.3拼装sql
			if(fieldValue==null || 
					(fieldValue instanceof Integer && (Integer) fieldValue == 0)){
				continue;
			}
			sb.append(" and ").append(filedName);
			if(fieldValue instanceof String){
				if(((String)fieldValue).contains(",")){
					String[] values = ((String)fieldValue).split(",");
					sb.append(" in(");
					for(String v:values){
						sb.append("'").append(v).append("'").append(",");
					}
					sb.deleteCharAt(sb.length()-1);
					sb.append(")");
				}else{
					
					sb.append("=").append("'").append(fieldValue).append("'");
				}
			}else if(fieldValue instanceof Integer){
				sb.append("=").append(fieldValue);
			}
		//	sb.append("and").append(filedName).append("=");
			
		}
		return sb.toString();
	}
}
