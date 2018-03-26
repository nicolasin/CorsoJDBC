package es.nico.prueba.modelo;

public class Matricula {
		public class IdMatricula{
				private long alumno;
				private long asignatura;
				private int year;
				
				public IdMatricula(long alumnos, long asignatura, int year) {
					this.year = year;
					this.asignatura = asignatura;
					this.alumno = alumnos;
				}
				@Override
				public int hashCode() {
					final int prime = 31;
					int result = 1;
					result = prime * result + getOuterType().hashCode();
					result = prime * result + (int) (alumno ^ (alumno >>> 32));
					result = prime * result + (int) (asignatura ^ (asignatura >>> 32));
					result = prime * result + year;
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
					IdMatricula other = (IdMatricula) obj;
					if (!getOuterType().equals(other.getOuterType()))
						return false;
					if (alumno != other.alumno)
						return false;
					if (asignatura != other.asignatura)
						return false;
					if (year != other.year)
						return false;
					return true;
				}
				private Matricula getOuterType() {
					return Matricula.this;
				}
				public long getAlumno() {
					return alumno;
				}
				public void setAlumno(long alumno) {
					this.alumno = alumno;
				}
				public long getAsignatura() {
					return asignatura;
				}
				public void setAsignatura(long asignatura) {
					this.asignatura = asignatura;
				}
				public int getYear() {
					return year;
				}
				public void setYear(int year) {
					this.year = year;
				}
				@Override
				public String toString() {
					return "IdMatricula [alumno=" + alumno + ", asignatura=" + asignatura + ", year=" + year + "]";
				}
				
		}
		
		private IdMatricula id = null;
		private Integer nota = null;

		public Matricula(long alumno, long asignatura, int year, Integer nota) {
			this.id = new IdMatricula(alumno, asignatura, nota);
			this.nota = nota;
		}
		public Matricula(IdMatricula id) {
			this.id = id;
		}
		public IdMatricula getId() {
			return this.id;
		}
		public void setId(IdMatricula id) {
			this.id = id;
		}
	
		public Integer getNota() {
			return nota;
		}
		@Override
		public String toString() {
			return "Matricula [id=" + id + ", nota=" + nota + "]";
		}
}
