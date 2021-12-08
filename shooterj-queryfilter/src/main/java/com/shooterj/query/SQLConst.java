package com.shooterj.query;

/**
 * 常用数据库变量
 */
public interface SQLConst {

	//数据库类型
	String DB_ORACLE = "oracle";
	String DB_MYSQL = "mysql";
	String DB_SQLSERVER = "mssql";
	String DB_SQLSERVER2008 = "mssql2008";
	String DB_DB2 = "db2";
	String DB_DERBY = "derby";
	String DB_HBASE = "hbase";
	String DB_HIVE = "hive";
	String DB_H2 = "h2";
	String DB_JTDS = "jtds";
	String DB_MOCK = "mock";
	String DB_HSQL = "hsql";
	String DB_POSTGRESQL = "postgresql";
	String DB_SYBASE = "sybase";
	String DB_SQLITE = "sqlite";
	String DB_MCKOI = "mckoi";
	String DB_CLOUDSCAPE = "cloudscape";
	String DB_INFORMIX = "informix";
	String DB_TIMESTEN = "timesten";
	String DB_AS400 = "as400";
	String DB_SAPDB = "sapdb";
	String DB_JSQLCONNECT = "JSQLConnect";
	String DB_JTURBO = "JTurbo";
	String DB_FIREBIRDSQL = "firebirdsql";
	String DB_INTERBASE = "interbase";
	String DB_POINTBASE = "pointbase";
	String DB_EDBC = "edbc";
	String DB_MIMER = "mimer";
	String DB_DM = "dm";
	String DB_INGRES = "ingres";


	/*** 自定义表的主键(ID)*/
	String PK_COLUMN_NAME = "ID";
	/**
	 * 自定义表的外键(REFID)
	 */
	String FK_COLUMN_NAME = "REFID";
	/**
	 * 自定义字段的字段前缀(F_)
	 */
	String CUSTOMER_COLUMN_PREFIX = "F_";
	/**
	 * 自定义表的表前缀(W_)
	 */
	String CUSTOMER_TABLE_PREFIX = "W_";
	/**
	 * 自定义表的索引前缀(IDX_)
	 */
	String CUSTOMER_INDEX_PREFIX = "IDX_";

	/**
	 * 历史业务数据表名的后缀(_HISTORY)
	 */
	String CUSTOMER_TABLE_HIS_SUFFIX = "_HISTORY";

	/**
	 * 新添加的表通用前缀 (TT_)
	 */
	String CUSTOMER_TABLE_COMM_PREFIX = "TT_";

	/**
	 * 在主表表中默认添加用户字段。(curentUserId_)
	 */
	String CUSTOMER_COLUMN_CURRENTUSERID = "curentUserId_";
	/**
	 * 在主表和从表 表中默认添加组织字段。
	 */
	String CUSTOMER_COLUMN_CURRENTORGID = "curentOrgId_";
	/**
	 * 流程运行ID
	 */
	String CUSTOMER_COLUMN_FLOWRUNID = "flowRunId_";
	/**
	 * 流程定义ID
	 */
	String CUSTOMER_COLUMN_DEFID = "defId_";

	/**
	 * 默认创建时间
	 */
	String CUSTOMER_COLUMN_CREATETIME = "CREATETIME";

}
