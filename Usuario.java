import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.h2.jdbcx.JdbcDataSource;

public class Usuario {

	// Declaracion de variables
	// Propiedades del usuario
	private String nombre, apellido, matricula, genero, correo, celular, puesto, food, medicina, dieta, camiseta;
	private boolean capacitado, vegetariano, alergias;
	private int campus;
	private Cuarto room;

	// Default constructor
	public Usuario(){
		nombre = apellido = matricula = genero = correo = celular = puesto = food = medicina = dieta = camiseta = "";
		capacitado = vegetariano = alergias = false;
		campus = -1;
		room = new Cuarto();
	}
	public Usuario(String puesto){
		nombre = apellido = matricula = genero = correo = celular = food = medicina = dieta = camiseta = "";
		this.puesto = puesto;
		capacitado = vegetariano = alergias = false;
		campus = -1;
		room = new Cuarto();
	}

	private String[]  tableHeader = {"ID","Nombres","Apellidos","Matr�cula","Puesto","G�nero","Campus","Fecha de nacimiento","Correo electr�nico","Celular","Alergias","A. Alimentos","A. Medicamentos","Vegetariano","Dieta","Capacitaci�n previa","Talla de camiseta","Contacto de emergencia","Parentesco","Tel�fono de emergencia","Aseguradora","P�liza","Vencimiento", "Cuarto"};
	
	public String[] campusArray = {"Aguascalientes","Central de Veracruz","Chiapas","Chihuahua","Ciudad de M�xico","Ciudad Ju�rez","Ciudad Obreg�n","Cuernavaca","Cumbres","Estado de M�xico","Eugenio Garza Lag�era","Eugenio Garza Sada","Guadalajara","Hidalgo","Irapuato","Laguna","Le�n","Morelia","Puebla","Quer�taro","Saltillo","San Luis Potos�","Santa Catarina","Santa Fe","Sinaloa","Sonora Norte","Tampico","Toluca","Valle Alto","Zacatecas","Prep School El Paso","Sede Celaya","Sede Colima","Sede Esmeralda","Sede Matamoros","Sede Metepec","Sede Navojoa","Sede Santa Anita"};
	
	String value;

