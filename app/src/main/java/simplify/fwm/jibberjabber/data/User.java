package simplify.fwm.jibberjabber.data;

/**
 * Created by fredericmurry on 1/14/16.
 */
public class User {

    private String firstName;
    private String lastName;
    private String displayName;

    public User(){

    }

    public User(String firstName, String lastName,String userName){
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = userName;

    }

    //          Accessors
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }


    public String getUserName(){
        return displayName;
    }


    //         Mutators
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setUserName(String userName){
        this.displayName = userName;
    }
}
