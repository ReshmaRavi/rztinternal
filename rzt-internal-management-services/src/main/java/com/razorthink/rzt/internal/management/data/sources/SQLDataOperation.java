/**
 * 
 */
package com.razorthink.rzt.internal.management.data.sources;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import com.razorthink.rzt.internal.management.exception.DataException;
import com.razorthink.utils.cmutils.NullEmptyUtils;

/**
 * @author shams
 *
 */
public class SQLDataOperation {

	private static final Logger log = Logger.getLogger(SQLDataOperation.class);

	private Connection connection = null;
	private Statement st = null;
	private String query;
	private boolean closeConnection = true;

	public SQLDataOperation( boolean closeConnection ) throws DataException
	{
		try
		{
			connection = ConnectionFactory.getInstance().getSQLConnection();
			this.setCloseConnection(closeConnection);
		}
		catch( DataException e )
		{
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}

	}

	public int executeUpdate( int returnGeneratedKeys ) throws DataException
	{
		try
		{
			if( NullEmptyUtils.isNullorEmpty(this.query) )
			{
				throw new DataException("cm.error", "SQL query cannot be null or empty ");
			}
			if( null == this.st )
			{
				createStatement();
			}
			return this.st.executeUpdate(query, returnGeneratedKeys);
		}
		catch( SQLException e )
		{
			log.error(e.getMessage());
			throw new DataException("cm.error", e.getMessage());
		}
		finally
		{
			close();
		}
	}

	public Statement createStatement() throws DataException
	{
		if( null == connection )
		{
			throw new DataException("cm.error", "SQL Connection not avalable ");
		}
		try
		{
			this.st = this.connection.createStatement();
		}
		catch( SQLException e )
		{
			log.error(e.getMessage());
			throw new DataException("cm.error", e.getMessage());
		}
		/*
		 * finally
		 * {
		 * close();
		 * }
		 */
		return this.st;
	}

	public void close()
	{

		if( !this.closeConnection )
		{
			return;
		}
		try
		{
			if( null != st )
			{
				st.close();
			}

			if( null != connection )
			{
				connection.close();
			}

		}
		catch( SQLException e )
		{
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	public String getQuery()
	{
		return query;
	}

	public void setQuery( String query )
	{
		this.query = query;
	}

	public Connection getConnection()
	{
		return connection;
	}

	public void setConnection( Connection connection )
	{
		this.connection = connection;
	}

	public Statement getSt()
	{
		return st;
	}

	public void setSt( Statement st )
	{
		this.st = st;
	}

	public boolean isCloseConnection()
	{
		return closeConnection;
	}

	public void setCloseConnection( boolean closeConnection )
	{
		this.closeConnection = closeConnection;
	}

}
