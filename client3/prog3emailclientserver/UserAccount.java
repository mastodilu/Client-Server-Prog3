package prog3emailclientserver;

/**
 *
 * @author Di Lucchio Matteo, matteo.dilucchio@edu.unito.it
 */
public class UserAccount {
    private String address, name, surname, nickname;
    
    public UserAccount()
    {
        address = "";
        name = "";
        surname = "";
        nickname = "";
    }
    
    public UserAccount(String a, String n, String s, String ni)
    {
        address = a;
        name = n;
        surname = s;
        nickname = ni;
    }
    
    //SETTERS & GETTERS

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
