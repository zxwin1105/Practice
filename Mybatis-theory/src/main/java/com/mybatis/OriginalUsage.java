package com.mybatis;

import com.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;


/**
 * mybatis 的原始用法
 *
 * @author zhaixinwei
 * @date 2022/11/17
 */
public class OriginalUsage {

    public static void main(String[] args) throws IOException {
        // 一级缓存
        localCache();
    }

    public static void localCache() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sessionFactory.openSession();
        // 一次提交
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println(mapper.getOneById("2a98f35f-d0ac-426d-a26a-55dcb0bb999e"));
        sqlSession.commit();

        SqlSession sqlSession1 = sessionFactory.openSession();
        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
        System.out.println(mapper1.getOneById("2a98f35f-d0ac-426d-a26a-55dcb0bb999e"));
        sqlSession1.commit();
    }
}
