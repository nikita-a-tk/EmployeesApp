package logic;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class ServerHelper {
	private static Connection conn = null;

	public static int setConnection() {
		try {
			String server = "postgresql://127.0.0.1:5432/employees";
			String user = "root";
			String pass = "root";
			conn = DriverManager.getConnection("jdbc:" + server, user, pass);
			System.out.println("Connection opened successfully");
			return 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 1;
		}
	}

	public static MyTableModel execSelectEmployee(String condition) {
		try {
			String[] columnNames = {"ID", "Name", "Age", "Gender"};
			MyTableModel tableModel = new MyTableModel(columnNames);

			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select " +
													"* from employee e " + condition +  "order by e.id");
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();

				rowData.add("" + rs.getInt("id"));
				rowData.add(rs.getString("name"));
				rowData.add("" + rs.getInt("age"));
				rowData.add("" + rs.getInt("gender"));

				tableModel.addRow(rowData);
			}
			return tableModel;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return MyTableModel.getEmptyTableModel();
		}
	}

	public static MyTableModel execSelectJob(String condition) {
		try {
			String[] columnNames = {"Employee ID", "Job Title", "Salary"};
			MyTableModel tableModel = new MyTableModel(columnNames);

			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from job j " + condition + " order by j.id");
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();

				rowData.add("" + rs.getInt("emp_id"));
				rowData.add(rs.getString("title"));
				rowData.add("" + rs.getInt("salary"));

				tableModel.addRow(rowData);
			}
			return tableModel;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return MyTableModel.getEmptyTableModel();
		}
	}

	public static MyTableModel execSelectJoin(String condition) {
		try {
			String[] columnNames = {"ID", "Name", "Age", "Gender", "Job Title", "Salary"};
			MyTableModel tableModel = new MyTableModel(columnNames);

			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select e.id, e.name, e.age, e.gender, j.title, j.salary "
													+ "from employee e "
													+ "left outer join job j "
													+ "on e.id = j.emp_id " + condition + " order by e.id");
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();

				rowData.add("" + rs.getInt("id"));
				rowData.add(rs.getString("name"));
				rowData.add("" + rs.getInt("age"));
				rowData.add(rs.getString("gender"));
				rowData.add(rs.getString("title"));
				rowData.add("" + rs.getInt("salary"));

				tableModel.addRow(rowData);
			}
			return tableModel;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return MyTableModel.getEmptyTableModel();
		}
	}

	public static MyTableModel execSelectSalaryBonus() {
		try {
			String[] columnNames = {"ID", "Name", "Age", "Gender", "Job Title", "Salary", "Bonus"};
			MyTableModel tableModel = new MyTableModel(columnNames);

			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select e.id, e.name, e.age, e.gender, j.title, j.salary, "
													+ "case when age > 30 then salary * 0.3 else 0 end bonus "
													+ "from employee e "
													+ "inner join job j on j.emp_id = e.id "
													+ "order by e.id");
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();

				rowData.add("" + rs.getInt("id"));
				rowData.add(rs.getString("name"));
				rowData.add("" + rs.getInt("age"));
				rowData.add(rs.getInt("gender") == 1 ? "male" : "female");
				rowData.add(rs.getString("title"));
				rowData.add("" + rs.getInt("salary"));
				rowData.add("" + rs.getInt("bonus"));

				tableModel.addRow(rowData);
			}
			return tableModel;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return MyTableModel.getEmptyTableModel();
		}
	}

	public static float execSelectEvalSalary(String condition) {
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select " + condition + "(salary) from job");
			rs.next();
			float result = rs.getFloat(1);
			return result;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return -1;
		}
	}

	public static Vector<String> execSelectEmployees() {
		try {
			Statement stmnt = conn.createStatement();
			Vector<String> emloyees = new Vector<String>();
			ResultSet rs = stmnt.executeQuery("select name from employee order by e.id");
			while (rs.next()) {
				emloyees.add(rs.getString(1));
			}
			return emloyees;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return null;
		}
	}

	public static MyTableModel execSearchQuery(int id, String name,	int gender, String title, int minSalary, int maxSalary) {
		String selectPart = "e.id, e.name, e.age, e.gender, j.title, j.salary "; // for selectPart = selectPart.substring(1);

		// building the query
		ArrayList<String> selectFields = new ArrayList<String>();

		selectFields.add("ID");
		selectFields.add("Name");
		selectFields.add("Age");
		selectFields.add("Gender");
		selectFields.add("Job Title");
		selectFields.add("Salary");

		selectPart = "select " + selectPart;
		System.out.println(selectPart);

		MyTableModel tableModel = new MyTableModel(selectFields);
		ResultSet rs;

		String wherePart = new String();

		if (id > -1) { wherePart += " and e.id = " + id; }
		if (name.length() > 0) { wherePart += " and upper(e.name) like upper('%" + name + "%')"; }
		if (gender > -1) { wherePart += " and e.gender = " + gender; }
		if (title.length() > 0) { wherePart += " and upper(j.title) like upper(%" + title + "%')"; }
		if (minSalary > -1) { wherePart += " and j.salary >= " + minSalary; }
		if (maxSalary > -1) { wherePart += " and j.salary <= " + maxSalary; }

		System.out.println(wherePart);

		try {
			Statement stmnt = conn.createStatement();
			rs = stmnt.executeQuery(selectPart + " from employee e inner join job j "
					+ " on e.id = j.emp_id " + wherePart + " order by e.id");
		} catch (SQLException exc) {
			exc.printStackTrace();
			return MyTableModel.getEmptyTableModel();
		}

		try {
			while (rs.next()) {
				Vector<String> rowData = new Vector<String>();

				rowData.add("" + rs.getInt("id"));
				rowData.add(rs.getString("name"));
				rowData.add("" + rs.getInt("age"));
				rowData.add("" + rs.getInt("gender"));
				rowData.add(rs.getString("title"));
				rowData.add("" + rs.getInt("salary"));

				tableModel.addRow(rowData);
			}
			return tableModel;
		} catch (SQLException exc) {
			exc.printStackTrace();
			return MyTableModel.getEmptyTableModel();
		}
	}

	public static String[] getSingleEmployeeInfo(String id) {
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select e.id, e.name, e.age, e.gender, j.title, j.salary "
													+ "from employee e "
													+ "left outer join job j "
													+ "on e.id = j.id where e.id = '" + id + "' limit 1");
			boolean dataExist = rs.next();
			if (dataExist) {
				int numOfCols = 6;
				String[] emloyeeInfo = new String[numOfCols];
				for (int i = 0; i < numOfCols; i++) {
					emloyeeInfo[i] = rs.getString(i + 1);
				}
				return emloyeeInfo;
			}
		} catch (SQLException exc) {
			System.out.println("SQLException on getSingleEmployeeInfo(String id)");
			exc.printStackTrace();
		}
		return null;
	}

	public static String[] getSingleEmployeeInfo(String id, String title) {
		try {
			Statement stmnt = conn.createStatement();
			ResultSet rs = stmnt.executeQuery("select e.id, e.name, e.age, e.gender, j.title, j.salary "
													+ "from employee e "
													+ "inner join job j "
													+ "where e.id = j.emp_id and e.id = " + id + " and j.title = " + title
													+ " order by e.id");
			rs.next();
			int numOfCols = 7;
			String[] emloyeeInfo = new String[numOfCols];
			for (int i = 0; i < numOfCols; i++) {
				emloyeeInfo[i] = rs.getString(i + 1);
			}
			return emloyeeInfo;
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
		return null;
	}

	public static void execUpdateQuery(String oldTitle, String newId, String newName, String newTitle,
									   String newSalary, int newGender, String newAge) throws SQLException {
		Statement stmnt = conn.createStatement();
		stmnt.execute(String.format("update employee set id = %1$s, name = '%2$s', gender = '%3$s', age = %4$s where id = %1$s",
					newId, newName, newGender, newAge));
		stmnt.execute(String.format("update job set emp_id = %1$s, title = '%2$s', salary = %3$s where id = %1$s and title = '%4$s'",
				newId, newTitle, newSalary, oldTitle));
	}

	public static void execInsertEmployeeInfo(String name, String title,
											  String salary, int gender, String age) {
		try {
			Statement stmnt = conn.createStatement();

			stmnt.execute(String.format("with ins_emp as (insert into employee(name, age, gender) values('%s', %s, '%s') returning id) "
										+ "insert into job(emp_id, title, salary) select id, '%s', %s from ins_emp",
										name, age, gender, title, salary));
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}

	public static void execDeleteEmployee(String[] ids) {

		try {
			Statement stmt = conn.createStatement();
			String empIds = Arrays.toString(ids).replace('[','(').replace(']',')');
			System.out.println(String.format("delete from employee where id in %s", empIds));
			stmt.executeQuery(String.format("delete from employee where id in %s", empIds));
		} catch (SQLException exc) {
			exc.printStackTrace();
		}
	}
}
