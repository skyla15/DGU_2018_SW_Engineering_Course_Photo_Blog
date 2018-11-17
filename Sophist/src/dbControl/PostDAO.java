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

    // PostDAO 인스턴스가 생성될 때 db연결을 관장하는 DBConnect객체를 선언함
    public PostDAO() {
        dbconnect = new DBConnect();
    }

    //글목록을 PostDTO인스턴스들의 리스트 형태로 가져오는 메소드
    public List<PostDTO> getTotalList() throws Exception{

        // DB와 연결을 하고, 메소드 내에서 사용될 변수들을 선언해줌
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            // 이하 사용법이 거의 동일하고 정형화됨
            // post 테이블에서 모든 포스팅을 불러오는 쿼리
            sql = "SELECT * FROM insta.post ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            // 결과값이 있을 때
            if(rs.next()){
                // PostDTO 인스턴스들을 담는 어레이리스트인 list를 선언
                list = new ArrayList<PostDTO>();

                // PostDTO의 인스턴스인 post를 생성하고 데이터를 집어넣은 뒤 list에 추가함
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

        // 정상 실행되면 list(PostDTO의 인스턴스로 구성된 어레이리스트)를 반환함
        return list;
    }

    //개별 포스팅을 PostDTO 인스턴스 형태로 리턴하는 메소드(어레이리스트 사용 X, 하나의 포스트 즉 하나의 객체만 불러오기 때문)
    public PostDTO getSinglePost(int postId) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // 리스트가 아닌 단일 객체만 리턴하면 되기때문에 PostDTO의 인스턴스인 post를 선언해준다.
        PostDTO post = null;

        try {
            // 인자로 전달받은 id와 일치하는 포스팅을 불러오는 sql문
            sql = "SELECT * FROM insta.post WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            // pstmt에 sql문을 담으면 아래와같이 변수를 할당해줄 수 있다
            // 아래 구문의 경우 1번 물음표에 postID라는 변수를 집어넣는 것, postId는 이 메소드에 기본적으로 전달되는 파라미터
            pstmt.setInt(1, postId);
            rs = pstmt.executeQuery();

            if(rs.next()){
                // 리스트가 아닌 단일 인스턴스를 생성하고 값들을 집어넣음
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
        // post라고 명명한 postDTO 인스턴스를 반환함
        return post;
    }

    // 요청 유저받은 유저의 게시물을 PostDTO 인스턴스들의 리스트로 반환하는 메소드
    // 최상단에 있는 getTotalList와 sql문을 제외하면 동일함
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

    //팔로워의 게시물만 불러오기
    //역시 sql문을 제외하고 동일함 // 다소 복잡한 sql문이 사용됨
    public List<PostDTO> getFollwersPost(int user_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<PostDTO>list = null;

        try{
            // 포스트 테이블에서 특정 user_id(외래키)를 가진 유저가 작성한 포스팅과 자기자신이 작성한 포스팅만 가져오는 sql문
            // where조건문에서 IN을 이용해 다른 쿼리를 중첩으로 적용하여, 특정 조건에 맞는 포스팅만을 검색함
            sql = "SELECT * FROM insta.post " +
                    "WHERE user_id IN (SELECT following_id FROM insta.follow WHERE user_id = ?)" +
                    "OR (user_id = ?)" +
                    "ORDER BY id DESC";
            pstmt = con.prepareStatement(sql);
            // 1번물음표에 user_id 2번 물음표에 user_id를 집어넣음
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

    //해시태그에 해당하는 게시물만 불러오기
    // 바로 위의 메소드와 거의 같으며 sql문만 다르다
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

    //글 입력하기
    //위에 있던 메소드와는 다르게 DB에 입력하는 메소드, DTO인스턴스가 아닌 단순 데이터를 인자로 받아와 글을 작성한다.
    public int insertPost(String image, String content, int userId) throws Exception {
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        // 지금까지와는 다르게 rs를 선언하지 않는다. 결과값이 필요없기 때문

        // db에 오류없이 입력이 되었는지 체크하는 integer, 이 변수를 리턴하여 jsp단에 오류 여부를 알려준다.
        int check = 0;

        try {
            sql = "INSERT INTO insta.post(IMAGE, CONTENT, USER_ID) VALUES(?,?,?)";
            pstmt = con.prepareStatement(sql);

            // 물음표 사용법은 상단과 동일
            pstmt.setString(1, image);
            pstmt.setString(2, content);
            pstmt.setInt(3, userId);

            pstmt.executeUpdate();

            // 글 작성과 동시에 해시태그를 DB에 집어넣는 메소드를 호출함
            insertHashTag(content);

            check = 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBClose.close(con, pstmt);
        }
        return check;
    }


    // 사용자가 작성한 글의 내용만 받아와 해시태그를 하이퍼링크로 바꿔주는 메소드를 호출하고,
    // 해시태그의 Id와 사용자가 작성한 포스트 id의 관계를 설정 > 복잡한 메소드들 집합
    public void insertHashTag(String content){
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;

        // 해시태그를 생성하기 위해 HashTagGenerator클래스를 hash라는 이름으로 생성한다
        HashTagGenerator hash = new HashTagGenerator();
        String url = "/hashPostView.jsp?hash=";
        ResultSet rs = null;

        try{
            // 이미 입력된 포스팅에서 내용이 같은 포스팅의id와 내용을 불러온다(비효율적이며 리팩토링이 필요)
            sql = "SELECT id, content FROM insta.post WHERE content = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, content);

            rs = pstmt.executeQuery();

            if(rs.next()){
                // 불러온 포스팅의 내용부분을 새로 바꿔주는 부분
                String oldContent = rs.getString("content");
                int postId = rs.getInt("id");
                // 원래 입력받았던 내용을 HashTagGenerator클래스의 setAutoLinkHashTag메소드를 이용해
                // 내용 안에 있는 해시태그를 인식해 하이퍼링크형태로 바꿔준다.
                String newContent = hash.setAutoLinkHashTag(url, oldContent, postId);

                // 새로 바뀐 내용부분을 수정하여 DB에 다시 입력
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

    //게시물 삭제
    public int deletePost(int post_id) throws Exception{
        Connection con = dbconnect.getConnection();
        PreparedStatement pstmt = null;
        int check = 0;

        try{
            // 게시물이 삭제될 경우 코멘트와 해시태그간의 관계도 삭제하기 때문에 총 3번의 sql문이 실행된다.
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

}
