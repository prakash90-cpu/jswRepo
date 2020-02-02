package jsw.report.viewBean;

import java.util.Date;

public class FileList {
	int id,userId,errorId,numberOfLines,status;
	Date createdTimeStamp,updatedTimeStamp,processed,offset,isActive;
	String fileName,errorDesc;
	String documentType;
	
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String toString() {
		return "FileList [id=" + id + ", userId=" + userId + ", errorId=" + errorId + ", numberOfLines=" + numberOfLines
				+ ", createdTimeStamp=" + createdTimeStamp + ", updatedTimeStamp=" + updatedTimeStamp + ", processed="
				+ processed + ", offset=" + offset + ", isActive=" + isActive + ", fileName=" + fileName + ", status="
				+ status + ", errorDesc=" + errorDesc + "]";
	}
	public FileList(int id, int userId, int errorId, int numberOfLines, Date createdTimeStamp, Date updatedTimeStamp,
			Date processed, Date offset, Date isActive, String fileName, int status, String errorDesc) {
		super();
		this.id = id;
		this.userId = userId;
		this.errorId = errorId;
		this.numberOfLines = numberOfLines;
		this.createdTimeStamp = createdTimeStamp;
		this.updatedTimeStamp = updatedTimeStamp;
		this.processed = processed;
		this.offset = offset;
		this.isActive = isActive;
		this.fileName = fileName;
		this.status = status;
		this.errorDesc = errorDesc;
	}
	public FileList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getErrorId() {
		return errorId;
	}
	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}
	public int getNumberOfLines() {
		return numberOfLines;
	}
	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}
	public Date getCreatedTimeStamp() {
		return createdTimeStamp;
	}
	public void setCreatedTimeStamp(Date createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	public Date getUpdatedTimeStamp() {
		return updatedTimeStamp;
	}
	public void setUpdatedTimeStamp(Date updatedTimeStamp) {
		this.updatedTimeStamp = updatedTimeStamp;
	}
	public Date getProcessed() {
		return processed;
	}
	public void setProcessed(Date processed) {
		this.processed = processed;
	}
	public Date getOffset() {
		return offset;
	}
	public void setOffset(Date offset) {
		this.offset = offset;
	}
	public Date getIsActive() {
		return isActive;
	}
	public void setIsActive(Date isActive) {
		this.isActive = isActive;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
