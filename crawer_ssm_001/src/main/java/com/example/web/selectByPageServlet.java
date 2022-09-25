package com.example.web;

import com.alibaba.fastjson.JSON;
import com.example.pojo.PageBean;
import com.example.pojo.magnet_model;
import com.example.service.MagnetService;
import com.example.service.MagnetServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/selectByPageServlet")
public class selectByPageServlet extends HttpServlet {
    private MagnetService magnetService = new MagnetServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");
        int currenPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);
        PageBean<magnet_model> pageBean = magnetService.selectByPage(currenPage,pageSize);
        String jsonString = JSON.toJSONString(pageBean);
        response.setContentType("text/json;charset = utf-8");
        response.getWriter().write(jsonString);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
