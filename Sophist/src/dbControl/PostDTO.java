package dbControl;
import java.util.List;
// PostDAO媛� DB�쓽 post�뀒�씠釉붿쓽 �뜲�씠�꽣瑜� 二쇨퀬諛쏆쓣 �븣 �궗�슜�븯�뒗 媛앹껜
// �떒�닚�엳 �뜲�씠�꽣留뚯쓣 蹂닿��븯硫� �빐�떦 �뜲�씠�꽣�뿉 ���븳 getter setter硫붿냼�뱶留뚯쓣 媛�吏꾨떎.
public class PostDTO {
    // PostDTO媛� 湲곕낯�쟻�쑝濡� 媛�吏��뒗 �뜲�씠�꽣�뱾 // private濡� �꽑�뼵�븯�뿬 罹≪뒓�솕
    private int id;
    private String image;
    private String content;
    private int user_id;
    private int cnt_like;
    private String create_datetime;

    public PostDTO(){

    }

    // PostDTO媛앹껜媛� �씤�뒪�꽩�뒪�솕 �맆 �븣 �떎�쓬怨� 媛숈� �씤�옄�뱾�쓣 �쟾�떖 諛쏆쑝硫� 媛앹껜�쓽 蹂��닔濡� �꽕�젙�빐以��떎.
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
    
    //글 수정시에 링크걸려있는 해쉬태그을 파싱하는 함수
    public String getHashString()
    {
    	String result = "";

    	for(int i=0; i<this.content.length(); i++)
    	{
    		if(this.content.charAt(i) == '<') {
    			while(this.content.charAt(i) != '>') {
    				i++;
    			}
    			i++;
    		}
    		if(!(i > this.content.length()-1))
    			result = (String)(result + this.content.charAt(i));
    	}
      	
    	return result;
    }
}
