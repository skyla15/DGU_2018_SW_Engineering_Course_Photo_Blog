package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FollowDAO {
    DBConnect dbConnect = null;
    String sql = "";

    public FollowDAO(){ dbConnect = new DBConnect();}

    //�뙏濡쒖슦 湲곕뒫 援ы쁽
    public int follow(int user_id,int following_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql="INSERT INTO insta.follow(user_id, following_id) VALUES(?,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, user_id);
            pstmt.setInt(2, following_id);
            pstmt.executeUpdate();

            check = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }

    //�뼵�뙏濡쒖슦 湲곕뒫 援ы쁽
    public int unfollow(int user_id, int following_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql = "DELETE FROM insta.follow WHERE user_id = ? AND following_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, following_id);
            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }

    //�뙏濡쒖엵 �뿬遺� �솗�씤 湲곕뒫 援ы쁽
    public boolean isFollwing(int user_id, int following_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        boolean isFollowing = false;

        try{
            sql = "SELECT * FROM insta.follow WHERE user_id = ? AND following_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, following_id);

            rs = pstmt.executeQuery();

            if (rs.next()){
                isFollowing = true;
            }else{
                isFollowing = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return isFollowing;
    }
    
    
    
}
