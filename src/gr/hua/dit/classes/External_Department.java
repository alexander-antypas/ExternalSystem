package gr.hua.dit.classes;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "External_Department")
public class External_Department {
	
	@Id
	@Column(name="name")
	private String name;
	
	@Column(name="is_Selected")
	private String is_Selected;
	
	
	
	public External_Department() {
		
	}

	public External_Department(String name, String is_Selected) {
		super();
		this.name = name;
		this.is_Selected = is_Selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIs_Selected() {
		return is_Selected;
	}

	public void setIs_Selected(String is_Selected) {
		this.is_Selected = is_Selected;
	}

	@Override
	public String toString() {
		return "External_Department [name=" + name + ", is_Selected=" + is_Selected + "]";
	}
	
	
	
	
}
