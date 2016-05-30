package com.giologic.pedrosystem10.service;

import com.giologic.pedrosystem10.model.Question;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by MarcDominic on 4/9/2016.
 */
public class QuestionService {
    private String dbUrl;
    private ConnectionSource cSource;
    private Dao<Question, String> dao;

    public QuestionService(String dbUrl, String user, String pass) throws SQLException {
        this.dbUrl = dbUrl;
        cSource = new JdbcConnectionSource(dbUrl, user, pass);
        dao = DaoManager.createDao(cSource, Question.class);
    }

    public Question findBy(int id) throws SQLException {
        return dao.queryForId(id+"");
    }

    public Collection<Question> findAllAnswered() throws SQLException {
        QueryBuilder<Question, String> queryBuilder = dao.queryBuilder();
        queryBuilder.where().not().eq("answer", null);
        return dao.query(queryBuilder.prepare());
    }

    public Collection<Question> findAll() throws SQLException {
        System.out.println(dao);
        return dao.queryForAll();
    }

    public int addQuestion (Question question) throws SQLException{
        return dao.create(question);
    }
}
