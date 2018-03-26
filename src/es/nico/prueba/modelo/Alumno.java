package es.nico.prueba.modelo;

import java.util.Date;

public class Alumno {
	private Long id = null;
	
	private String nombre;
	private String apelllidos;
	private Date FechaNacimiento;
	public Alumno(String nombre, String apelllidos, Date fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.apelllidos = apelllidos;
		FechaNacimiento = fechaNacimiento;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return this.id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApelllidos() {
		return apelllidos;
	}
	public void setApelllidos(String apelllidos) {
		this.apelllidos = apelllidos;
	}
	public Date getFechaNacimiento() {
		return FechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		FechaNacimiento = fechaNacimiento;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((FechaNacimiento == null) ? 0 : FechaNacimiento.hashCode());
		result = prime * result + ((apelllidos == null) ? 0 : apelllidos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		if (FechaNacimiento == null) {
			if (other.FechaNacimiento != null)
				return false;
		} else if (!FechaNacimiento.equals(other.FechaNacimiento))
			return false;
		if (apelllidos == null) {
			if (other.apelllidos != null)
				return false;
		} else if (!apelllidos.equals(other.apelllidos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ModeloAlumno [id=" + id + ", nombre=" + nombre + ", apelllidos=" + apelllidos + ", FechaNacimiento="
				+ FechaNacimiento + "]";
	}
	
}
