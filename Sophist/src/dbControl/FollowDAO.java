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

    //팔로우 연결관계를 저장하는 메소드
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

    //팔로우 연결관계를 끊는 메소드
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

    //팔로우 연결관계가 있는지 조회하는 메소드
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
    //한 유저의 팔로우 정보를 삭제하는 메소드
    public int deleteAllFollowingInfo(int user_id) throws Exception{
        Connection con = dbConnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql = "DELETE FROM insta.follow WHERE user_id = ? OR following_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, user_id);
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
