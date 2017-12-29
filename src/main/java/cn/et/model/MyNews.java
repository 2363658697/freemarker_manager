package cn.et.model;

import java.util.List;
import java.util.Map;

public class MyNews {
        public void insertNews(String title,String content,String htmlPath,String time) {
            String sql="insert into news(title,content,htmlPath,time) values('"+title+"','"+content+"','"+htmlPath+"','"+time+"')";
            try {
                DbUtils.excute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public List<Map<String, String>> query(){
            String sql="select * from news";
            List<Map<String, String>> list = null;
            try {
                list = DbUtils.query(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
        
        
        
        
        
        public static void main(String[] args) {
            MyNews name = new MyNews();
            name.insertNews("aa", "bb", "cc","aa");
            
            
          
        }
}