	public Connection connect(){
		// private int id;
		//Create DataSource
		JdbcDataSource ds = new JdbcDataSource();
		Connection c = null;
		try{
			ds.setURL("jdbc:h2:~/test");
			ds.setUser("sa");
			ds.setPassword("sa");
			c = ds.getConnection();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return c;

		/*
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios ORDER BY id DESC LIMIT 1");
		rs.next();
		id = rs.getInt(1);
		 */

	}

	// Statement stmt = conn.createStatement();
	// ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios ORDER BY id DESC LIMIT 1");
	// rs.next();
	// id = rs.getInt(1);

	public void add(){
		//Agregar persona
		Scanner scan = new Scanner(System.in);

		String[] inputs = {"Nombres","Apellidos","Matr�cula","Puesto","G�nero","Campus","Fecha de nacimiento","Correo electr�nico","Celular","Alergias","Alergias a alimentos","Alergias a medicamentos","Vegetariano","Dieta especial","Capacitado previamente","Talla de camiseta","Nombre del contacto de emergencia","Parentesco","Tel�fono de emergencia","Aseguradora","P�liza","Vencimiento"};

		String[] columnNames = {"nombres","apellidos","matricula","puesto","sexo","campus","dob","correo","celular","alergias","comida","medicina","vegetariano","dieta","capacitado","camiseta","contactoemergencia","parentesco","telemergencia","aseguradora","poliza","vence"};

		String[] parentescos = {"Padre","Madre","Herman@","Abuel@","Tutor"};


		String value = "", query = "", temp = "";
		int choice;

		System.out.println("Introduzca los siguientes datos");
		int i = 0;

		// Pedir y registrar nombre, apellido y matr�cula
		for (; i <= 2; i++) {			
			System.out.println(inputs[i]+":");
			value = scan.nextLine();
			query += "'" + value + "',";
		}

		// Pedir puesto
		String[] puestos = {"Participante","Staff","Instructor","Coordinador","Coordinador DAE"};
		System.out.println(inputs[3] + ": \n1 Participante\n2 Staff\n3 Instructor\n4 Coordinador\n5 Coordinador DAE");
		do{
			choice = scan.nextInt();
			
			if(choice<1 || choice>5){
				System.out.println("Opci�n inv�lida, intente nuevamente");
				choice = 0;
			}
		}while(choice == 0);


		// Registrar puesto
		value = puestos[choice-1];
		query += "'" + value + "',";

		// Pedir y regsitrar g�nero
		System.out.println(inputs[4] + ": (H/M)"); // Genero: 
		scan.nextLine(); //Clear buffer
		do{
			value = (scan.nextLine()).toUpperCase();
			if(!value.equals("H") || !value.equals("M"))
				System.out.println("Opci�n inv�lida, intente nuevamente");
		}while(!value.equals("H") || !value.equals("M"));


		query += "'" + value + "',";

		// Pedir y registrar campus
		int digit = 0;
		System.out.println(inputs[5] + ":"); // Campus: 

		// Imprimir lista de campus
		for (int k = 0; k < campusArray.length; k++)
			System.out.println(k+1 +" " + campusArray[k]);

		do{
			digit = scan.nextInt();
			if(digit >= 1 && digit <= 38){
				query += "'" + campusArray[digit-1] + "',";
			} else
				System.out.println("Opci�n inv�lida, intente nuevamente");
		}while(digit < 1 || digit > 38);


		// Pedir y registrar DOB
		System.out.println(inputs[6] + ": (YYYY-MM-DD)");
		scan.nextLine(); //Clear buffer
		value = scan.nextLine();
		
		// Validate DOB PENDING
		query += "'" + value + "',";


		// Pedir y registrar correo y celular
		// scan.nextLine();//Clear buffer

		for (i = 7; i <= 8 ; i++) {
			System.out.println(inputs[i]+":");
			value = scan.nextLine();
			query += "'" + value + "',";			
		}

		// Pedir si existen alergias
		System.out.println(inputs[9] + ": (Y/N)"); // Alergias: (Y/N) 
		do{
			value = scan.nextLine();
			value = value.toUpperCase();
			if(value.equals("Y")){

				query += "'" + value + "',";				

				// Verificiar si tiene alergias a alimentos
				System.out.println("Alergias a alimentos: (Y/N)");
				do{
					value = scan.nextLine();
					value = value.toUpperCase();
					if(value.equals("Y")){
						System.out.println("Alergias a alimentos:");
						value = scan.nextLine();
						query += "'" + value + "',";
					} else if(value.equals("N")){
						value = "NULL";
						temp = value + ",";
						query += temp;
					} else
						System.out.println("Opci�n inv�lida, intente nuevamente");
				}while(!value.equals("Y") && !value.equals("N"));
			
				// Verificar si tiene alergias a medicamentos
				System.out.println("Alergias a medicamentos: (Y/N)");	
				do{
					value = scan.nextLine();
					value = value.toUpperCase();
					if(value.equals("Y")){
						System.out.println("Alergias a medicamentos:");
						value = scan.nextLine();
						query += "'" + value + "',";
					} 
					else if(value.equals("N")){
						value = "NULL";
						query += value + ",";
					} 
					else
						System.out.println("Opci�n inv�lida, intente nuevamente");
				}while(!value.equals("Y") && !value.equals("N"));
			}
			else if(value.equals("N")){
				for(int o = 0; o < 3; o++){
					value = "NULL";
					query += value + ",";
				}
			}
			else
				System.out.println("Opci�n inv�lida, intente nuevamente");
		}while(!value.equals("Y") && !value.equals("N"));

		// Pedir si es vegetariano
		int j = 0;
		System.out.println("Vegetariano: (Y/N)");

		do{
			value = scan.nextLine();
			if((value.toUpperCase()).equals("Y")){
				j = 1;
			} else if((value.toUpperCase()).equals("N")){
				j = 1;
			} else{
				j = 0;
				System.out.println("Opci�n inv�lida, intente nuevamente");
			}
		}while(j == 0);
		
		temp = "'" + value.toUpperCase() + "',";
		query += temp;

		// Pedir si tiene dieta especial
		j = 0;
		System.out.println("Dieta especial: (Y/N)");

		do{
			value = scan.nextLine();
			if((value.toUpperCase()).equals("Y")){
				j = 1;
				System.out.println("Dieta especial:");
				value = scan.nextLine();
				temp = "'" + value + "',";
				query += temp;
			} else if((value.toUpperCase()).equals("N")){
				j = 1;
				value = "NULL";
				temp = value + ",";
				query += temp;
			} else{
				j = 0;
				System.out.println("Opci�n inv�lida, intente nuevamente");
			}
		}while(j == 0);

		// Pedir si ha sido capacitado
		j = 0;
		System.out.println("Capacitado previamente: (Y/N)");

		do{
			value = scan.nextLine();
			if((value.toUpperCase()).equals("Y")){
				j = 1;
			} else if((value.toUpperCase()).equals("N")){
				j = 1;
			} else{
				j = 0;
				System.out.println("Opci�n inv�lida, intente nuevamente");
			}
		}while(j == 0);
		
		temp = "'" + value.toUpperCase() + "',";
		query += temp;

		// Pedir talla de camiseta
		System.out.println("Talla de camiseta: (S-M-L)");
	
		value = scan.nextLine();
	
		while(!((value.toUpperCase()).equals("S")) && !((value.toUpperCase()).equals("M")) && !((value.toUpperCase()).equals("L"))){
			System.out.println("Opci�n inv�lida, intente nuevamente");
			value = scan.nextLine();
		}
		
		temp = "'" + value.toUpperCase() + "',";
		query += temp;

		// Pedir contacto de emergencia
		System.out.println(inputs[16]+":");
		value = scan.nextLine();
		temp = "'" + value + "',";
		query += temp;

		// Pedir parentesco
		System.out.println(inputs[17]+": \n1 Padre \n2 Madre \n3 Herman@ \n4 Abuel@ \n5 Tutor");
		do{
			choice = scan.nextInt();
			
			if(choice<1 || choice>5){
				System.out.println("Opci�n inv�lida, intente nuevamente");
				choice = 0;
			}
		}while(choice == 0);
		// Registrar parentesco
		value = parentescos[choice-1];
		
		temp = "'" + value + "',";
		query += temp;

		// Pedir tel�fono de emergencia
		scan.nextLine();//Clear buffer
		System.out.println(inputs[18]+":");
		value = scan.nextLine();
		temp = "'" + value + "',";
		query += temp;

		// Pedir aseguradora
		System.out.println(inputs[19]+":");
		value = scan.nextLine();
		temp = "'" + value + "',";
		query += temp;

		// Pedir p�liza
		System.out.println(inputs[20]+":");
		value = scan.nextLine();
		temp = "'" + value + "',";
		query += temp;

		// Pedir vencimiento
		System.out.println(inputs[21] + ": (YYYY-MM-DD)");
		value = scan.nextLine();
		temp = "'" + value + "',";
		query += temp;
				
		
		// Escape this
		String columnary = "";
		temp = "";
		value = "";

		for(int m=0; m<inputs.length;m++){
			value = columnNames[m];
			temp = value + ",";
			columnary = columnary + temp;
		}
		
		String insert = "INSERT INTO usuarios(" + columnary + ") VALUES(" + query + ")";

		Connection connection = this.connect();
		try{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(insert);
			System.out.println("Usuario agregado exitosamente");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void edit(){
		Scanner scan = new Scanner(System.in);
		String column; // Columna a editar
		String query = "";
		int choice;

		// Iniciar la conexi�n con la BD
		Connection connection = this.connect();

		// Referenciar al usuario a editar
		System.out.println("Introduzca el ID de la persona a editar");
		int id = scan.nextInt();

		System.out.println("Introduzca el tipo de dato a editar \n1 Datos personales \n2 Datos m�dicos \n3 Datos generales \n4 Datos de emergencia");

		do{
			choice = scan.nextInt();
			switch(choice){
			// Modificaci�n de datos personales
				case 1: 
					System.out.println("Introduzca el campo a editar  \n1 Nombres\n2 Apellidos \n3 Matr�cula \n4 G�nero \n5 Fecha de nacimiento \n6 Correo electr�nico \n7 Celular \n8 Campus \n9 Puesto");
					column = ""; 

					do{
					choice = scan.nextInt();
						switch(choice){
							case 1:
								column = "NOMBRES";
								break;
							case 2:
								column = "APELLIDOS";
								break;
							case 3:
								column = "MATRICULA";
								break;
							case 4:
								column = "SEXO";
								break;
							case 5:
								// Fecha de nacimiento
								column = "DOB";
								System.out.println("Introduzca la nueva fecha en formato YYYY-MM-DD");
								scan.nextLine(); // Clear buffer
								value = scan.nextLine();
								break;
							case 6:
								column = "CORREO";
								break;
							case 7:
								column = "CELULAR";
								break;
							case 8:
								column = "CAMPUS";
								String[] campus = {"Aguascalientes","Central de Veracruz","Chiapas","Chihuahua","Ciudad de M�xico","Ciudad Ju�rez","Ciudad Obreg�n","Cuernavaca","Cumbres","Estado de M�xico","Eugenio Garza Lag�era","Eugenio Garza Sada","Guadalajara","Hidalgo","Irapuato","Laguna","Le�n","Morelia","Puebla","Quer�taro","Saltillo","San Luis Potos�","Santa Catarina","Santa Fe","Sinaloa","Sonora Norte","Tampico","Toluca","Valle Alto","Zacatecas","Prep School El Paso","Sede Celaya","Sede Colima","Sede Esmeralda","Sede Matamoros","Sede Metepec","Sede Navojoa","Sede Santa Anita"};
								// Imprimir lista de campus
								for (int i=0; i<campus.length; i++)
									System.out.println(i+1 +" " + campus[i]);

								System.out.println("Introduzca el campus");
								scan.nextLine(); // Clear buffer
								int opcion = scan.nextInt();
								value = campus[opcion-1];

								break;
							case 9:
							column = "PUESTO";
							break;
							default:
						System.out.println("Opci�n inv�lida, intente nuevamente");
						choice=0;
						}
					}while(choice == 0);
					
					if((choice != 5) && (choice != 8)){				
							System.out.println("Introduzca el nuevo dato");
							scan.nextLine();
							value = scan.nextLine();
					}
					query = "UPDATE usuarios SET "+ column + "='"+ value + "' WHERE ID=" + id;

					break;
				// Modificaci�n de datos m�dicos
				case 2:
					System.out.println("Introduzca el campo a editar \n1 Alergias \n2 Dietas especiales");
					choice = scan.nextInt(); // Leer opci�n
					switch(choice){
					// ALERGIAS
					case 1:
						column = "ALERGIAS";
						System.out.println("�Alergias? Y/N");
						scan.nextLine(); // Clear buffer
						String input = scan.nextLine();// temporary variable
						if(((input.toUpperCase()).equals("Y"))){
							alergias = true;
						}

						if(alergias){

							String column2 = "", newValue = "";
							do{
								System.out.println();
								System.out.println("Introduzca el campo a editar \n 1 Alergias a alimentos \n 2 Alergias a medicamentos");
								choice = scan.nextInt(); // Leer opcion


								// Alergias a alimentos
								if(choice == 1) 
									column2 = "COMIDA";
								// Alergias a medicamentos
								else if(choice == 2)
									column2 = "MEDICINA";
								else
									System.out.println("Opci�n inv�lida");

							}while(choice != 1 || choice != 2);


							System.out.println("Introduzca las alergias a " + column2.toLowerCase());
							scan.nextLine(); // Clear buffer

							newValue = scan.nextLine(); // Read line

							/*
									column = ALERGIAS
									alergias = Y/N
									column2 = COMIDA/MEDICINA

							 */
							query = "UPDATE usuarios SET "+ column + "='"+ alergias +"', " + column2 + "='" + newValue + "' WHERE ID="+id;

						} 
						// Si no tiene alergias
						else if(!alergias){
							/*
									Incluir descripcion de justificacion de este metodo
							 */
							String value2 = "COMIDA";
							String value3 = "MEDICINA";
							query = "UPDATE usuarios SET "+ column + "='"+ alergias +"', " + value2 + "= NULL, " + value3 + "= NULL WHERE ID=" + id;
						} 
						else if(!(input.equals("Y")) && !(input.equals("N"))){
							System.out.println("Opci�n inv�lida");
						}
						break;
						// DIETAS ESPECIALES
					case 2:
						column = "DIETA";
						System.out.println("�Dieta especial? Y/N");
						scan.nextLine();//Clear buffer
						dieta = scan.nextLine();

						if((dieta.toUpperCase()).equals("Y")){
							System.out.println("Introduzca la dieta especial");
							dieta = scan.nextLine();
							query = "UPDATE usuarios SET "+ column + "='"+ dieta + "' WHERE ID="+id;
						}

						//Si no tiene dieta especial
						else if((dieta.toUpperCase()).equals("N")){
							query = "UPDATE usuarios SET "+ column + "= NULL WHERE ID="+id;
						}

						else{
							System.out.println("Opci�n inv�lida");
						}
						break;
					default:
					}

					break;
				// Modificaci�n de datos generales
				case 3:
					System.out.println("Introduzca el campo a editar \n1 Vegetariano \n2 Capacitaciones previas \n3 Talla de camiseta");
					choice = scan.nextInt();
					column = "";
					switch(choice){
					case 1:
						column = "VEGETARIANO";
						System.out.println("�Vegetariano? Y/N");
						scan.nextLine();//Clear buffer
						String input = scan.nextLine();

						while(!((input.toUpperCase()).equals("Y")) && !((input.toUpperCase()).equals("N"))){//Repetir si no es Y o N
							System.out.println("Opci�n inv�lida, intente nuevamente");
							
							if((input.toUpperCase()).equals("Y")){
								vegetariano = true;
							} else {
								vegetariano = false;
							}
						}
						query = "UPDATE usuarios SET "+ column + "='"+ vegetariano + "' WHERE ID=" + id;
						break;

					case 2:
						column = "CAPACITADO";
						System.out.println("�Capacitado previamente? Y/N");
						scan.nextLine();//Clear buffer
						input = scan.nextLine();//temporary

						while(!((input.toUpperCase()).equals("Y")) && !((input.toUpperCase()).equals("N"))){//Repetir si no es Y o N
							System.out.println("Opci�n inv�lida, intente nuevamente");
							input = scan.nextLine();
						}

						if((input.toUpperCase()).equals("Y")){
							capacitado = true;
						} else {
							capacitado = false;
						}

						query = "UPDATE usuarios SET "+ column + "='"+ capacitado + "' WHERE ID=" + id;
						break;

					case 3:
						column = "CAMISETA";
						System.out.println("�Talla de camiseta? S-M-L");
						scan.nextLine();//Clear buffer
						camiseta = scan.nextLine();

						while(!((camiseta.toUpperCase()).equals("S")) && !((camiseta.toUpperCase()).equals("M")) && !((camiseta.toUpperCase()).equals("L"))){//Repetir si no es S o M o L
							System.out.println("Opci�n inv�lida, intente nuevamente");
							camiseta = scan.nextLine();
						}

						value = camiseta.toUpperCase();
						query = "UPDATE usuarios SET "+ column + "='"+ value + "' WHERE ID=" + id;
						break;
					}
					break;
				// Modificaci�n de datos de emergencia
				case 4:
					column = "";
					System.out.println("Introduzca el campo a editar \n1 Contacto de emergencia \n2 Seguro de Gastos M�dicos Mayores");
					do{
						choice = scan.nextInt();
						switch(choice){
							case 1:
								System.out.println("Introduzca el campo a editar \n1 Nombre del contacto \n2 Parentesco \n3 Tel�fono de emergencia");
								scan.nextLine();//Clear buffer
								do{
									choice = scan.nextInt(); // Leer opci�n
									switch(choice){
										case 1:
											column = "CONTACTOEMERGENCIA";
											break;

										case 2:
											column = "PARENTESCO";
											break;

										case 3:
											column = "TELEMERGENCIA";
											break;

										default:
										System.out.println("Opci�n inv�lida, intente nuevamente");
										choice = 0;
									}
								}while(choice == 0);
								System.out.println("Introduzca el nuevo dato");
								scan.nextLine();
								value = scan.nextLine();
								query = "UPDATE usuarios SET "+ column + "='"+ value + "' WHERE ID=" + id;
								break;

							case 2:
								System.out.println("Introduzca el campo a editar \n1 Aseguradora \n2 P�liza \n3 Vencimiento");
								do{
									choice = scan.nextInt(); // Leer opci�n
									switch(choice){
										case 1:
											column ="ASEGURADORA";
										break;

										case 2:
											column = "POLIZA";
										break;

										case 3:
											column = "VENCE";
											System.out.println("Introduzca la nueva fecha en formato YYYY-MM-DD");
											scan.nextLine(); // Clear buffer
											value = scan.nextLine();
										break;

										default:
										System.out.println("Opci�n inv�lida, intente nuevamente");
										choice = 0;
									}
								}while(choice ==0);
				
								if(choice != 3){				
									System.out.println("Introduzca el nuevo dato");
									scan.nextLine();
									value = scan.nextLine();
								}
								query = "UPDATE usuarios SET "+ column + "='"+ value + "' WHERE ID=" + id;
								break;
							
							default:
							System.out.println("Opci�n inv�lida, intente nuevamente");
							choice = 0;
						}
					}while(choice == 0);
					break;
				default:
					System.out.println("Opci�n inv�lida, intente nuevamente");
					choice = 0;
			}
		}while(choice == 0);
		try{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			System.out.println("Dato editado exitosamente \n");
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void deleteEntry(){
		Scanner scan = new Scanner(System.in);
		int id;
		String choice = "", query = "";

		// Get DB connection
		Connection connection = this.connect();
		Statement stmt;
		
		try{
			System.out.println("Introduzca el ID del usuario a eliminar");
			id = scan.nextInt();
			query = "SELECT nombres, apellidos FROM usuarios WHERE ID=" + id;
						
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			// Comprobar si desea eliminar al usuario
			System.out.println("Desea eliminar al usuario: " + rs.getString(1) + " " + rs.getString(2) + " (Y/N)");
			scan.nextLine();//Clear buffer
			choice = scan.nextLine();

			if((choice.toUpperCase()).equals("Y")){
				query = "DELETE FROM usuarios WHERE ID="+id;
				stmt.executeUpdate(query);
				System.out.println("Usuario eliminado exitosamente");
			} else {
				System.out.println("Cancelado");
			}
		rs.close();
		connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void resetTable(){
		Scanner scan = new Scanner(System.in);
		String choice = "";
		Statement stmt;
		
		Connection connection = this.connect();
		try{
			stmt = connection.createStatement();
			// Comprobar si desea borrar la tabla
			System.out.println("�Desea eliminar TODOS los datos? (Y/N)");
			scan.nextLine();//Clear buffer
			choice = scan.nextLine();

			if((choice.toUpperCase()).equals("Y")){
				stmt.executeUpdate("TRUNCATE TABLE usuarios");
				System.out.println("Tabla borrada exitosamente");
			} else {
				System.out.println("Cancelado");
			}
		connection.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
/*
//COUNT ROWS

ResultSet rs= stmt.executeQuery("SELECT COUNT(*) FROM usuarios");
rs.next();
int count = rs.getInt(1);
*/
	public void printTable(){
		// Print user info
		Connection connection = this.connect();
		Statement stmt;

		try{
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios ORDER BY ID ASC");

			for(int i=0; i<tableHeader.length; i++)
				System.out.print(tableHeader[i]+"\t");

			System.out.println();

			while(rs.next()){
				int id = rs.getInt(1);
				String nombre = rs.getString(2);
				String apellido = rs.getString(3);
				String matricula = rs.getString(4);
				String puesto = rs.getString(5);
				String genero = rs.getString(6);
				int digit = rs.getInt(7);
				String dob = rs.getString(8);
				String correo = rs.getString(9);
				String celular = rs.getString(10);
				String alergias = rs.getString(11);
				String comida = rs.getString(12);
				String medicina = rs.getString(13);
				String vegetariano = rs.getString(14);
				String dieta = rs.getString(15);
				String capacitacion = rs.getString(16);
				String camiseta = rs.getString(17);
				String room = rs.getString(18);

				System.out.println(id + "\t" + nombre + "\t" + apellido + "\t" + matricula + "\t" + puesto + "\t" + genero +  "\t" + campusArray[digit-1] + "\t" + dob + "\t" + correo + "\t" + celular + "\t" + alergias + "\t" + comida + "\t" + medicina + "\t" + vegetariano + "\t" + dieta + "\t" + capacitacion + "\t" + camiseta + "\t" + room);
			}
			rs.close();
			connection.close();
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void search(){
		Scanner scan = new Scanner(System.in);
		Connection connection = this.connect();
		Statement stmt;
		String search, query;
		
		try{
			stmt = connection.createStatement();
			System.out.println("Introduzca la palabra clave a buscar");
			search = scan.nextLine();
			stmt.execute("CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\"");
			stmt.execute("CALL FT_INIT()");
			stmt.execute("CALL FT_REINDEX()");
	
			System.out.println(search);
			query = "SELECT T.* FROM FT_SEARCH_DATA('" + search + "', 0, 0) FT, usuarios T WHERE FT.TABLE='usuarios' AND T.ID=FT.KEYS[0]";  
			ResultSet rs = stmt.executeQuery(query);
			
			// Print header
			for(int i=0; i<tableHeader.length; i++){
				System.out.print(tableHeader[i]+"\t");
			}
			System.out.println();

			while(rs.next()){
				int id = rs.getInt(1);
				String nombre = rs.getString(2);
				String apellido = rs.getString(3);
				String matricula = rs.getString(4);
				String puesto = rs.getString(5);
				String genero = rs.getString(6);
				String campus = rs.getString(7);
				String dob = rs.getString(8);
				String correo = rs.getString(9);
				String celular = rs.getString(10);
				String alergias = rs.getString(11);
				String comida = rs.getString(12);
				String medicina = rs.getString(13);
				String vegetariano = rs.getString(14);
				String dieta = rs.getString(15);
				String capacitacion = rs.getString(16);
				String camiseta = rs.getString(17);

				System.out.println(id + "\t" + nombre + "\t" + apellido + "\t" + matricula + "\t" + puesto + "\t" + genero +  "\t" + campus + "\t" + dob + "\t" + correo + "\t" + celular + "\t" + alergias + "\t" + comida + "\t" + medicina + "\t" + vegetariano + "\t" + dieta + "\t" + capacitacion + "\t" + camiseta);
			}
			
		} catch(SQLException e){
			System.out.println(e.getMessage());
		}
		
	}
}