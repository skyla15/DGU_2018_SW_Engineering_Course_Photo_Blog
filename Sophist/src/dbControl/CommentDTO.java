package dbControl;

public class CommentDTO {
    private int id;
    private int user_id;
    private int post_id;
    private String content;

    public CommentDTO(){

    }

    public CommentDTO(int id, int user_id, int post_id, String content){
        this.id = id;
        this.user_id = user_id;
        this.post_id = post_id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
