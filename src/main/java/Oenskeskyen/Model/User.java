package Oenskeskyen.Model;

public class User {

    private String fullName;
    private String mail;
    private String passWord;
    private int id;

    public User (){
    }

    public User (String fullName, String mail, String passWord){
        this.fullName = fullName;
        this.mail = mail;
        this.passWord = passWord;
    }

    public User (int id, String fullName, String mail, String passWord){
        this.id = id;
        this.fullName = fullName;
        this.mail = mail;
        this.passWord = passWord;
    }

    //************************* Setter ang getters *******************************//

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getId(){
        return id;
    }

    public void setUserId(int id){
        this.id = id;
    }
}
