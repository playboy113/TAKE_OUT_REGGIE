package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.magnet_model;

import java.util.List;

public interface MagnetService {


    List<magnet_model> selectAll();

    PageBean<magnet_model> selectByPage(int currentPage, int pageSize);
}
