package com.puerta18.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.puerta18.model.Socio;

@Controller
public class SociosController {

	@Autowired
	private Environment env;

	// landing page, muestra un formulario de busqueda 
	// y tambien muestra los resultados con un parametro no requerido 
	@GetMapping("/")
	public String landing(@RequestParam(required=false) String palabraClave,Model template) throws SQLException {
		
		if (palabraClave == null) {
			palabraClave = "#$%&/()";
		}

		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));

		PreparedStatement consulta = connection
				.prepareStatement("SELECT * FROM socios "
						+ "WHERE ( unaccent(lower(nombre)) LIKE unaccent(lower(?)) OR unaccent(lower(apellido)) LIKE unaccent(lower(?)) OR documento LIKE ? ) "
						+ "AND NOT presente;");
		// no agregue en el 3 porque debe ser exacto.
		consulta.setString(1, "%" + palabraClave + "%");
		consulta.setString(2, "%" + palabraClave + "%");
		consulta.setString(3, palabraClave);
		ResultSet resultados = consulta.executeQuery();

		consulta = connection
				.prepareStatement("SELECT * FROM socios WHERE presente;");

		ResultSet resultadosPresentes = consulta.executeQuery();
		ArrayList<Socio> socios = new ArrayList<Socio>();
		ArrayList<Socio> sociosPresentes = new ArrayList<Socio>();

		while (resultados.next()) {
			int id = resultados.getInt("id");
			String nombre = resultados.getString("nombre");
			String apellido = resultados.getString("apellido");
			String mail = resultados.getString("email");
			String dni = resultados.getString("documento");
			Socio elsocio = new Socio(id, nombre, apellido, mail, dni);
			socios.add(elsocio);
		}
		
		while (resultadosPresentes.next()) {
			int id = resultadosPresentes.getInt("id");
			String nombre = resultadosPresentes.getString("nombre");
			String apellido = resultadosPresentes.getString("apellido");
			String mail = resultadosPresentes.getString("email");
			String dni = resultadosPresentes.getString("documento");
			Socio elsocio = new Socio(id, nombre, apellido, mail, dni);
			sociosPresentes.add(elsocio);
		}

		template.addAttribute("socios", socios);
		template.addAttribute("sociosPresentes", sociosPresentes);

		connection.close();
		
		return "index";
	}
	
	
	@GetMapping("/admin/socios") 
	public String adminSocios(Model template) {
		return "admin/socios";
	}
	
	@PostMapping("/logout") 
	public String logout(Model template) {
		return "redirect:/";
	}	
	@GetMapping("/login") 
	public String login(Model template) {
		return "login";
	}
	@GetMapping("/socios/nuevo") // formulario de alta vacio
	public String nuevo(Model template) {
		return "nuevo";
	}
	
	@GetMapping("/socios/nuevo/procesar") // inserta nuevos socios
	public String insertarNuevo(@RequestParam String nombre,@RequestParam String apellido,@RequestParam String email, 
			@RequestParam String documento,@RequestParam String nacimiento, @RequestParam String celular,
			@RequestParam String genero, @RequestParam String celular_tutor, @RequestParam String telefono,
			@RequestParam String tutor,
			Model template) 
			throws SQLException {
		
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaNacimiento  = LocalDate.parse(nacimiento, formatter);
		
		Connection connection;
		
		connection = DriverManager.getConnection(
				env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"),
				env.getProperty("spring.datasource.password"));
		
		PreparedStatement consulta = 
				connection.prepareStatement(
	"INSERT INTO Socios (nombre, apellido, email, documento, nacimiento, celular, genero, celular_tutor, telefono, tutor)" + 
	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
				);
		consulta.setString(1, nombre );
		consulta.setString(2, apellido);
		consulta.setString(3, email);
		consulta.setString(4, documento );
		consulta.setDate(5, java.sql.Date.valueOf(fechaNacimiento));
		consulta.setString(6, celular);
		consulta.setString(7, genero );
		consulta.setString(8, celular_tutor);
		consulta.setString(9, telefono);
		consulta.setString(10, tutor );
		
		consulta.execute();
		consulta.close();
		return "redirect:/";
		
		
	}

	
	@GetMapping("/socios/checkin/{id}") // 
	public String checkIn(@PathVariable int id , Model template) throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		String checkin = "checkin";
		PreparedStatement consulta = connection
				.prepareStatement("INSERT INTO  checks(id_socio, momento,tipo)VALUES (?, NOW(), 'in')");
		consulta.setInt(1, id);
		consulta.execute();
		
		consulta = connection.prepareStatement("UPDATE  socios SET presente = true  WHERE id = ? ");		
		consulta.setInt( 1, id );
		consulta.execute ();

		connection.close();
		return "redirect:/";
	}
	
	@GetMapping("/socios/checkout/{id}") // 
	public String checkOut(@PathVariable int id , Model template) throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"),
				env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));
		String checkin = "checkin";
		PreparedStatement consulta = connection
				.prepareStatement("INSERT INTO  checks(id_socio, momento,tipo)VALUES (?, NOW(), 'out')");
		consulta.setInt(1, id);
		consulta.execute();
		
		consulta = connection.prepareStatement("UPDATE  socios SET presente = false  WHERE id = ? ");
		consulta.setInt( 1, id );
		consulta.execute ();

		connection.close();
		return "redirect:/";
	}
	
	// estas rutas mas adelante vamos a protegerlas con usuario y contraseña
	// @GetMapping("/socios/mostrar/{id}") // muestra el detalle completo de un socio
	// @GetMapping("/socios/listado")      // muestra el listado completo sin paginacion, por ahora
	
	// @GetMapping("/socios/modificar/{id}")
	// @GetMapping("/socios/modificar/procesar/{id}")
}
