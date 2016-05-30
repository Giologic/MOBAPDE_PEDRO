package com.giologic.pedrosystem10.service;

import android.util.Log;

import com.giologic.pedrosystem10.model.Organization;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by MarcDominic on 3/14/2016.
 */
public class OrgService {
    private String dbUrl;
    private ConnectionSource cSource;
    private Dao<Organization, String> dao;

    public OrgService(String dbUrl, String user, String pass) throws SQLException {
        this.dbUrl = dbUrl;
        cSource = new JdbcConnectionSource(dbUrl, user, pass);
        dao = DaoManager.createDao(cSource, Organization.class);
    }

    public void addOrganization(Organization o) throws SQLException {
        dao.create(o);
    }

    public Organization findBy(int id) throws SQLException {
        return dao.queryForId(id+"");
    }

    public Collection<Organization> findAll() throws SQLException {
        return dao.queryForAll();
    }
}
