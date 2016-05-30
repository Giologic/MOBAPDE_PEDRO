package com.giologic.pedrosystem10.service;

import com.giologic.pedrosystem10.model.Post;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by MarcDominic on 3/14/2016.
 */
public class PostService {
    private String dbUrl;
    private ConnectionSource cSource;
    private Dao<Post, String> dao;

    public PostService(String dbUrl, String user, String pass) throws SQLException {
        this.dbUrl = dbUrl;
        cSource = new JdbcConnectionSource(dbUrl, user, pass);
        dao = DaoManager.createDao(cSource, Post.class);
    }

    public void addPost(Post p) throws SQLException {
        dao.create(p);
    }

    public Post findBy(int id) throws SQLException {
        return dao.queryForId(id+"");
    }

    public Collection<Post> findAll() throws SQLException {
        QueryBuilder<Post, String> queryBuilder = dao.queryBuilder();
        queryBuilder.orderBy("org_id", true);
        return dao.query(queryBuilder.prepare());
    }
}
