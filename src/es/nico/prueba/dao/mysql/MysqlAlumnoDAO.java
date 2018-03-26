package es.nico.prueba.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.nico.prueba.dao.*;
import es.nico.prueba.modelo.Alumno;
import es.nico.prueba.modelo.Profesor;

public class MysqlAlumnoDAO implements AlumnoDAO{
	private Connection connection;
	final String INSERT="INSERT INTO alumnos (nombre, apellidos, fechanac) VALUES(?,?,?)";
	final String UPDATE="UPDATE alumnos SET nombre = ?, apellidos = ?, fechanac = ? where id_alumno = ?";
	final String DELETE="DELETE FROM alumnos where id_alumno = ?";
	final String GETALL = "SELECT id_alumno, nombre, apellidos, fechanac FROM alumnos";
	final String GETONE = "SELECT id_alumno, nombre, apellidos, fechanac FROM alumnos WHERE id_alumno = ?";

	public MysqlAlumnoDAO(Connection connection) {
		this.connection = connection;
	}
	@Override
	public void insertar(Alumno alumno) throws DAOException {
		PreparedStatement statement = null;
		try {
			
			statement = connection.prepareStatement(INSERT);
			statement.setString(1,alumno.getNombre());
			statement.setString(2,alumno.getApelllidos());
			statement.setDate(3, new Date(alumno.getFechaNacimiento().getTime()));
			if(statement.executeUpdate() == 0) {
				throw new DAOException("Error al insertar en la Base de datos");
			}
		}catch(SQLException sqle){
			throw new DAOException("Error en sql", sqle);
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error en SQL", e);
				}
			}
		}

	}

	@Override
	public void modificar(Alumno a) throws DAOException{
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1,a.getNombre());
			statement.setString(2, a.getApelllidos());
			statement.setDate(3, (Date) a.getFechaNacimiento());
			statement.setLong(4, a.getId());
			if(statement.executeUpdate()==0) {
				throw new DAOException("Error SQL");
			}
		}catch(SQLException sql) {
			throw new DAOException("Error SQL", sql);
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL",e);
				}
			}
		}

	}

	@Override
	public void eliminar(Alumno a) throws DAOException{
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DELETE);
			statement.setLong(1,a.getId());
			if(statement.executeUpdate()==0) {
				throw new DAOException("Error al eliminar alumno");
			}
		}catch(SQLException e) {
			throw new DAOException("Error SQL",e);
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL", e);
				}
			}
		}

	}

	@Override
	public List<Alumno> obtenerTodos()throws DAOException {
		ResultSet result = null;
		PreparedStatement statement = null;
		List<Alumno> alumnos = new ArrayList<Alumno>();
		try {
			statement = connection.prepareStatement(GETALL);
			result = statement.executeQuery();
			while(result.next()) {
				alumnos.add(convertir(result));
			}
		}catch(SQLException e) {
			throw new DAOException("SQL Error", e);
		}finally {

			if(statement!= null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("SQL Error", e);
				}
			}
			if(result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new DAOException("SQL Error", e);
				}
			}

		}
		return alumnos;
	}

	@Override
	public Alumno obtener(Long id) throws DAOException{
		Alumno a = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			statement = connection.prepareStatement(GETONE);
			statement.setLong(1, id);
			result = statement.executeQuery();
			if(result.next()) {
				a = convertir(result);
			}else {
				throw new DAOException("No se encuentra el registro en alumno");
			}
		}catch(SQLException e) {
			throw new DAOException("Error SQL", e);
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL", e);
				}
			}
			if(result!=null) {
				try {
					result.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL",e);
				}
			}
		}
		return a;
	}
	private Alumno convertir(ResultSet rs) throws DAOException{
		Alumno a = null;
		try {
			
			String nombre = rs.getString("nombre");
			String apellidos = rs.getString("apellidos");
			String fechanac = rs.getString("fechanac");
			Date f = Date.valueOf(fechanac);
			a = new Alumno(nombre, apellidos, f);
			a.setId(rs.getLong("id_alumno"));
		}catch(SQLException e) {
			throw new DAOException("Error SQL");
		}
		return a;
	}
}
