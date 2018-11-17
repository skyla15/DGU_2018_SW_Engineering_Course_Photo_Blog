package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HashDAO {
    DBConnect dbconnect = null;
    String sql = "";

    public HashDAO() { dbconnect = new DBConnect(); }

    public int getHashId(String content){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int hashId = -1;

        try {
            sql="SELECT * FROM insta.hashtag WHERE content = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, content);

            rs = pstmt.executeQuery();

            if(rs.next()){
                hashId = rs.getInt("id");
            }else{
                hashId = 0;
            }

        }catch (Exception e){

        }finally {
            DBClose.close(con, pstmt);
        }

        return hashId;
    }

    public int insertHash(String content){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int checkNum = -1;

        try{
            sql="INSERT INTO insta.hashtag(content) VALUES(?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.executeUpdate();

            checkNum = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return checkNum;
    }

    public int makeHashPostRel(int hashId, int postId){
        int checkNum = 0;
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        try{
            sql="INSERT INTO insta.post_hash_rel(post_id, hash_id) VALUES (?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, postId);
            pstmt.setInt(2, hashId);

            pstmt.executeUpdate();
            checkNum = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }

        return checkNum;
    }
}
