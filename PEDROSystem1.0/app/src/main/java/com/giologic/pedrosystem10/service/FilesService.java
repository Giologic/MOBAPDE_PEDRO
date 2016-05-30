package com.giologic.pedrosystem10.service;

import com.giologic.pedrosystem10.model.CourseOffering;
import com.giologic.pedrosystem10.model.Flowchart;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by MarcDominic on 4/9/2016.
 */
public class FilesService {
    private String dbUrl;
    private ConnectionSource cSource;
    private Dao<Flowchart, String> flowDao;
    private Dao<CourseOffering, String> courseOfferingDao;

    public FilesService(String dbUrl, String user, String pass) throws SQLException {
        this.dbUrl = dbUrl;
        cSource = new JdbcConnectionSource(dbUrl, user, pass);
        flowDao = DaoManager.createDao(cSource, Flowchart.class);
        courseOfferingDao = DaoManager.createDao(cSource, CourseOffering.class);
    }
    public String[] getIdNumbers() throws SQLException {

        GenericRawResults<String[]> rawResults =
                flowDao.queryRaw("select distinct idNumber from Flowchart");
        List<String[]> results = rawResults.getResults();
        String[] actualResults = new String[results.size()];
        for(int i = 0; i < actualResults.length; i++){
            actualResults[i] = results.get(i)[0];
        }

        return actualResults;
    }

    public String[] getSpecializations() throws SQLException {
        GenericRawResults<String[]> rawResults =
                flowDao.queryRaw("select distinct specialization from Flowchart");
        List<String[]> results = rawResults.getResults();
        String[] actualResults = new String[results.size()];
        for(int i = 0; i < actualResults.length; i++){
            actualResults[i] = results.get(i)[0];
        }
        return actualResults;
    }

    public Collection<Flowchart> findAllFlowcharts() throws SQLException {
        return flowDao.queryForAll();
    }
    public Collection<CourseOffering> findAllCourseOfferings() throws SQLException {
        return courseOfferingDao.queryForAll();
    }

    public CourseOffering getCourseOffering() throws SQLException {
        return courseOfferingDao.queryForAll().get(0);
    }

    public String getFlowchartBy(String idNo, String specialization) throws SQLException {
        GenericRawResults<String[]> rawResults =
                flowDao.queryRaw("select filepath from Flowchart where idNumber = '"+idNo+"' and specialization = '"+specialization+"'");
        List<String[]> results = rawResults.getResults();
        return results.get(0)[0];
    }
}
