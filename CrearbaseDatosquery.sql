public void crearBasedeDatos() {

		String drive = "jdbc:mysql://localhost:3306/";
		String query = "Create database footballmanager";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(drive,ParametrosConexion.user,ParametrosConexion.pass);
			PreparedStatement pst = conn.prepareStatement(query);
			pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("Error ");
			System.out.println("==========");
			e.printStackTrace();
		}

	}