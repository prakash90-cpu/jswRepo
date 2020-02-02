package jsw.report.service;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

/*import org.forgerock.opendj.ldap.AuthenticationException;
import org.forgerock.opendj.ldap.Connection;
import org.forgerock.opendj.ldap.LDAPConnectionFactory;
import org.forgerock.opendj.ldap.SearchScope;
import org.forgerock.opendj.ldap.responses.SearchResultEntry;
import org.forgerock.opendj.ldap.responses.SearchResultReference;
import org.forgerock.opendj.ldif.ConnectionEntryReader;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




/**
 * This class is used for LDAP integration
 * 
 * @author nishewal
 * @version 1.0
 * 
 */
public class LDAPServiceImpl implements LDAPService {
	// TODO Need to get this from constant file and sys parm table
	@Autowired
		private static   String LDAP_HOST_NAME;
	@Autowired
		private static   String LDAP_PORT;
	@Autowired
		private static String baseDN;
	@Autowired
		private static String LDAP_ENV;
	static DirContext ldapContext;
		//private static volatile LDAPConnectionFactory factory;

		private final static Logger LOGGER = LoggerFactory
				.getLogger(LDAPServiceImpl.class);
		
		

		public static String getLDAP_HOST_NAME() {
			return LDAP_HOST_NAME;
		}

		public static void setLDAP_HOST_NAME(String lDAP_HOST_NAME) {
			LDAP_HOST_NAME = lDAP_HOST_NAME;
		}

		public static String getLDAP_PORT() {
			return LDAP_PORT;
		}

		public static void setLDAP_PORT(String lDAP_PORT) {
			LDAP_PORT = lDAP_PORT;
		}

		public static String getBaseDN() {
			return baseDN;
		}

		public static void setBaseDN(String baseDN) {
			LDAPServiceImpl.baseDN = baseDN;
		}

		public static String getLDAP_ENV() {
			return LDAP_ENV;
		}

		public static void setLDAP_ENV(String lDAP_ENV) {
			LDAP_ENV = lDAP_ENV;
		}

		
		
		
		public boolean authenticate(String userName, String password)
				throws Exception {
			
			
				String str1 = LDAP_HOST_NAME+":"+LDAP_PORT; 
			    String str2 = baseDN; 
			    String str3 = userName; // empid
			    String str4 = password; // user password
			    
			    try
			    {
			     
			      
			      Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			      ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			      
			      ldapEnv.put("java.naming.provider.url", "LDAP://" + str1);
			      ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");      
			      ldapEnv.put(Context.SECURITY_PRINCIPAL,"CN="+str3+","+str2);//CN=ceadmin,CN=Users,DC=jsw,DC=com");
			      ldapEnv.put(Context.SECURITY_CREDENTIALS, str4);
			   
			      
			      ldapContext = new InitialDirContext(ldapEnv);

			      
			     
			    }
			    catch (NamingException localNamingException)
			    {
			    	LOGGER.debug("Error authenticating user:::::::::::::",localNamingException);
			      return false;
			    }
			    LOGGER.debug("OK, successfully authenticating user");
			  
			return true;
			}
			
			
		//} // Method End

		public static void main(String args[]) throws Exception {
			LDAPServiceImpl ldap = new LDAPServiceImpl();

			

		}

		
		public void init(final String hostname, final String port,
				final String baseDN,final String enviornmnt) throws Exception {
			//Sonar Fix : Dodgy - Write to static field from instance method
			/*LDAPServiceImpl.LDAP_HOST_NAME = hostname;
			LDAPServiceImpl.LDAP_PORT = port;
			LDAPServiceImpl.baseDN = baseDN;
			LDAPServiceImpl.LDAP_ENV=enviornmnt;*/
			//getInitialize(hostname,port,baseDN,enviornmnt);
		}

		
		public void destroy() throws Exception {
			//Sonar Fix : Dodgy - Write to static field from instance method
			/*LDAPServiceImpl.LDAP_HOST_NAME = null;
			LDAPServiceImpl.LDAP_PORT = null;
			LDAPServiceImpl.baseDN = null;
			LDAPServiceImpl.factory = null;*/
			//getdestroy();

		}
		
		
	
		
		
		
	

	}

