package com.giologic.pedrosystem10.model;

import android.graphics.drawable.Drawable;

import com.giologic.pedrosystem10.util.ImageUtils;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.io.IOException;
import java.util.Date;


@DatabaseTable(tableName="AcademicAnnouncement")
public class AcademicAnnouncement {
    @DatabaseField(id=true)
    private int id;
    @DatabaseField
    private String title;
    @DatabaseField
    private String contents;
    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] img;
    @DatabaseField
    private Date date;

    private Drawable drawable;

    protected AcademicAnnouncement(){}

    public AcademicAnnouncement(String title, String contents, Date date) {
        super();
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

	public byte[] getImg() {
		return img;
	}

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setImg(File img) throws IOException {
        this.img = ImageUtils.readImageOldWay(img);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public String toString() {
        return "AcademicAnnouncement [id=" + id + ", title=" + title
                + ", contents=" + contents + ", img=" + img + ", date=" + date
                + "]";
    }
}
