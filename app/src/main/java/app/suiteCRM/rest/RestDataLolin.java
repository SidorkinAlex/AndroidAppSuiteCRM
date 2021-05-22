package app.suiteCRM.rest;

public class RestDataLolin {
    public User_Auth user_auth;

    public RestDataLolin(String user_name,String password) {
        this.user_auth = new User_Auth(user_name,password);
    }

    public class User_Auth{
        String user_name;
        String password;

        public User_Auth(String user_name, String password)  {
            this.user_name = user_name;
            this.password = MD5.hash( password );
        }
    }
}
