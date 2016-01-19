/**
 * 
 */
package com.razorthink.rzt.internal.management.data.sources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.razorthink.rzt.internal.management.exception.DataException;

/**
 * @author shams
 *
 */
public class ConnectionFactory {

	private static ConnectionFactory connectionFactory = null;
	private Properties properties = new Properties();

	private ConnectionFactory() throws DataException
	{
		try
		{
			properties.load(this.getClass().getResourceAsStream("/application.properties"));
		}
		catch( IOException e )
		{
			e.printStackTrace();
			throw new DataException("cm.erro", e.getMessage());
		}
	}

	public static synchronized ConnectionFactory getInstance() throws DataException
	{
		if( connectionFactory == null )
		{
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}

	public Connection getSQLConnection() throws DataException
	{
		try
		{
			Class.forName(properties.getProperty("spring.datasource.driver-class-name"));
			return DriverManager.getConnection(properties.getProperty("spring.datasource.url"),
					properties.getProperty("spring.datasource.username"),
					properties.getProperty("spring.datasource.password"));

		}
		catch( ClassNotFoundException e )
		{
			e.printStackTrace();
			throw new DataException("cm.erro", e.getMessage());
		}
		catch( SQLException e )
		{
			e.printStackTrace();
			throw new DataException("cm.erro", e.getMessage());
		}

	}
}
