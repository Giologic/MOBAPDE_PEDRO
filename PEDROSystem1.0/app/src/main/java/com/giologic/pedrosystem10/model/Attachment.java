package com.giologic.pedrosystem10.model;//package com.mdsp.marcdominic.ssh;
//
//import java.sql.Blob;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//@Entity
//public class Attachment {
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int id;
//	private String filename;
//	private String contentType;
//	private Blob fileData;
//
//	protected Attachment(){}
//
//	public Attachment(String filename, String contentType, Blob fileData) {
//		super();
//		this.filename = filename;
//		this.contentType = contentType;
//		this.fileData = fileData;
//	}
//
//	public int getId(){
//		return id;
//	}
//
//	public String getFilename() {
//		String name = new String(filename);
//		if(name.length()>15){
//			name = name.substring(0, 5)+"..."+name.substring(name.length()-6, name.length());
//		}
//		return name;
//	}
//	public void setFilename(String filename) {
//		this.filename = filename;
//	}
//	public Blob getFileData() {
//		return fileData;
//	}
//	public void setFileData(Blob fileData) {
//		this.fileData = fileData;
//	}
//
//	public String getContentType() {
//		return contentType;
//	}
//
//	public void setContentType(String contentType) {
//		this.contentType = contentType;
//	}
//
//}
