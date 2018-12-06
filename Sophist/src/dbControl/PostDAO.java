package dbControl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import dbControl.PostDTO;

public class PostDAO {
    DBConnect dbconnect = null;
    String sql = "";


    public PostDAO() {
        dbconnect = new DBConnect();
    }

    //전체 게시물을 반환하는 메소드
    public List<PostDTO> getTotalList() throws Exception{

       
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            sql = "SELECT * FROM insta.post ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            
            if(rs.next()){
                
                list = new ArrayList<PostDTO>();

                // 각각의 게시물 정보를 리스트에 삽입하기전에 DTO에 맞는 형식으로 값을 저장한다.
                do{
                    PostDTO post = new PostDTO();
                    post.setId(rs.getInt("id"));
                    post.setImage(rs.getString("image"));
                    post.setContent(rs.getString("content"));
                    post.setUser_id(rs.getInt("user_id"));
                    post.setCnt_like(rs.getInt("cnt_like"));
                    list.add(post);
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

    //게시물 아이디로 게시물을 조회하는 메소드
    public PostDTO getSinglePost(int postId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

                PostDTO post = null;

        try {
            
            sql = "SELECT * FROM insta.post WHERE id = ?";
            pstmt = con.prepareStatement(sql);

                        pstmt.setInt(1, postId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                post = new PostDTO();
                post.setId(rs.getInt("id"));
                post.setImage(rs.getString("image"));
                post.setContent(rs.getString("content"));
                post.setUser_id(rs.getInt("user_id"));
                post.setCnt_like(rs.getInt("cnt_like"));
            }else{
                post = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        
        return post;
    }


    public List<PostDTO> getList(int user_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            sql = "SELECT * FROM insta.post WHERE user_id = ? ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<PostDTO>();

                do{
                    PostDTO post = new PostDTO();
                    post.setId(rs.getInt("id"));
                    post.setImage(rs.getString("image"));
                    post.setContent(rs.getString("content"));
                    post.setUser_id(rs.getInt("user_id"));
                    post.setCnt_like(rs.getInt("cnt_like"));
                    list.add(post);
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
    public List<PostDTO> getList(String where, String is) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            sql = "SELECT * FROM insta.post WHERE "+where+" LIKE ? ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%"+is+"%");

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<PostDTO>();

                do{
                    PostDTO post = new PostDTO();
                    post.setId(rs.getInt("id"));
                    post.setImage(rs.getString("image"));
                    post.setContent(rs.getString("content"));
                    post.setUser_id(rs.getInt("user_id"));
                    post.setCnt_like(rs.getInt("cnt_like"));
                    list.add(post);
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
    
    public int getListCount(int user_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int PostNumber = 0;

        try{
            sql = "SELECT count(*) count FROM insta.post WHERE user_id = ? ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
            	PostNumber = rs.getInt("count");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return PostNumber;
    }


    public List<PostDTO> getFollwersPost(int user_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            // �룷�뒪�듃 �뀒�씠釉붿뿉�꽌 �듅�젙 user_id(�쇅�옒�궎)瑜� 媛�吏� �쑀��媛� �옉�꽦�븳 �룷�뒪�똿怨� �옄湲곗옄�떊�씠 �옉�꽦�븳 �룷�뒪�똿留� 媛��졇�삤�뒗 sql臾�
            // where議곌굔臾몄뿉�꽌 IN�쓣 �씠�슜�빐 �떎瑜� 荑쇰━瑜� 以묒꺽�쑝濡� �쟻�슜�븯�뿬, �듅�젙 議곌굔�뿉 留욌뒗 �룷�뒪�똿留뚯쓣 寃��깋�븿
            sql = "SELECT * FROM insta.post " +
                    "WHERE user_id IN (SELECT following_id FROM insta.follow WHERE user_id = ?)" +
                    "OR (user_id = ?)" +
                    "ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            // 1踰덈Ъ�쓬�몴�뿉 user_id 2踰� 臾쇱쓬�몴�뿉 user_id瑜� 吏묒뼱�꽔�쓬
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, user_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<PostDTO>();

                do{
                    PostDTO post = new PostDTO();
                    post.setId(rs.getInt("id"));
                    post.setImage(rs.getString("image"));
                    post.setContent(rs.getString("content"));
                    post.setUser_id(rs.getInt("user_id"));
                    post.setCnt_like(rs.getInt("cnt_like"));
                    list.add(post);
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


    public List<PostDTO> getHahtagPost(String hash) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        HashDAO hashDao = new HashDAO();
        int hashId = hashDao.getHashId(hash);

        try{
            sql = "SELECT * FROM insta.post " +
                    "WHERE id in (SELECT post_id FROM insta.post_hash_rel WHERE hash_id = ?)" +
                    "ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, hashId);

            rs = pstmt.executeQuery();

            if(rs.next()){
                list = new ArrayList<PostDTO>();

                do{
                    PostDTO post = new PostDTO();
                    post.setId(rs.getInt("id"));
                    post.setImage(rs.getString("image"));
                    post.setContent(rs.getString("content"));
                    post.setUser_id(rs.getInt("user_id"));
                    post.setCnt_like(rs.getInt("cnt_like"));
                    list.add(post);
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


    public int insertPost(String image, String content, int userId) throws Exception {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        int check = 0;

        try {
            sql = "INSERT INTO insta.post(IMAGE, CONTENT, USER_ID) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);


            pstmt.setString(1, image);
            pstmt.setString(2, content);
            pstmt.setInt(3, userId);

            pstmt.executeUpdate();


            insertHashTag(content);

            check = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    
    public int updatePost(int postId,String image, String content, int userId) throws Exception {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        int check = 0;

        try {
            sql = "UPDATE insta.post SET user_id=?, content=?, image=? WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, userId);
            pstmt.setString(2, content);
            pstmt.setString(3, image);
            pstmt.setInt(4, postId);

            pstmt.executeUpdate();

            insertHashTag(content);

            check = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }




    public void insertHashTag(String content){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;


        HashTagGenerator hash = new HashTagGenerator();
        String url = "/Sophist/hashPostView.jsp?hash=";
        ResultSet rs = null;

        try{

            sql = "SELECT id, content FROM insta.post WHERE content = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, content);

            rs = pstmt.executeQuery();

            if(rs.next()){

                String oldContent = rs.getString("content");
                int postId = rs.getInt("id");

                String newContent = hash.setAutoLinkHashTag(url, oldContent, postId);
                sql = "UPDATE insta.post SET content = ? WHERE id = ?";
                pstmt = con.prepareStatement(sql);

                pstmt.setString(1, newContent);
                pstmt.setInt(2, postId);

                pstmt.executeUpdate();
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
    }


    public int deletePost(int post_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{

            sql="DELETE FROM insta.post WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, post_id);

            pstmt.executeUpdate();

            sql="DELETE FROM insta.post_hash_rel WHERE post_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, post_id);

            pstmt.executeUpdate();

            sql="DELETE FROM insta.comment WHERE post_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, post_id);

            pstmt.executeUpdate();

            check = 1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    
    public int getLikeCount(int post_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int likeCount = 0;

        try{
            sql = "SELECT cnt_like FROM insta.post WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, post_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
            	likeCount = rs.getInt("cnt_like");
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return likeCount;
    }
    
    public int getReportCount(int post_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int reportCount = 0;

        try{
            sql = "SELECT cnt_report FROM insta.post WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, post_id);

            rs = pstmt.executeQuery();

            if(rs.next()){
            	reportCount = rs.getInt("cnt_report");
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con,pstmt);
        }
        return reportCount;
    }
    
    public int updateLike(int likeCount, int postid) {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql="UPDATE insta.post SET cnt_like=? WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, likeCount);
            pstmt.setInt(2, postid);
            pstmt.executeUpdate();

            check = 1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }
    
    public int updateReport(int reportCount, int postid) {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        int check = -1;

        try{
            sql="UPDATE insta.post SET cnt_report=? WHERE id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reportCount);
            pstmt.setInt(2, postid);
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
