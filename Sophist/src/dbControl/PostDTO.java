package dbControl;

// PostDAO가 DB의 post테이블의 데이터를 주고받을 때 사용하는 객체
// 단순히 데이터만을 보관하며 해당 데이터에 대한 getter setter메소드만을 가진다.
public class PostDTO {
    // PostDTO가 기본적으로 가지는 데이터들 // private로 선언하여 캡슐화
    private int id;
    private String image;
    private String content;
    private int user_id;
    private int cnt_like;
    private String create_datetime;

    public PostDTO(){

    }

    // PostDTO객체가 인스턴스화 될 때 다음과 같은 인자들을 전달 받으면 객체의 변수로 설정해준다.
    public PostDTO(int id, String image, String content,
                   int user_id, int cnt_like, String create_datetime){
        this.id = id;
        this.image = image;
        this.content = content;
        this.user_id = user_id;
        this.cnt_like = cnt_like;
        this.create_datetime = create_datetime;
    }

    // Getter Setter Methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCnt_like() {
        return cnt_like;
    }

    public void setCnt_like(int cnt_like) {
        this.cnt_like = cnt_like;
    }

    public String getCreate_datetime() {
        return create_datetime;
    }

    public void setCreate_datetime(String create_datetime) {
        this.create_datetime = create_datetime;
    }
}
