package com.danielev86.java8exercise.bean;

public class CyclistBean extends PersonBean {
	

	private static final long serialVersionUID = 1L;
	private String teamName;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CyclistBean other = (CyclistBean) obj;
		if (teamName == null) {
			if (other.teamName != null)
				return false;
		} else if (!teamName.equals(other.teamName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CyclistBean [teamName=" + teamName + ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + "]";
	}

}
