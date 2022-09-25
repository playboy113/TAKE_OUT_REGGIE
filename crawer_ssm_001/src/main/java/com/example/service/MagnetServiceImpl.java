package com.example.service;

import com.example.mapper.MagnetMapper;
import com.example.pojo.PageBean;
import com.example.pojo.magnet_model;
import com.example.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class MagnetServiceImpl implements MagnetService{
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public List<magnet_model> selectAll() {
        SqlSession sqlSession = factory.openSession();
        MagnetMapper mapper = sqlSession.getMapper(MagnetMapper.class);

        List<magnet_model> magnets = mapper.selectAll();
        sqlSession.close();
        return magnets;

    }

    @Override
    public PageBean<magnet_model> selectByPage(int currentPage, int pageSize) {
        //获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //获取magnetMapper
        MagnetMapper mapper = sqlSession.getMapper(MagnetMapper.class);
        //开始计算索引
        int begin = (currentPage-1)*pageSize;

        //计算查询条目数
        int size = pageSize;

        //查询当前页数据
        List<magnet_model> rows = mapper.selectByPage(begin,size);

        //查询总记录数
        int totalCount = mapper.selectTotalCount();

        //封装pagebean对象
        PageBean<magnet_model> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        //释放资源
        sqlSession.close();
        return pageBean;

    }
}
