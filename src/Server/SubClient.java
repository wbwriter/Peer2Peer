package Server;

import java.util.List;

public class SubClient {

	private String IP;
	private String Name;
	private List list;
	
	
	public SubClient(String iP, String name, List list) {
		IP = iP;
		Name = name;
		this.list = list;
	}
	
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
}
