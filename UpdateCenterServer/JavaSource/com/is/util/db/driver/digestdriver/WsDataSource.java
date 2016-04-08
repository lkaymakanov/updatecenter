package com.is.util.db.driver.digestdriver;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class WsDataSource implements DataSource{
	
	private String keyStoreFile = null;
	private String keyStorePass = null;
	private String keyStoreAlias = null;
	
	public WsDataSource(){
		
	}
	
	public String getKeyStoreFile() {
		return keyStoreFile;
	}

	public void setKeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public String getKeyStorePass() {
		return keyStorePass;
	}

	public void setKeyStorePass(String keyStorePass) {
		this.keyStorePass = keyStorePass;
	}

	public String getKeyStoreAlias() {
		return keyStoreAlias;
	}

	public void setKeyStoreAlias(String keyStoreAlias) {
		this.keyStoreAlias = keyStoreAlias;
	}


	private boolean restartNeeded = false;
	
	/**
     * The default auto-commit state of connections created by this pool.
     */
    protected volatile boolean defaultAutoCommit = true;

    /**
     * Returns the default auto-commit property.
     * 
     * @return true if default auto-commit is enabled
     */
    public boolean getDefaultAutoCommit() {
        return this.defaultAutoCommit;
    }

    /**
     * <p>Sets default auto-commit state of connections returned by this
     * datasource.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param defaultAutoCommit default auto-commit value
     */
    public void setDefaultAutoCommit(boolean defaultAutoCommit) {
        this.defaultAutoCommit = defaultAutoCommit;
        this.restartNeeded = true;
    }


    /**
     * The default read-only state of connections created by this pool.
     */
    protected transient Boolean defaultReadOnly = null;

    /**
     * Returns the default readOnly property.
     * 
     * @return true if connections are readOnly by default
     */
    public boolean getDefaultReadOnly() {
        Boolean val = defaultReadOnly;
        if (val != null) {
            return val.booleanValue();
        }
        return false;
    }

    /**
     * <p>Sets defaultReadonly property.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param defaultReadOnly default read-only value
     */
    public void setDefaultReadOnly(boolean defaultReadOnly) {
        this.defaultReadOnly = defaultReadOnly ? Boolean.TRUE : Boolean.FALSE;
        this.restartNeeded = true;
    }

    /**
     * The default TransactionIsolation state of connections created by this pool.
     */
    protected volatile int defaultTransactionIsolation =0;
      //  PoolableConnectionFactory.UNKNOWN_TRANSACTIONISOLATION;

    /**
     * Returns the default transaction isolation state of returned connections.
     * 
     * @return the default value for transaction isolation state
     * @see Connection#getTransactionIsolation
     */
    public int getDefaultTransactionIsolation() {
        return this.defaultTransactionIsolation;
    }

    /**
     * <p>Sets the default transaction isolation state for returned
     * connections.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param defaultTransactionIsolation the default transaction isolation
     * state
     * @see Connection#getTransactionIsolation
     */
    public void setDefaultTransactionIsolation(int defaultTransactionIsolation) {
        this.defaultTransactionIsolation = defaultTransactionIsolation;
        this.restartNeeded = true;
    }


    /**
     * The default "catalog" of connections created by this pool.
     */
    protected volatile String defaultCatalog = null;

    /**
     * Returns the default catalog.
     * 
     * @return the default catalog
     */
    public String getDefaultCatalog() {
        return this.defaultCatalog;
    }

    /**
     * <p>Sets the default catalog.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param defaultCatalog the default catalog
     */
    public void setDefaultCatalog(String defaultCatalog) {
        if ((defaultCatalog != null) && (defaultCatalog.trim().length() > 0)) {
            this.defaultCatalog = defaultCatalog;
        }
        else {
            this.defaultCatalog = null;
        }
        this.restartNeeded = true;
    }

  
    /**
     * The fully qualified Java class name of the JDBC driver to be used.
     */
    protected String driverClassName = null;

    /**
     * Returns the jdbc driver class name.
     * 
     * @return the jdbc driver class name
     */
    public synchronized String getDriverClassName() {
        return this.driverClassName;
    }

    /**
     * <p>Sets the jdbc driver class name.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param driverClassName the class name of the jdbc driver
     */
    public synchronized void setDriverClassName(String driverClassName) {
        if ((driverClassName != null) && (driverClassName.trim().length() > 0)) {
            this.driverClassName = driverClassName;
        }
        else {
            this.driverClassName = null;
        }
        this.restartNeeded = true;
    }

    /**
     * The class loader instance to use to load the JDBC driver. If not
     * specified, {@link Class#forName(String)} is used to load the JDBC driver.
     * If specified, {@link Class#forName(String, boolean, ClassLoader)} is
     * used.
     */
    protected ClassLoader driverClassLoader = null;
    
    /**
     * Returns the class loader specified for loading the JDBC driver. Returns
     * <code>null</code> if no class loader has been explicitly specified.
     */
    public synchronized ClassLoader getDriverClassLoader() {
        return this.driverClassLoader;
    }

    /**
     * <p>Sets the class loader to be used to load the JDBC driver.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param driverClassLoader the class loader with which to load the JDBC
     *                          driver
     */
    public synchronized void setDriverClassLoader(
            ClassLoader driverClassLoader) {
        this.driverClassLoader = driverClassLoader;
        this.restartNeeded = true;
    }
    
    /**
     * The maximum number of active connections that can be allocated from
     * this pool at the same time, or negative for no limit.
     */
    protected int maxActive = 0;// GenericObjectPool.DEFAULT_MAX_ACTIVE;

    /**
     * <p>Returns the maximum number of active connections that can be
     * allocated at the same time.
     * </p>
     * <p>A negative number means that there is no limit.</p>
     * 
     * @return the maximum number of active connections
     */
    public synchronized int getMaxActive() {
        return this.maxActive;
    }

    /**
     * Sets the maximum number of active connections that can be
     * allocated at the same time. Use a negative value for no limit.
     * 
     * @param maxActive the new value for maxActive
     * @see #getMaxActive()
     */
    public synchronized void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
        /*if (connectionPool != null) {
            connectionPool.setMaxActive(maxActive);
        }*/
    }

    /**
     * The maximum number of connections that can remain idle in the
     * pool, without extra ones being released, or negative for no limit.
     * If maxIdle is set too low on heavily loaded systems it is possible you
     * will see connections being closed and almost immediately new connections
     * being opened. This is a result of the active threads momentarily closing
     * connections faster than they are opening them, causing the number of idle
     * connections to rise above maxIdle. The best value for maxIdle for heavily
     * loaded system will vary but the default is a good starting point.
     */
    protected int maxIdle = 0;// GenericObjectPool.DEFAULT_MAX_IDLE;

    /**
     * <p>Returns the maximum number of connections that can remain idle in the
     * pool.
     * </p>
     * <p>A negative value indicates that there is no limit</p>
     * 
     * @return the maximum number of idle connections
     */
    public synchronized int getMaxIdle() {
        return this.maxIdle;
    }

    /**
     * Sets the maximum number of connections that can remain idle in the
     * pool.
     * 
     * @see #getMaxIdle()
     * @param maxIdle the new value for maxIdle
     */
    public synchronized void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * The minimum number of active connections that can remain idle in the
     * pool, without extra ones being created, or 0 to create none.
     */
    protected int minIdle = 0;//GenericObjectPool.DEFAULT_MIN_IDLE;

    /**
     * Returns the minimum number of idle connections in the pool
     * 
     * @return the minimum number of idle connections
     * @see GenericObjectPool#getMinIdle()
     */
    public synchronized int getMinIdle() {
        return this.minIdle;
    }

    /**
     * Sets the minimum number of idle connections in the pool.
     * 
     * @param minIdle the new value for minIdle
     * @see GenericObjectPool#setMinIdle(int)
     */
    public synchronized void setMinIdle(int minIdle) {
       this.minIdle = minIdle;
    }

    /**
     * The initial number of connections that are created when the pool
     * is started.
     * 
     * @since 1.2
     */
    protected int initialSize = 0;
    
    /**
     * Returns the initial size of the connection pool.
     * 
     * @return the number of connections created when the pool is initialized
     */
    public synchronized int getInitialSize() {
        return this.initialSize;
    }
    
    /**
     * <p>Sets the initial size of the connection pool.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param initialSize the number of connections created when the pool
     * is initialized
     */
    public synchronized void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
        this.restartNeeded = true;
    }

    /**
     * The maximum number of milliseconds that the pool will wait (when there
     * are no available connections) for a connection to be returned before
     * throwing an exception, or <= 0 to wait indefinitely.
     */
    protected long maxWait = 0;// GenericObjectPool.DEFAULT_MAX_WAIT;

    /**
     * <p>Returns the maximum number of milliseconds that the pool will wait
     * for a connection to be returned before throwing an exception.
     * </p>
     * <p>A value less than or equal to zero means the pool is set to wait
     * indefinitely.</p>
     * 
     * @return the maxWait property value
     */
    public synchronized long getMaxWait() {
        return this.maxWait;
    }

    /**
     * <p>Sets the maxWait property.
     * </p>
     * <p>Use -1 to make the pool wait indefinitely.
     * </p>
     * 
     * @param maxWait the new value for maxWait
     * @see #getMaxWait()
     */
    public synchronized void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    /**
     * Prepared statement pooling for this pool. When this property is set to <code>true</code>
     * both PreparedStatements and CallableStatements are pooled.
     */
    protected boolean poolPreparedStatements = false;
    
    /**
     * Returns true if we are pooling statements.
     * 
     * @return true if prepared and callable statements are pooled
     */
    public synchronized boolean isPoolPreparedStatements() {
        return this.poolPreparedStatements;
    }

    /**
     * <p>Sets whether to pool statements or not.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param poolingStatements pooling on or off
     */
    public synchronized void setPoolPreparedStatements(boolean poolingStatements) {
        this.poolPreparedStatements = poolingStatements;
        this.restartNeeded = true;
    }

    /**
     * <p>The maximum number of open statements that can be allocated from
     * the statement pool at the same time, or non-positive for no limit.  Since 
     * a connection usually only uses one or two statements at a time, this is
     * mostly used to help detect resource leaks.</p>
     * 
     * <p>Note: As of version 1.3, CallableStatements (those produced by {@link Connection#prepareCall})
     * are pooled along with PreparedStatements (produced by {@link Connection#prepareStatement})
     * and <code>maxOpenPreparedStatements</code> limits the total number of prepared or callable statements
     * that may be in use at a given time.</p>
     */
    protected int maxOpenPreparedStatements = 0;// GenericKeyedObjectPool.DEFAULT_MAX_TOTAL;

    /**
     * Gets the value of the {@link #maxOpenPreparedStatements} property.
     * 
     * @return the maximum number of open statements
     * @see #maxOpenPreparedStatements
     */
    public synchronized int getMaxOpenPreparedStatements() {
        return this.maxOpenPreparedStatements;
    }

    /** 
     * <p>Sets the value of the {@link #maxOpenPreparedStatements}
     * property.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param maxOpenStatements the new maximum number of prepared statements
     * @see #maxOpenPreparedStatements
     */
    public synchronized void setMaxOpenPreparedStatements(int maxOpenStatements) {
        this.maxOpenPreparedStatements = maxOpenStatements;
        this.restartNeeded = true;
    }

    /**
     * The indication of whether objects will be validated before being
     * borrowed from the pool.  If the object fails to validate, it will be
     * dropped from the pool, and we will attempt to borrow another.
     */
    protected boolean testOnBorrow = true;

    /**
     * Returns the {@link #testOnBorrow} property.
     * 
     * @return true if objects are validated before being borrowed from the
     * pool
     * 
     * @see #testOnBorrow
     */
    public synchronized boolean getTestOnBorrow() {
        return this.testOnBorrow;
    }

    /**
     * Sets the {@link #testOnBorrow} property. This property determines
     * whether or not the pool will validate objects before they are borrowed
     * from the pool. For a <code>true</code> value to have any effect, the 
     * <code>validationQuery</code> property must be set to a non-null string.
     * 
     * @param testOnBorrow new value for testOnBorrow property
     */
    public synchronized void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    /**
     * The indication of whether objects will be validated before being
     * returned to the pool.
     */
    protected boolean testOnReturn = false;

    /**
     * Returns the value of the {@link #testOnReturn} property.
     * 
     * @return true if objects are validated before being returned to the
     * pool
     * @see #testOnReturn
     */
    public synchronized boolean getTestOnReturn() {
        return this.testOnReturn;
    }

    /**
     * Sets the <code>testOnReturn</code> property. This property determines
     * whether or not the pool will validate objects before they are returned
     * to the pool. For a <code>true</code> value to have any effect, the 
     * <code>validationQuery</code> property must be set to a non-null string.
     * 
     * @param testOnReturn new value for testOnReturn property
     */
    public synchronized void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    /**
     * The number of milliseconds to sleep between runs of the idle object
     * evictor thread.  When non-positive, no idle object evictor thread will
     * be run.
     */
    protected long timeBetweenEvictionRunsMillis = 0;
        
    /**
     * Returns the value of the {@link #timeBetweenEvictionRunsMillis}
     * property.
     * 
     * @return the time (in miliseconds) between evictor runs
     * @see #timeBetweenEvictionRunsMillis
     */
    public synchronized long getTimeBetweenEvictionRunsMillis() {
        return this.timeBetweenEvictionRunsMillis;
    }

    /**
     * Sets the {@link #timeBetweenEvictionRunsMillis} property.
     * 
     * @param timeBetweenEvictionRunsMillis the new time between evictor runs
     * @see #timeBetweenEvictionRunsMillis
     */
    public synchronized void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * The number of objects to examine during each run of the idle object
     * evictor thread (if any).
     */
    protected int numTestsPerEvictionRun = 0;
       // GenericObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

    /**
     * Returns the value of the {@link #numTestsPerEvictionRun} property.
     * 
     * @return the number of objects to examine during idle object evictor
     * runs
     * @see #numTestsPerEvictionRun
     */
    public synchronized int getNumTestsPerEvictionRun() {
        return this.numTestsPerEvictionRun;
    }

    /**
     * Sets the value of the {@link #numTestsPerEvictionRun} property.
     * 
     * @param numTestsPerEvictionRun the new {@link #numTestsPerEvictionRun} 
     * value
     * @see #numTestsPerEvictionRun
     */
    public synchronized void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    /**
     * The minimum amount of time an object may sit idle in the pool before it
     * is eligable for eviction by the idle object evictor (if any).
     */
    protected long minEvictableIdleTimeMillis = 0;
        //GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    /**
     * Returns the {@link #minEvictableIdleTimeMillis} property.
     * 
     * @return the value of the {@link #minEvictableIdleTimeMillis} property
     * @see #minEvictableIdleTimeMillis
     */
    public synchronized long getMinEvictableIdleTimeMillis() {
        return this.minEvictableIdleTimeMillis;
    }

    /**
     * Sets the {@link #minEvictableIdleTimeMillis} property.
     * 
     * @param minEvictableIdleTimeMillis the minimum amount of time an object
     * may sit idle in the pool 
     * @see #minEvictableIdleTimeMillis
     */
    public synchronized void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /**
     * The indication of whether objects will be validated by the idle object
     * evictor (if any).  If an object fails to validate, it will be dropped
     * from the pool.
     */
    protected boolean testWhileIdle = false;

    /**
     * Returns the value of the {@link #testWhileIdle} property.
     * 
     * @return true if objects examined by the idle object evictor are
     * validated
     * @see #testWhileIdle
     */
    public synchronized boolean getTestWhileIdle() {
        return this.testWhileIdle;
    }

    /**
     * Sets the <code>testWhileIdle</code> property. This property determines
     * whether or not the idle object evictor will validate connections.  For a
     * <code>true</code> value to have any effect, the 
     * <code>validationQuery</code> property must be set to a non-null string.
     * 
     * @param testWhileIdle new value for testWhileIdle property
     */
    public synchronized void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /**
     * [Read Only] The current number of active connections that have been
     * allocated from this data source.
     * 
     * @return the current number of active connections
     */
    public synchronized int getNumActive() {
            return 0;
    }


    /**
     * [Read Only] The current number of idle connections that are waiting
     * to be allocated from this data source.
     * 
     * @return the current number of idle connections
     */
    public synchronized int getNumIdle() {
            return 0;
    }

    /**
     * The connection password to be passed to our JDBC driver to establish
     * a connection.
     */
    protected volatile String password = null;

    /**
     * Returns the password passed to the JDBC driver to establish connections.
     * 
     * @return the connection password
     */
    public String getPassword() {
        return this.password;
    }

    /** 
     * <p>Sets the {@link #password}.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param password new value for the password
     */
    public void setPassword(String password) {
        this.password = password;
        this.restartNeeded = true;
    }

    /**
     * The connection URL to be passed to our JDBC driver to establish
     * a connection.
     */
    protected String url = null;

    /**
     * Returns the JDBC connection {@link #url} property.
     * 
     * @return the {@link #url} passed to the JDBC driver to establish
     * connections
     */
    public synchronized String getUrl() {
        return this.url;
    }

    /** 
     * <p>Sets the {@link #url}.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param url the new value for the JDBC connection url
     */
    public synchronized void setUrl(String url) {
        this.url = url;
        this.restartNeeded = true;
    }

    /**
     * The connection username to be passed to our JDBC driver to
     * establish a connection.
     */
    protected String username = null;

    /**
     * Returns the JDBC connection {@link #username} property.
     * 
     * @return the {@link #username} passed to the JDBC driver to establish
     * connections
     */
    public String getUsername() {
        return this.username;
    }

    /** 
     * <p>Sets the {@link #username}.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param username the new value for the JDBC connection username
     */
    public void setUsername(String username) {
        this.username = username;
        this.restartNeeded = true;
    }

    /**
     * The SQL query that will be used to validate connections from this pool
     * before returning them to the caller.  If specified, this query
     * <strong>MUST</strong> be an SQL SELECT statement that returns at least
     * one row.
     */
    protected volatile String validationQuery = null;

    /**
     * Returns the validation query used to validate connections before
     * returning them.
     * 
     * @return the SQL validation query
     * @see #validationQuery
     */
    public String getValidationQuery() {
        return this.validationQuery;
    }

    /** 
     * <p>Sets the {@link #validationQuery}.</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param validationQuery the new value for the validation query
     */
    public void setValidationQuery(String validationQuery) {
        if ((validationQuery != null) && (validationQuery.trim().length() > 0)) {
            this.validationQuery = validationQuery;
        } else {
            this.validationQuery = null;
        }
        this.restartNeeded = true;
    }
    
    /**
     * Timeout in seconds before connection validation queries fail. 
     * 
     * @since 1.3
     */
    protected volatile int validationQueryTimeout = -1;
    
    /**
     * Returns the validation query timeout.
     * 
     * @return the timeout in seconds before connection validation queries fail.
     * @since 1.3
     */
    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }
    
    /**
     * Sets the validation query timeout, the amount of time, in seconds, that
     * connection validation will wait for a response from the database when
     * executing a validation query.  Use a value less than or equal to 0 for
     * no timeout.
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param timeout new validation query timeout value in seconds
     * @since 1.3
     */
    public void setValidationQueryTimeout(int timeout) {
        this.validationQueryTimeout = timeout;
        restartNeeded = true;
    }
    
    /**
     * These SQL statements run once after a Connection is created.
     * <p>
     * This property can be used for example to run ALTER SESSION SET
     * NLS_SORT=XCYECH in an Oracle Database only once after connection
     * creation.
     * </p>
     * 
     * @since 1.3
     */
    protected volatile List connectionInitSqls;
	
    /** 
     * Controls access to the underlying connection.
     */
    private boolean accessToUnderlyingConnectionAllowed = false; 

    /**
     * Returns the value of the accessToUnderlyingConnectionAllowed property.
     * 
     * @return true if access to the underlying connection is allowed, false
     * otherwise.
     */
    public synchronized boolean isAccessToUnderlyingConnectionAllowed() {
        return this.accessToUnderlyingConnectionAllowed;
    }

    /**
     * <p>Sets the value of the accessToUnderlyingConnectionAllowed property.
     * It controls if the PoolGuard allows access to the underlying connection.
     * (Default: false)</p>
     * <p>
     * Note: this method currently has no effect once the pool has been
     * initialized.  The pool is initialized the first time one of the
     * following methods is invoked: <code>getConnection, setLogwriter,
     * setLoginTimeout, getLoginTimeout, getLogWriter.</code></p>
     * 
     * @param allow Access to the underlying connection is granted when true.
     */
    public synchronized void setAccessToUnderlyingConnectionAllowed(boolean allow) {
        this.accessToUnderlyingConnectionAllowed = allow;
        this.restartNeeded = true;
    }

    // ----------------------------------------------------- Instance Variables

    // TODO: review & make isRestartNeeded() public, restartNeeded protected

    /**
    
    /**
     * Returns whether or not a restart is needed. 
     *  
     * Note: restart is not currently triggered by property changes.
     * 
     * @return true if a restart is needed
     */
    private boolean isRestartNeeded() {
        return restartNeeded;
    }

    /**
     * The object pool that internally manages our connections.
     */
    
    /**
     * The connection properties that will be sent to our JDBC driver when
     * establishing new connections.  <strong>NOTE</strong> - The "user" and
     * "password" properties will be passed explicitly, so they do not need
     * to be included here.
     */
    protected Properties connectionProperties = new Properties();

    /**
     * The data source we will use to manage connections.  This object should
     * be acquired <strong>ONLY</strong> by calls to the
     * <code>createDataSource()</code> method.
     */
    protected volatile DataSource dataSource = null;

    /**
     * The PrintWriter to which log messages should be directed.
     */
    protected PrintWriter logWriter = new PrintWriter(System.out);


	

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
