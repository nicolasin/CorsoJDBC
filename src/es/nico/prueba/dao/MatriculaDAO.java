package es.nico.prueba.dao;
import java.util.List;
import es.nico.prueba.modelo.Matricula;

public interface MatriculaDAO extends DAO<Matricula, Matricula.IdMatricula>{
	List<Matricula> obtenerPorAlumno(long alumno) throws DAOException;
	List<Matricula> obtenerPorAsignatura(long asignatura)throws DAOException;
	List<Matricula> obtenerPorCurso(int curso)throws DAOException;

}
