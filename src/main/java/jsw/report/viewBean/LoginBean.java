package jsw.report.viewBean;

public class LoginBean
{
		private String username;

		private String password;
		private int id=0;

		public String getPassword()
		{
				return this.password;
		}

		public String getUsername()
		{
				return this.username;
		}

		public void setUsername(String username)
		{
				this.username = username;
		}

		public void setPassword(String password)
		{
				this.password = password;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}


}
