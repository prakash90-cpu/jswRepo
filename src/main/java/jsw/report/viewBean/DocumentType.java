package jsw.report.viewBean;

public class DocumentType {
	private int id=0;
	private String documentType;
	private String description;
    private String isActive;
	
	private String documentName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public DocumentType() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DocumentType [id=" + id + ", documentType=" + documentType + ", description=" + description
				+ ", isActive=" + isActive + ", documentName=" + documentName + "]";
	}
	public DocumentType(int id, String documentType, String description, String isActive, String documentName) {
		super();
		this.id = id;
		this.documentType = documentType;
		this.description = description;
		this.isActive = isActive;
		this.documentName = documentName;
	}
	
	
	
}
