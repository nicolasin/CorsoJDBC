package es.nico.prueba.modelo;

public class Asignatura {
	private Long id = null;
	private String nombre;
	private Long iProfesor;
	public Asignatura(String nombre, Long iProfesor) {
		super();
		this.nombre = nombre;
		this.iProfesor = iProfesor;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "ModeloAsignatura [id=" + id + ", nombre=" + nombre + ", iProfesor=" + iProfesor + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iProfesor == null) ? 0 : iProfesor.hashCode());
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
		Asignatura other = (Asignatura) obj;
		if (iProfesor == null) {
			if (other.iProfesor != null)
				return false;
		} else if (!iProfesor.equals(other.iProfesor))
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
	public Long getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getiProfesor() {
		return iProfesor;
	}
	public void setiProfesor(Long iProfesor) {
		this.iProfesor = iProfesor;
	}

}
