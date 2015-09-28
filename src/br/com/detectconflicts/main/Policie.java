package br.com.detectconflicts.main;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class Policie {

	private String name;
	private String kp;
	private String org;
	private String sr;
	private String aa;
	private String ov;
	private Date ac;
	private Date dc;
	private boolean derivated;
	private Set<String> origin;
	
	public Policie(String name, String kp, String org, String sr, String aa, String ov, String ac, String dc, Boolean derivated, Set<String> origin) {
		this.name = name;
		this.kp = kp;
		this.org = org;
		this.sr = sr;
		this.aa = aa;
		this.ov = ov;
		this.ac = convertDate(ac);
		this.dc = convertDate(dc);	
		this.derivated = derivated;
		this.origin = origin;
	}
	
	public Policie(String name, String kp, String org, String sr, String aa, String ov, Date ac, Date dc, Boolean derivated, Set<String> origin) {
		this.name = name;
		this.kp = kp;
		this.org = org;
		this.sr = sr;
		this.aa = aa;
		this.ov = ov;
		this.ac = ac;
		this.dc = dc;
		this.derivated = derivated;
		this.origin = origin;
		
	}
	
	public void setOrigin(Set<String> origin) {
		this.origin = origin;
	}
	
	public Set<String> getOrigin() {
		return origin;
	}
	
	public String getName() {
		return name;
	}
	
	public String getOriginalName() {
		String[] parts = name.split("\\.");
		return parts[0];
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getKp() {
		return kp;
	}
	
	public void setKp(String kp) {
		this.kp = kp;
	}
	
	public String getOrg() {
		if(org != null)
			return org;
		else 
			return "";
	}
	
	public void setOrg(String org) {
		this.org = org;
	}
	
	public String getSr() {
		if(sr != null)
			return sr;
		else 
			return "null";
	}
	
	public void setSr(String sr) {
		this.sr = sr;
	}
	
	public String getAa() {
		return aa;
	}
	
	public void setAa(String aa) {
		this.aa = aa;
	}
	
	public String getOv() {
		return ov;
	}
	
	public void setOv(String ov) {
		this.ov = ov;
	}
	
	public Date getAc() {
		return ac;
	}
	
	public void setAc(String ac) {
		this.ac = convertDate(ac);
	}
	
	public Date getDc() {
		return dc;
	}
	
	public void setDc(String dc) {
		this.dc = convertDate(dc);
	}
	
	public void setDerivated(boolean derivated) {
		this.derivated = derivated;
	}
	
	public boolean isDerivated() {
		return derivated;
	}

	public Date convertDate (String date) {
		
		String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		ParsePosition position = new ParsePosition(2);
		Date dataSaida = sdf.parse(date, position);
		return dataSaida;
		
	}

	@Override
	public String toString() {
		return name + "-> [" + kp + ", " + org
				+ ", " + sr + ", " + aa + ", " + ov + ", " + ac
				+ ", " + dc + "]";
	}
}

