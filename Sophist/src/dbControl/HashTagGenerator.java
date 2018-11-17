package dbControl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 글의 내용에서 해시태그와 공백 사이의 문자를 인식하고, 하이퍼링크 형태로 바꿔주는 메소드를 가지고 있는 클래스
public class HashTagGenerator {

    public static String setAutoLinkHashTag(String url, String content, int postId){
        // String들을 담는 list 객체를 생성
        List<String> list = new ArrayList<String>();
        // 검색하고자하는 패턴을 저장
        String pattern = "(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)";
        // hash테이블에 접근할 때 필요한 HashDAO생성
        HashDAO hashDao = new HashDAO();

        // 인자로 받아온 content를 strRet이라는 스트링 타입으로 저장
        String strRet = content;

        try{
            // 지정해둔 패턴과 매칭되는 스트링들이 있는지 검색
            Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
            Matcher matcher = compiledPattern.matcher(content);
            while(matcher.find()) {
                list.add(matcher.group().trim());
            }
            Collections.sort(list);
            //만약에 일치하는 스트링들이 검색되면
            if(list.size() > 0){
                for(int k = list.size() -1; k >= 0; k--){
                    String hashContent = list.get(k);
                    // 기존의 스트링값들에 하이퍼링크를 덧씌움, 추가적으로 인코딩작업도 진행
                    strRet = strRet.replaceAll(
                            list.get(k),
                            "<a href='" + url + URLEncoder.encode(hashContent, "UTF-8") + "'>"
                                + hashContent +
                            "</a>"
                    );

                    // 입력받은 해시태그와 동일한 해시태그가 존재하는지 검색
                    // hashDao의 getHashId메소드는 존재하지 않으면 0, 존재하면 1, 오류시 -1을 리턴하는 메소드임
                    int hashId = hashDao.getHashId(list.get(k));
                    if (hashId == 0){
                        hashDao.insertHash(hashContent);
                        hashId = hashDao.getHashId(hashContent);
                        hashDao.makeHashPostRel(hashId, postId);
                    }else if(hashId == -1){
                        System.out.print("오류발생");
                    }else{
                        hashDao.makeHashPostRel(hashId, postId);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        // 하이퍼링크로 변환 완료된 포스팅 내용을 다시 리턴함
        return strRet;
    }

}
