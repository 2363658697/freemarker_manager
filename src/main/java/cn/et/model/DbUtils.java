package cn.et.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



/**
 * jdbc封装类
 * @author Administrator
 *
 */
public class DbUtils {
	static Properties p=new Properties();
	static{
		InputStream is=DbUtils.class.getResourceAsStream("/jdbc2.properties");  // '/' 代表同一个src下 ，不带‘/’代表同一个包下
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取连接
	 * @return
	 * @throws Exception 
	 */
	public static Connection getConnection() throws Exception{
		String url=p.getProperty("url");
		String driverClass=p.getProperty("driverClass");
		String uname=p.getProperty("username");
		String password=p.getProperty("password");
		Class.forName(driverClass);
		//登录成功
		Connection conn=DriverManager.getConnection(url, uname, password);
		return conn;
	}
	/**
	 * 查询sql返回list集合
	 */
	public static  List<Map<String, String>> query(String sql) throws  Exception{
		Connection connection=getConnection();
		PreparedStatement ps=connection.prepareStatement(sql);
		ResultSet rSet=ps.executeQuery();
		ResultSetMetaData data=rSet.getMetaData();
		List<Map<String, String>> list=new ArrayList<>();
		while(rSet.next()){
			Map<String,String> map=new HashMap<>();
			for(int i=1;i<=data.getColumnCount();i++){
				String coString=data.getColumnName(i);
				String coValue=rSet.getString(i);
				map.put(coString, coValue);
			}
			list.add(map);
		}
		ps.close();
		rSet.close();
		connection.close();
		return list;
	}
	public static int excute(String sql) throws Exception{
	       Connection connection=getConnection();
	        PreparedStatement ps=connection.prepareStatement(sql);
	        int count=ps.executeUpdate();
	        ps.close();
	        connection.close();
	        return count;
	}
	
}
