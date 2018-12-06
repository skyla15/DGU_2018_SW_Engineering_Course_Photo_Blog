package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LikeDAO {
    DBConnect dbConnect = null;
    String sql = "";

    public LikeDAO(){ dbConnect = new DBConnect();}

    // 게시물에 좋아요를 누를 경우 호출하는 메소드
    public int like(int user_id,int post_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql="INSERT INTO insta.like_(user_id, post_id) VALUES(?,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);
            pstmt.executeUpdate();

            check = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    
    //좋아요를 취소할 경우 호출하는 메소드
    public int unlike(int user_id, int post_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql = "DELETE FROM insta.like_ WHERE user_id = ? AND post_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);
            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    //이미 좋아요를 누른 상태인지 체크하는 메소드
    public boolean isLike(int user_id, int post_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        boolean isLike = false;

        try{
            sql = "SELECT * FROM insta.like_ WHERE user_id = ? AND post_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, post_id);

            rs = pstmt.executeQuery();

            if (rs.next()){
            	isLike = true;
            }else{
            	isLike = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return isLike;
    }
}
