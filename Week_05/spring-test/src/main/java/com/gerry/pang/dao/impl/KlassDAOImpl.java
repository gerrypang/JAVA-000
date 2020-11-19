package com.gerry.pang.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Repository;

import com.gerry.pang.dao.KlassDAO;
import com.gerry.pang.domain.Klass;

@Repository
public class KlassDAOImpl implements KlassDAO {

	private Connection connection;
	private PreparedStatement statement;
	@Autowired
	private DataSourceProperties dataSourceProperties;
	
	private void createConnection() throws Exception {
		// STEP 1: Register JDBC driver
		Class.forName(dataSourceProperties.getDriverClassName());
		// STEP 2: Open a connection
		System.out.println("Connecting to database...");
		connection = DriverManager.getConnection(dataSourceProperties.getUrl(), 
				dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
		if (connection != null) {
			System.out.println("Connection successful!");
		} else {
			System.err.println("Connection failed!");
		}
	}
	
	private void closeConnection() throws Exception {
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	
	@Override
	public Klass saveOne(Klass domain) {
		Klass result = null;
		try {
			createConnection();
			// 参考：https://stackoverflow.com/questions/1915166/how-to-get-the-insert-id-in-jdbc
			String sql = "insert into tb_klass(name, code) values(?, ?)";
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, domain.getName());
			statement.setString(2, domain.getCode());
			int rs = statement.executeUpdate();
			if (rs == 0) {
				 throw new SQLException("Creating klass failed, no rows affected.");
			}
			
			result = Klass.builder().build();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				result.setId(generatedKeys.getInt(1));
			}
			result.setCode(domain.getCode());
			result.setName(domain.getName());
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean updateOne(Klass domain) {
		boolean result = false;
		try {
			createConnection();
			connection.setAutoCommit(false);

			String sql = "update tb_klass set name=?, code=? where 1=1 and id=?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, domain.getName());
			statement.setString(2, domain.getCode());
			statement.setInt(3, domain.getId());
			int rs = statement.executeUpdate();
			if (rs != 0) {
				result = true;
			}
			
			// 提供事务
			connection.commit();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public boolean updateBatch(List<Klass> domains) {
		boolean result = false;
		try {
			createConnection();
			connection.setAutoCommit(false);
			
			String sql = "update tb_klass set name=?, code=? where 1=1 and id = ? ;";
			statement = connection.prepareStatement(sql);
			for (int i = 0; i < domains.size(); i++) {
				Klass domain = domains.get(i);
				statement.setString(1, domain.getName());
				statement.setString(2, domain.getCode());
				statement.setInt(3, domain.getId());
				// 添加到同一个批处理中
				statement.addBatch();
			}
			
			// 执行批处理
			int[] rs = statement.executeBatch();
			if (rs != null) {
				result = true;
			}
			
			// 提供事务
			connection.commit();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean deleteOne(Klass domain) {
		boolean result = false;
		try {
			createConnection();
			connection.setAutoCommit(false);
			
			String sql = "delete from tb_klass where 1=1 and id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, domain.getId());
			int rs = statement.executeUpdate();
			if (rs != 0) {
				result = true;
			}
			
			// 提供事务
			connection.commit();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Klass selectOne(Klass domain) {
		Klass result = null;
		try {
			createConnection();
			String sql = "select id, name, code from tb_klass where 1=1 and id=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, domain.getId());
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
			    int id  = rs.getInt("id");
			    String name = rs.getString("name");
			    String code = rs.getString("code");
			    result = Klass.builder().id(id).name(name).code(code).build();
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			try {
				closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
