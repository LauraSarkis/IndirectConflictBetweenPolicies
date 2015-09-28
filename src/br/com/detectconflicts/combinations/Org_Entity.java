package br.com.detectconflicts.combinations;

public class Org_Entity {

	private Organization org;
	private Entity entity;
	
	Org_Entity(Organization org, Entity entity) {
		
		this.org = org;
		this.entity = entity;
		
	}
	
	public void setOrg(Organization org) {
		this.org = org;
	}
	
	public Organization getOrg() {
		return org;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
}
