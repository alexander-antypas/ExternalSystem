package gr.hua.dit.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")

public class authorities {

	@Id
	@Column(name="username")
	private String id;
	
	@Column(name="authority")
	private String authority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public authorities(String id, String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "authorities [id=" + id + ", authority=" + authority + "]";
	}
	
	public authorities() {

	}
}
