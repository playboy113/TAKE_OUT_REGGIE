package com.example.web;

import com.alibaba.fastjson.JSON;
import com.example.pojo.magnet_model;
import com.example.service.MagnetService;
import com.example.service.MagnetServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/selectAllServlet")
public class SelectAllServlet extends HttpServlet {
    private MagnetService magnetService = new MagnetServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 调用service查询
        List<magnet_model> magnets = magnetService.selectAll();
        //2. 转为JSON
        String jsonString = JSON.toJSONString(magnets);

        //3. 写数据
        response.setContentType("text/json;charset=UTF-8"); //告知浏览器响应的数据是什么， 告知浏览器使用什么字符集进行解码
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
