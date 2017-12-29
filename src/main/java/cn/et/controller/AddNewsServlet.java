package cn.et.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.et.model.MyNews;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class AddNewsServlet extends HttpServlet {
	
    static final String HTML_DIR="D:/html";
    MyNews  myNews = new MyNews();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    //获取表单数据
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		//获取生成的HTML文件名
		String htmlName=UUID.randomUUID().toString();
		//生成的HTML文件的存放路径
		String savePath=HTML_DIR+"/"+htmlName+".html";
		//数据库中HTML文件名
		String htmlPath="/"+htmlName+".html";
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
        Date date=new Date();
        String time=dateFormat.format(date);
		
		myNews.insertNews(title, content, htmlPath,time);
		
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
       //配置ftl查找目录
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
        //设置数据的抓取模式
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_0));
        
        
      
        //构建数据     
        Map root = new HashMap();
        root.put("title", title); 
        root.put("content", content);
        root.put("time", time);
       
        // 实例化模版对象
        Template temp = cfg.getTemplate("news.ftl");

        //定义文件输出流，把模版内容输出到html文件中
        FileOutputStream fileOutputStream=new FileOutputStream(savePath);
        Writer out = new OutputStreamWriter(fileOutputStream);

        try {
            temp.process(root, out);
            out.flush();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        
        response.getWriter().write("添加成功");
	}

}
