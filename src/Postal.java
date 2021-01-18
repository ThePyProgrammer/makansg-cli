public class Postal {
	private String sectorCode;
	private String districtArea;
	public Postal(){}
	public Postal(String sectorCode, String districtArea){
		this.sectorCode = sectorCode;
		this.districtArea = districtArea;
	}
	public void setSectorCode(String sectorCode){
		this.sectorCode = sectorCode;
	}
	public void setDistrictArea(String districtArea){
		this.districtArea = districtArea;
	}
	public String getSectorCode(){
		return this.sectorCode;
	}
    public String getDistrictArea(){
    	return this.districtArea;
    }
    public String toString(){
    	return this.sectorCode + " " + this.districtArea;
    }
}
