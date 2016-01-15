package simplify.fwm.jibberjabber.data;

/**
 * Created by fredericmurry on 1/14/16.
 */
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userName;

    public User(){

    }

    public User(String firstName, String lastName, String email, String userName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userName = userName;

    }

    //          Accessors
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
}
