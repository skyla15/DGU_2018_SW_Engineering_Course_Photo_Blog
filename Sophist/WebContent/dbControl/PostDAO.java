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

    // PostDAO �씤�뒪�꽩�뒪媛� �깮�꽦�맆 �븣 db�뿰寃곗쓣 愿��옣�븯�뒗 DBConnect媛앹껜瑜� �꽑�뼵�븿
    public PostDAO() {
        dbconnect = new DBConnect();
    }

    //湲�紐⑸줉�쓣 PostDTO�씤�뒪�꽩�뒪�뱾�쓽 由ъ뒪�듃 �삎�깭濡� 媛��졇�삤�뒗 硫붿냼�뱶
    public List<PostDTO> getTotalList() throws Exception{

        // DB�� �뿰寃곗쓣 �븯怨�, 硫붿냼�뱶 �궡�뿉�꽌 �궗�슜�맆 蹂��닔�뱾�쓣 �꽑�뼵�빐以�
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            // �씠�븯 �궗�슜踰뺤씠 嫄곗쓽 �룞�씪�븯怨� �젙�삎�솕�맖
            // post �뀒�씠釉붿뿉�꽌 紐⑤뱺 �룷�뒪�똿�쓣 遺덈윭�삤�뒗 荑쇰━
            sql = "SELECT * FROM insta.post ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            // 寃곌낵媛믪씠 �엳�쓣 �븣
            if(rs.next()){
                // PostDTO �씤�뒪�꽩�뒪�뱾�쓣 �떞�뒗 �뼱�젅�씠由ъ뒪�듃�씤 list瑜� �꽑�뼵
                list = new ArrayList<PostDTO>();

                // PostDTO�쓽 �씤�뒪�꽩�뒪�씤 post瑜� �깮�꽦�븯怨� �뜲�씠�꽣瑜� 吏묒뼱�꽔�� �뮘 list�뿉 異붽��븿
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

        // �젙�긽 �떎�뻾�릺硫� list(PostDTO�쓽 �씤�뒪�꽩�뒪濡� 援ъ꽦�맂 �뼱�젅�씠由ъ뒪�듃)瑜� 諛섑솚�븿
        return list;
    }

    //媛쒕퀎 �룷�뒪�똿�쓣 PostDTO �씤�뒪�꽩�뒪 �삎�깭濡� 由ы꽩�븯�뒗 硫붿냼�뱶(�뼱�젅�씠由ъ뒪�듃 �궗�슜 X, �븯�굹�쓽 �룷�뒪�듃 利� �븯�굹�쓽 媛앹껜留� 遺덈윭�삤湲� �븣臾�)
    public PostDTO getSinglePost(int postId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 由ъ뒪�듃媛� �븘�땶 �떒�씪 媛앹껜留� 由ы꽩�븯硫� �릺湲곕븣臾몄뿉 PostDTO�쓽 �씤�뒪�꽩�뒪�씤 post瑜� �꽑�뼵�빐以��떎.
        PostDTO post = null;

        try {
            // �씤�옄濡� �쟾�떖諛쏆� id�� �씪移섑븯�뒗 �룷�뒪�똿�쓣 遺덈윭�삤�뒗 sql臾�
            sql = "SELECT * FROM insta.post WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            // pstmt�뿉 sql臾몄쓣 �떞�쑝硫� �븘�옒��媛숈씠 蹂��닔瑜� �븷�떦�빐以� �닔 �엳�떎
            // �븘�옒 援щЦ�쓽 寃쎌슦 1踰� 臾쇱쓬�몴�뿉 postID�씪�뒗 蹂��닔瑜� 吏묒뼱�꽔�뒗 寃�, postId�뒗 �씠 硫붿냼�뱶�뿉 湲곕낯�쟻�쑝濡� �쟾�떖�릺�뒗 �뙆�씪誘명꽣
            pstmt.setInt(1, postId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                // 由ъ뒪�듃媛� �븘�땶 �떒�씪 �씤�뒪�꽩�뒪瑜� �깮�꽦�븯怨� 媛믩뱾�쓣 吏묒뼱�꽔�쓬
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
        // post�씪怨� 紐낅챸�븳 postDTO �씤�뒪�꽩�뒪瑜� 諛섑솚�븿
        return post;
    }

    // �슂泥� �쑀��諛쏆� �쑀���쓽 寃뚯떆臾쇱쓣 PostDTO �씤�뒪�꽩�뒪�뱾�쓽 由ъ뒪�듃濡� 諛섑솚�븯�뒗 硫붿냼�뱶
    // 理쒖긽�떒�뿉 �엳�뒗 getTotalList�� sql臾몄쓣 �젣�쇅�븯硫� �룞�씪�븿
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

    //�뙏濡쒖썙�쓽 寃뚯떆臾쇰쭔 遺덈윭�삤湲�
    //�뿭�떆 sql臾몄쓣 �젣�쇅�븯怨� �룞�씪�븿 // �떎�냼 蹂듭옟�븳 sql臾몄씠 �궗�슜�맖
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

    //�빐�떆�깭洹몄뿉 �빐�떦�븯�뒗 寃뚯떆臾쇰쭔 遺덈윭�삤湲�
    // 諛붾줈 �쐞�쓽 硫붿냼�뱶�� 嫄곗쓽 媛숈쑝硫� sql臾몃쭔 �떎瑜대떎
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

    //湲� �엯�젰�븯湲�
    //�쐞�뿉 �엳�뜕 硫붿냼�뱶���뒗 �떎瑜닿쾶 DB�뿉 �엯�젰�븯�뒗 硫붿냼�뱶, DTO�씤�뒪�꽩�뒪媛� �븘�땶 �떒�닚 �뜲�씠�꽣瑜� �씤�옄濡� 諛쏆븘�� 湲��쓣 �옉�꽦�븳�떎.
    public int insertPost(String image, String content, int userId) throws Exception {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        // 吏�湲덇퉴吏����뒗 �떎瑜닿쾶 rs瑜� �꽑�뼵�븯吏� �븡�뒗�떎. 寃곌낵媛믪씠 �븘�슂�뾾湲� �븣臾�

        // db�뿉 �삤瑜섏뾾�씠 �엯�젰�씠 �릺�뿀�뒗吏� 泥댄겕�븯�뒗 integer, �씠 蹂��닔瑜� 由ы꽩�븯�뿬 jsp�떒�뿉 �삤瑜� �뿬遺�瑜� �븣�젮以��떎.
        int check = 0;

        try {
            sql = "INSERT INTO insta.post(IMAGE, CONTENT, USER_ID) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);

            // 臾쇱쓬�몴 �궗�슜踰뺤� �긽�떒怨� �룞�씪
            pstmt.setString(1, image);
            pstmt.setString(2, content);
            pstmt.setInt(3, userId);

            pstmt.executeUpdate();

            // 湲� �옉�꽦怨� �룞�떆�뿉 �빐�떆�깭洹몃�� DB�뿉 吏묒뼱�꽔�뒗 硫붿냼�뱶瑜� �샇異쒗븿
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



    // �궗�슜�옄媛� �옉�꽦�븳 湲��쓽 �궡�슜留� 諛쏆븘�� �빐�떆�깭洹몃�� �븯�씠�띁留곹겕濡� 諛붽퓭二쇰뒗 硫붿냼�뱶瑜� �샇異쒗븯怨�,
    // �빐�떆�깭洹몄쓽 Id�� �궗�슜�옄媛� �옉�꽦�븳 �룷�뒪�듃 id�쓽 愿�怨꾨�� �꽕�젙 > 蹂듭옟�븳 硫붿냼�뱶�뱾 吏묓빀
    public void insertHashTag(String content){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        // �빐�떆�깭洹몃�� �깮�꽦�븯湲� �쐞�빐 HashTagGenerator�겢�옒�뒪瑜� hash�씪�뒗 �씠由꾩쑝濡� �깮�꽦�븳�떎
        HashTagGenerator hash = new HashTagGenerator();
        String url = "/Sophist/hashPostView.jsp?hash=";
        ResultSet rs = null;

        try{
            // �씠誘� �엯�젰�맂 �룷�뒪�똿�뿉�꽌 �궡�슜�씠 媛숈� �룷�뒪�똿�쓽id�� �궡�슜�쓣 遺덈윭�삩�떎(鍮꾪슚�쑉�쟻�씠硫� 由ы뙥�넗留곸씠 �븘�슂)
            sql = "SELECT id, content FROM insta.post WHERE content = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, content);

            rs = pstmt.executeQuery();

            if(rs.next()){
                // 遺덈윭�삩 �룷�뒪�똿�쓽 �궡�슜遺�遺꾩쓣 �깉濡� 諛붽퓭二쇰뒗 遺�遺�
                String oldContent = rs.getString("content");
                int postId = rs.getInt("id");
                // �썝�옒 �엯�젰諛쏆븯�뜕 �궡�슜�쓣 HashTagGenerator�겢�옒�뒪�쓽 setAutoLinkHashTag硫붿냼�뱶瑜� �씠�슜�빐
                // �궡�슜 �븞�뿉 �엳�뒗 �빐�떆�깭洹몃�� �씤�떇�빐 �븯�씠�띁留곹겕�삎�깭濡� 諛붽퓭以��떎.
                String newContent = hash.setAutoLinkHashTag(url, oldContent, postId);

                // �깉濡� 諛붾�� �궡�슜遺�遺꾩쓣 �닔�젙�븯�뿬 DB�뿉 �떎�떆 �엯�젰
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

    //寃뚯떆臾� �궘�젣
    public int deletePost(int post_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            // 寃뚯떆臾쇱씠 �궘�젣�맆 寃쎌슦 肄붾찘�듃�� �빐�떆�깭洹멸컙�쓽 愿�怨꾨룄 �궘�젣�븯湲� �븣臾몄뿉 珥� 3踰덉쓽 sql臾몄씠 �떎�뻾�맂�떎.
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
    
    

}
