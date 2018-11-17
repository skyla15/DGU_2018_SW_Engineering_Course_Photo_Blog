package dbControl;

public class MemberDTO {
    private int id;
    private String nick;
    private String email;
    private String password;
    private String profile_img;
    private String profile_comment;

    public MemberDTO(){

    }

    public MemberDTO(String nick, String email, String password){
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public MemberDTO(int id, String nick, String password, String email, String profile_img, String profile_comment){
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.email = email;
        this.profile_img = profile_img;
        this.profile_comment = profile_comment;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_img() { return profile_img; }

    public void setProfile_img(String profile_img) { this.profile_img = profile_img; }

    public String getProfile_comment() { return profile_comment; }

    public void setProfile_comment(String profile_comment) { this.profile_comment = profile_comment; }

}