package com.giologic.pedrosystem10.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Question")
public class Question {
	@DatabaseField(id=true)
	private int id;
	@DatabaseField
	private String contents;
	@DatabaseField
	private Date dateAsked;
	@DatabaseField
	private String answer;
	@DatabaseField
	private Date dateAnswered;
	
	protected Question(){}
	
	public Question(String contents, Date dateAsked) {
		super();
		this.contents = contents;
		this.dateAsked = dateAsked;
	}

	public int getId() {
		return id;
	}

	public String getContents() {
		return contents;
	}

	public Date getDateAsked() {
		return dateAsked;
	}

	public String getAnswer() {
		return answer;
	}

	public Date getDateAnswered() {
		return dateAnswered;
	}
	
	public void answerQuestion(String answer, Date dateAsnwered){
		this.answer = answer;
		this.dateAnswered = dateAsnwered;
	}

	@Override
	public String toString() {
		return "Question [id=" + id + ", contents=" + contents + ", dateAsked="
				+ dateAsked + ", answer=" + answer + ", dateAnswered="
				+ dateAnswered + "]";
	}
	
	
}
