package es.nico.prueba.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import es.nico.prueba.dao.DAOException;
import es.nico.prueba.dao.ProfesorDAO;
import es.nico.prueba.modelo.Profesor;

public class MysqlProfesorDAO implements ProfesorDAO{
	private Connection connection;
	final String INSERT="INSERT INTO profesores (id_profesor, nombre, apellidos) VALUES(?,?,?)";
	final String UPDATE="UPDATE profesores SET nombre = ?, apellidos = ?, where id_alumno = ?";
	final String DELETE="DELETE FROM profesores where id_profesor = ?";
	final String GETALL = "SELECT id_profesor, nombre, apellidos FROM profesores";
	final String GETONE= "SELECT id_profesor, nombre, apellidos FROM profesores WHERE id_profesor = ?";

	public MysqlProfesorDAO(Connection connection) {
		this.connection = connection;
	}
	@Override
	public void insertar(Profesor a) throws DAOException{
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(INSERT);
			statement.setLong(1,a.getId());
			statement.setString(2, a.getNombre());
			statement.setString(3,a.getApellidos());
			if(statement.executeUpdate() == 0) {
				throw new DAOException("Error al insertar Profesor en la BBDD");
			}
		}catch(SQLException e) {
			throw new DAOException("Error sql");
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL");
				}
			}
		}
	}

	@Override
	public void modificar(Profesor a)throws DAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1, a.getNombre());
			statement.setString(2,a.getApellidos());
			statement.setLong(3,a.getId());
			if(statement.executeUpdate() == 0) {
				throw new DAOException("Error al insertar Profesor en la BBDD");
			}
		}catch(SQLException e) {
			throw new DAOException("Error sql");
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL");
				}
			}
		}

	}

	@Override
	public void eliminar(Profesor a) throws DAOException{
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DELETE);
			statement.setLong(1,a.getId());
			if(statement.executeUpdate() == 0) {
				throw new DAOException("Error al eliminar al profesor");
			}
		}catch(SQLException e) {
			throw new DAOException("Error sql",e);
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL",e);
				}
			}
		}

	}

	@Override
	public List<Profesor> obtenerTodos()throws DAOException {
		PreparedStatement statement = null;
		List<Profesor> profesores = new ArrayList<Profesor>();
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(GETALL);
			result = statement.executeQuery();
			while(result.next()){
				profesores.add(convertir(result));
			}
		}catch(SQLException e) {
			throw new DAOException("Error sql", e);
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL",e);
				}
			}
			if(result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new DAOException("Error SQL",e);
				}
			}
		}
		return profesores;
	}

	@Override
	public Profesor obtener(Long id)throws DAOException {
		PreparedStatement statement = null;
		ResultSet result = null;
		Profesor profesor = null;
		try {
			statement = connection.prepareStatement(GETONE);
			statement.setLong(1, id);
			result = statement.executeQuery();
			if(result.next()) {
				profesor = convertir(result);
			}else {
				throw new DAOException("Registro no encontrado");
			}
		}catch(SQLException e) {
			throw new DAOException("Error sql", e);
		}finally {
			if(statement != null) {
				try {
					statement.close();
				}catch(SQLException e) {
					throw new DAOException("Error SQL", e);
				}
			}
			if(result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new DAOException("Error SQL", e);
				}
			}
		}
		return profesor;
	}

	private Profesor convertir(ResultSet res)throws DAOException {
		Profesor prof = null;
		try {
			prof = new Profesor(res.getString("nombre"), res.getString("apellidos"));
			prof.setID(res.getLong("id_profesor"));
		}catch(SQLException e) {
			throw new DAOException("Error SQL");
		}
		return prof;
	}
}
