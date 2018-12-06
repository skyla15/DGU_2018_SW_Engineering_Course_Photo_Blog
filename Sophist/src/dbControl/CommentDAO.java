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

    //게시물 id로 게시물에 달린 댓글을 가져오는 메소드
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

    //게시물에 댓글을 다는 메소드
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
    
    //댓글 아이디로 조회하여 댓글을 삭제하는 메소드
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
    
    //userid로 댓글을 조회하여 삭제하는 메소드
    public int deleteCommentUsedUserId(int userId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            sql="DELETE FROM insta.comment WHERE user_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);

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
