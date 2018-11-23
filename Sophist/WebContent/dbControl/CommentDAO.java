package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CommentDAO {
    DBConnect dbconnect = null;
    String sql = "";

    public CommentDAO() {
        dbconnect = new DBConnect();
    }

    //�뙎湲� �쟾泥� 紐⑸줉 媛��졇�삤湲�
    public List<CommentDTO> getList(int post_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CommentDTO>list = null;

        try{
            sql = "SELECT * FROM insta.comment WHERE post_id= ? ORDER BY id ASC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, post_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<CommentDTO>();

                do{
                    CommentDTO comment = new CommentDTO();
                    comment.setId(rs.getInt("id"));
                    comment.setContent(rs.getString("content"));
                    comment.setUser_id(rs.getInt("user_id"));
                    comment.setPost_id(rs.getInt("post_id"));
                    list.add(comment);
                }while(rs.next());
            }else {
                list = Collections.EMPTY_LIST;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return list;
    }

    //�뙎湲� �엯�젰�븯湲�
    public int insertComment(int user_id, int post_id, String content){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try {
            sql = "INSERT INTO insta.comment(USER_ID, POST_ID, CONTENT) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);
            pstmt.setString(3, content);

            pstmt.executeUpdate();
            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    
    public int deleteComment(int comment_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            sql="DELETE FROM insta.comment WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, comment_id);

            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
}
