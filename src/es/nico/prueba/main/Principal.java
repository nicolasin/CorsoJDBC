package es.nico.prueba.main;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import es.nico.prueba.dao.DAO;
import es.nico.prueba.dao.DAOException;
import es.nico.prueba.dao.mysql.*;
import es.nico.prueba.modelo.*;

public class Principal{
	static final String url = "jdbc:mysql://localhost:3306/";
	static final String name = "Prueba";
	static final String user = "root";
	static final String pass = "";
	
	public static void main(String[] args) throws DAOException {
		System.out.println("Conectando...");
		try {
			Connection connection = DriverManager.getConnection(url+name,user,pass);
			DAO<Alumno, Long> alumnosSql = new MysqlAlumnoDAO(connection);
			
			Date fecha = Date.valueOf("2005-03-04");
			Alumno a = new Alumno("Federico", "Gonzalez", fecha);
			//alumnosSql.insertar(a);
			ArrayList<Alumno> alumnos = (ArrayList<Alumno>)alumnosSql.obtenerTodos();
//			alumnos.get(9).setApelllidos("LAUREANO");
//			alumnos.get(9).setNombre("Eustaquio");
//			alumnosSql.modificar(alumnos.get(9));
//			alumnosSql.eliminar(alumnos.get(1));
			
			alumnos.forEach(System.out::println);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
