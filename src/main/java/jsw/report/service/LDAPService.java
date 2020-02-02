package jsw.report.service;



public interface LDAPService {

	boolean authenticate(final String userName, final String password)
			throws Exception;

	
	/**
	 * @param hostname
	 * @param port
	 * @param baseDN
	 * @throws CGSystemException
	 */
	void init(final String hostname, final String port, final String baseDN,final String env)
			throws Exception;
	
	/**
	 * Destroying The Properties and closing the connection
	 * @throws CGSystemException
	 */
	void destroy()
			throws Exception;
}
