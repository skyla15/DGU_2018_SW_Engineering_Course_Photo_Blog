package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HashDAO {
    DBConnect dbconnect = null;
    String sql = "";

    public HashDAO() { dbconnect = new DBConnect(); }

    //게시물의 내용에서 해쉬태그를 추출하는 메소드
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

    //해쉬태그를 저장하는 메소드
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
    //게시물과 해쉬태그의 관계를 저장하는 메소드
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
    //하나의 게시물과 연관된 해시태그 연결관계를 삭제하는 메소드
    public int deleteHashRel(int postId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            sql="DELETE FROM insta.post_hash_rel WHERE post_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, postId);

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
