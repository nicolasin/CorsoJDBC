package es.nico.prueba.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.nico.prueba.dao.AsignaturaDAO;
import es.nico.prueba.dao.DAOException;
import es.nico.prueba.modelo.Asignatura;

public class MysqlAsignaturaDAO implements AsignaturaDAO{
	private Connection connection;
	final String INSERT="INSERT INTO asignatura (id_asignatura, nombre, profesor) VALUES(?,?,?)";
	final String UPDATE="UPDATE asignatura SET  nombre = ?, profesor = ?, where id_asignatura = ?";
	final String DELETE="DELETE FROM alumnos where id_asignatura = ?";
	final String GETALL = "SELECT id_asignatura, nombre, profesor FROM asignaturas";
	final String GETONE= "SELECT id_asignatura, nombre, profesor FROM asignaturas WHERE id_asignatura = ?";

	public MysqlAsignaturaDAO(Connection conection) {
		this.connection = connection;
	}
	@Override
	public void insertar(Asignatura a) throws DAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(INSERT);
			statement.setLong(1,a.getId());
			statement.setString(2, a.getNombre());
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
	public void modificar(Asignatura a) throws DAOException{
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE);
			statement.setString(1,a.getNombre());
			statement.setLong(2,a.getiProfesor());
			statement.setLong(3,a.getId());
			if(statement.executeUpdate()==0) {
				throw new DAOException("Error al modificar el registro");
			}
		}catch(SQLException e) {
			throw new DAOException("Error SQL", e);
		}finally {
			if(statement!=null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("Erro SQL", e);
				}
			}
		}

	}

	@Override
	public void eliminar(Asignatura a) throws DAOException{
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DELETE);
			statement.setLong(1,a.getId());
			if(statement.executeUpdate() == 0) {
				throw new DAOException("Error al eliminar la asignatura");
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
	public List<Asignatura> obtenerTodos()throws DAOException {
		PreparedStatement statement = null;
		List<Asignatura> asignaturas = new ArrayList<Asignatura>();
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(GETALL);
			result = statement.executeQuery();
			while(result.next()){
				asignaturas.add(convertir(result));
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
		return asignaturas;
	}
	@Override
	public Asignatura obtener(Long id)throws DAOException {
		PreparedStatement statement = null;
		ResultSet result = null;
		Asignatura asignatura = null;
		try {
			statement = connection.prepareStatement(GETONE);
			statement.setLong(1, id);
			result = statement.executeQuery();
			if(result.next()) {
				asignatura = convertir(result);
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
		return asignatura;
	}
	private Asignatura convertir(ResultSet res)throws DAOException {
		Asignatura asig = null;
		try {
			asig = new Asignatura(res.getString("nombre"), res.getLong("profesors"));
			asig.setId(res.getLong("id_asignatura"));
		}catch(SQLException e) {
			throw new DAOException("Error SQL");
		}
		return asig;
	}
}
