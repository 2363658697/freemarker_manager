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
	    //��ȡ������
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		//��ȡ���ɵ�HTML�ļ���
		String htmlName=UUID.randomUUID().toString();
		//���ɵ�HTML�ļ��Ĵ��·��
		String savePath=HTML_DIR+"/"+htmlName+".html";
		//���ݿ���HTML�ļ���
		String htmlPath="/"+htmlName+".html";
		
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
        Date date=new Date();
        String time=dateFormat.format(date);
		
		myNews.insertNews(title, content, htmlPath,time);
		
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
       //����ftl����Ŀ¼
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
        //�������ݵ�ץȡģʽ
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_0));
        
        
      
        //��������     
        Map root = new HashMap();
        root.put("title", title); 
        root.put("content", content);
        root.put("time", time);
       
        // ʵ����ģ�����
        Template temp = cfg.getTemplate("news.ftl");

        //�����ļ����������ģ�����������html�ļ���
        FileOutputStream fileOutputStream=new FileOutputStream(savePath);
        Writer out = new OutputStreamWriter(fileOutputStream);

        try {
            temp.process(root, out);
            out.flush();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        
        response.getWriter().write("��ӳɹ�");
	}

}
