package update.center.init;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.is.util.db.driver.digestdriver.WsDataSource;

import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.updatercenter.common.context.ContextUtils;

public class DataSourceConnectionFactory  implements IConnectionFactoryX{

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String arg0) {
		// TODO Auto-generated method stub
		try{
			Context initContext = (Context)new InitialContext().lookup("java:/comp/env");
			Object dd = initContext.lookup(arg0);
		//	DataSource ds =	ContextUtils.getParam(arg0, WsDataSource.class , null);
			return null;//return ds.getConnection();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
