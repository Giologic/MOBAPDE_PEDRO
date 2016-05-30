package com.giologic.pedrosystem10.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "CourseOffering")
public class CourseOffering {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField
	private String term;
	@DatabaseField
	private String filepath;
	
	public CourseOffering(String term, String filepath) {
		super();
		this.term = term;
		this.filepath = filepath;
	}
	
	protected CourseOffering(){}

	public int getId() {
		return id;
	}

	public String getTerm() {
		return term;
	}

	public String getFilepath() {
		return filepath.replaceAll(" ", "%20");
	}
	
	
}
