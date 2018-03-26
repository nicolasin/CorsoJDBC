package es.nico.prueba.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import es.nico.prueba.dao.DAOException;
import es.nico.prueba.dao.MatriculaDAO;
import es.nico.prueba.modelo.Matricula;
import es.nico.prueba.modelo.Matricula.IdMatricula;

public class MysqlMatriculaDAO implements MatriculaDAO {
	private Connection connection;
	final String INSERT = "INSERT INTO matriculas (alumno, asignatura, fecha, nota) VALUES(?,?,?,?)";
	final String UPDATE = "UPDATE matricula SET alumno = ?, asignatura = ?, fecha = ?, nota = ? where alumno = ? and asignatura = ? and fecha = ?";
	final String DELETE = "DELETE FROM matriculas where alumno = ? and asignatura = ?, and fecha = ?";
	final String GETALL = "SELECT alumno, nombre, apellidos, fecha_nac FROM alumnos";
	final String GETONE = "SELECT id_alumno, nombre, apellidos, fecha_nac FROM alumnos WHERE id_alumno = ?";
	final String GETALU = GETALL + " WHERE alumno = ?";
	final String GETCUR = GETALL + " WHERE fecha = ?";
	final String GETASI = GETALL + " WHERE asignatura = ?";

	public void MysqlMatriculaDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insertar(Matricula a) throws DAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(INSERT);
			statement.setLong(1, a.getId().getAlumno());
			statement.setLong(2, a.getId().getAsignatura());
			statement.setInt(3, a.getId().getYear());
			if (a.getNota() != null) {
				statement.setInt(4, a.getNota());
			} else {
				statement.setNull(4, Types.INTEGER);
			}
			if (statement.executeUpdate() == 0) {
				throw new DAOException("Error al insertar registro");
			}
		} catch (SQLException e) {
			throw new DAOException("ERROR SQL");
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("Error SQL");
				}
			}
		}
	}

	@Override
	public void modificar(Matricula a) throws DAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE);
			statement.setLong(1, a.getId().getAlumno());
			statement.setLong(2, a.getId().getAsignatura());
			statement.setInt(3, a.getId().getYear());
			statement.setInt(4, a.getNota());
			statement.setLong(1, a.getId().getAlumno());
			statement.setLong(2, a.getId().getAsignatura());
			statement.setInt(3, a.getId().getYear());

			if (statement.executeUpdate() == 0) {
				throw new DAOException("Error al modificar el registro");
			}
		} catch (SQLException e) {
			throw new DAOException("Error SQL", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("Erro SQL", e);
				}
			}
		}

	}

	@Override
	public void eliminar(Matricula a) throws DAOException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(DELETE);
			statement.setLong(1, a.getId().getAlumno());
			statement.setLong(2, a.getId().getAsignatura());
			statement.setInt(3, a.getId().getYear());

			if (statement.executeUpdate() == 0) {
				throw new DAOException("Error al eliminar matricula");
			}
		} catch (SQLException e) {
			throw new DAOException("ERROR SQL");
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException("Error SQL");
				}
			}
		}

	}

	@Override
	public List<Matricula> obtenerTodos() throws DAOException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		List<Matricula> matriculas = new ArrayList<Matricula>();
		try {
			statement = connection.prepareStatement(GETALL);
			rs = statement.executeQuery();

			while (rs.next()) {
				matriculas.add(convertir(rs));
			}

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}
		return matriculas;
	}

	@Override
	public Matricula obtener(Matricula.IdMatricula id) throws DAOException {
		Matricula matricula = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			statement = connection.prepareStatement(GETONE);
			statement.setLong(1, id.getAlumno());
			statement.setLong(2, id.getAsignatura());
			statement.setInt(3, id.getYear());
			result = statement.executeQuery();
			if (result.next()) {
				matricula = convertir(result);
			} else {
				throw new DAOException("No se encontró la matricula");
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}

			}
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}
			}
		}
		return matricula;
	}

	@Override
	public List<Matricula> obtenerPorAlumno(long alumno) throws DAOException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		List<Matricula> matriculas = new ArrayList<Matricula>();
		try {
			statement = connection.prepareStatement(GETALU);
			statement.setLong(1,alumno);
			rs =statement.executeQuery();
			while(rs.next()) {
				matriculas.add(convertir(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}

			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}
			}
		}
		return matriculas;
	}

	@Override
	public List<Matricula> obtenerPorAsignatura(long asignatura) throws DAOException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		List<Matricula> matriculas = new ArrayList<Matricula>();
		try {
			statement = connection.prepareStatement(GETASI);
			statement.setLong(1,asignatura);
			rs =statement.executeQuery();
			while(rs.next()) {
				matriculas.add(convertir(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}

			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}
			}
		}
		return matriculas;
	}

	@Override
	public List<Matricula> obtenerPorCurso(int curso) throws DAOException {
		ResultSet rs = null;
		PreparedStatement statement = null;
		List<Matricula> matriculas = new ArrayList<Matricula>();
		try {
			statement = connection.prepareStatement(GETCUR);
			statement.setInt(1,curso);
			rs =statement.executeQuery();
			while(rs.next()) {
				matriculas.add(convertir(rs));
			}
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}

			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DAOException(e.getMessage());
				}
			}
		}
		return matriculas;
	}

	private Matricula convertir(ResultSet rs) throws SQLException {
		long alumno = rs.getLong("alumno");
		long asignatura = rs.getLong("asignatura");
		int year = rs.getInt("fecha");

		Integer nota = rs.getInt("nota");
		if (rs.wasNull()) {
			nota = null;
		}

		Matricula matricula = new Matricula(alumno, asignatura, year, nota);
		return matricula;
	}

}
