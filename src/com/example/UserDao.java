package com.example;

import java.sql.*;
import javax.sql.DataSource; // xml을 사용한다면!

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	private DataSource dataSource;
//	private ConnectionMaker connectionMaker;
//	private Connection c;
	private User user;
	
//	public UserDao(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker = connectionMaker;
//	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws SQLException {
//		Connection c = connectionMaker.makeConnection();
		Connection c = this.dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement(
				"INSERT INTO USERS(ID, NAME, PASSWORD) VALUES(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws SQLException {
//		this.c = connectionMaker.makeConnection();
		Connection c = this.dataSource.getConnection();
		
	    PreparedStatement ps = c.prepareStatement(
	    		"SELECT * FROM USERS WHERE ID = ?");
	    ps.setString(1, id);
	
	    ResultSet rs = ps.executeQuery();
	    
	    User user = null;
	    if (rs.next()) {
		    user = new User();
		    user.setId(rs.getString("ID"));
		    user.setName(rs.getString("NAME"));
		    user.setPassword(rs.getString("PASSWORD"));
	    }
	    
	    rs.close();
	    ps.close();
	    c.close();
	    
	    if (user == null) throw new EmptyResultDataAccessException(1);
	    
	    return user;
	    
//	    rs.next();
//	
//	    this.user = new User();
//	    this.user.setId(rs.getString("ID"));
//	    this.user.setName(rs.getString("NAME"));
//	    this.user.setPassword(rs.getString("PASSWORD"));
//	    
//	    return this.user;
		
//		Connection c = connectionMaker.makeConnection();
//
//	    PreparedStatement ps = c.prepareStatement(
//	        "SELECT * FROM USERS WHERE ID = ?");
//	    ps.setString(1, id);
//
//	    ResultSet rs = ps.executeQuery();
//	    rs.next();
//
//	    User user = new User();
//	    user.setId(rs.getString("ID"));
//	    user.setName(rs.getString("NAME"));
//	    user.setPassword(rs.getString("PASSWORD"));
//
//	    rs.close();
//	    ps.close();
//	    c.close();
//
//	    return user;
	}
	
	public void deleteAll() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("DELETE FROM USERS");
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();
		
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM USERS");
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);
		
		rs.close();
		ps.close();
		c.close();
		
		return count;
	}
}
