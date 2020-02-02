package jsw.report.viewBean;

public class BusinessGroupLoc {
int id;
int businessId;
String locId;


String businessGroup;
String location;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getBusinessGroup() {
	return businessGroup;
}
public void setBusinessGroup(String businessGroup) {
	this.businessGroup = businessGroup;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}


public int getBusinessId() {
	return businessId;
}
public void setBusinessId(int businessId) {
	this.businessId = businessId;
}
public String getLocId() {
	return locId;
}
public void setLocId(String locId) {
	this.locId = locId;
}
@Override
public String toString() {
	return "BusinessGroupLoc [id=" + id + ", businessId=" + businessId + ", locId=" + locId + ", businessGroup="
			+ businessGroup + ", location=" + location + "]";
}














}
