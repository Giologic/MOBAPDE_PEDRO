package com.giologic.pedrosystem10.service;

import android.util.Log;

import com.giologic.pedrosystem10.model.AcademicAnnouncement;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by MarcDominic on 3/11/2016.
 */
public class AAService {
    private String dbUrl;
    private ConnectionSource cSource;
    private Dao<AcademicAnnouncement, String> dao;

    public AAService(String dbUrl, String user, String pass) throws SQLException {
        this.dbUrl = dbUrl;
        cSource = new JdbcConnectionSource(dbUrl, user, pass);
        dao = DaoManager.createDao(cSource, AcademicAnnouncement.class);
    }

    public void addAnnouncement(AcademicAnnouncement aa) throws SQLException {
        dao.create(aa);
    }

    public AcademicAnnouncement findBy(int id) throws SQLException {
        return dao.queryForId(id+"");
    }

    public Collection<AcademicAnnouncement> findAll() throws SQLException {
        return dao.queryForAll();
    }
}
