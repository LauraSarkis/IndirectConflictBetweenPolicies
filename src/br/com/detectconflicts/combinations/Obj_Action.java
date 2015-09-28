package br.com.detectconflicts.combinations;

public class Obj_Action {

	private Object object;
	private Action action;
	
	public Obj_Action(Object object, Action action) {
		this.object = object;
		this.action = action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Object getObject() {
		return object;
	}
}
