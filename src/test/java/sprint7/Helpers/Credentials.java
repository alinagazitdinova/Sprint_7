package sprint7.Helpers;

public class Credentials {
    private String login;
    // ключ link стал полем типа String
    private String password;


    // конструктор со всеми параметрами
    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public static Credentials from(Courier courier){
        return new Credentials(courier.getLogin(), courier.getPassword());
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }



    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    // конструктор без параметров
    public Credentials() {
    }
}
