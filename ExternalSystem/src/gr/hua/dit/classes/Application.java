package gr.hua.dit.classes;

import java.util.Arrays;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "Application")

public class Application {
	
	@Column(name="date_of_submission")
	private Date date_of_submission;
	
	@Id
	@Column(name="application_id")
	private String application_id;
	
	
	@Column(name="isChecked")
	private int isChecked;
	
	@Lob	
	@Column(name="family")
	private byte[] family;
	
	@Lob
	@Column(name="financially")
	private byte[] financially;
	
	@Lob
	@Column(name="locality")
	private byte[] locality;
	
	public Application() {}

	public Application(Date date_of_submission, String application_id, int isChecked, byte[] family, byte[] financially,
			byte[] locality) {
		super();
		this.date_of_submission = date_of_submission;
		this.application_id = application_id;
		this.isChecked = isChecked;
		this.family = family;
		this.financially = financially;
		this.locality = locality;
	}

	public Date getDate_of_submission() {
		return date_of_submission;
	}

	public void setDate_of_submission(Date date_of_submission) {
		this.date_of_submission = date_of_submission;
	}

	public String getApplication_id() {
		return application_id;
	}

	public void setApplication_id(String application_id) {
		this.application_id = application_id;
	}

	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}

	public byte[] getFamily() {
		return family;
	}

	public void setFamily(byte[] family) {
		this.family = family;
	}

	public byte[] getFinancially() {
		return financially;
	}

	public void setFinancially(byte[] financially) {
		this.financially = financially;
	}

	public byte[] getLocality() {
		return locality;
	}

	public void setLocality(byte[] locality) {
		this.locality = locality;
	}

	@Override
	public String toString() {
		return "Application [date_of_submission=" + date_of_submission + ", application_id=" + application_id
				+ ", isChecked=" + isChecked + ", family=" + Arrays.toString(family) + ", financially="
				+ Arrays.toString(financially) + ", locality=" + Arrays.toString(locality) + "]";
	}

	
	
}
