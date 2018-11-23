package Emotion;

import java.net.URI;
import java.io.*;

//MS FACE API占쏙옙 占쏙옙占쏙옙 MS클占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占싱뱄옙占쏙옙 占싻쇽옙 占쏙옙占쏙옙占쏙옙 httpComponent import
import org.apache.http.HttpEntity;
import org.apache.http.entity.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;

//json 占쏙옙체占쏙옙 처占쏙옙占싹깍옙(占식쏙옙)占싹깍옙 占쏙옙占쏙옙 import
import org.json.*;

//占싻쇽옙 占쏙옙占쏙옙占� 占싱뱄옙占쏙옙占쏙옙 占쏙옙치占쏙옙 占쏙옙占쏙옙 java.awt import
import java.awt.image.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class EmotionAnalyze {

    private float angerRate;
    private float contemptRate;
    private float disgustRate;
    private float fearRate;
    private float happinessRate;
    private float neutralRate;
    private float sadnessRate;
    private float surpriseRate;
	
    private int topPosition;
    private int leftPosition;
    private int width;
    private int height;
    
    private File InputFile;
    private String outputFile;
    
    public String getReulstFile()
    {
    	return outputFile;
    }
    
    
    public void DetectEmotion()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://japaneast.api.cognitive.microsoft.com/face/v1.0/detect");

            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");
            builder.setParameter("returnFaceAttributes", "emotion");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/octet-stream");
            // �봽濡쒖젥�듃 �떆 �궎瑜� �굹以묒뿉 蹂�寃� �븘�슂 
            request.setHeader("Ocp-Apim-Subscription-Key", "250c2043f4784659aa489aeeaa4b3a48");

            request.setEntity(new FileEntity(InputFile));

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
            	String src = EntityUtils.toString(entity);
                System.out.println(src);
                
                JSONArray arrObj = new JSONArray(src);
                for(int i=0; i<arrObj.length(); i++) {
	                
                    topPosition = arrObj.getJSONObject(0).getJSONObject("faceRectangle").getInt("top");
                    leftPosition = arrObj.getJSONObject(0).getJSONObject("faceRectangle").getInt("left");;
                    width = arrObj.getJSONObject(0).getJSONObject("faceRectangle").getInt("width");
                    height = arrObj.getJSONObject(0).getJSONObject("faceRectangle").getInt("height");
                	      	
                 	angerRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("anger"); 
	                contemptRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("contempt"); 
	                disgustRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("disgust"); 
	                fearRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("fear"); 
	                happinessRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("happiness"); 
	                neutralRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("neutral"); 
	                sadnessRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("sadness"); 
	                surpriseRate =  arrObj.getJSONObject(0).getJSONObject("faceAttributes").getJSONObject("emotion").getFloat("surprise");
                }             
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void makeResult(String uploadPath, String OutputPath) {
    	BufferedImage image;
        
        try {
        	image= ImageIO.read(InputFile);
        	Graphics2D graphics = image.createGraphics();
        	graphics.setColor(Color.blue);
        	graphics.drawRect(leftPosition, topPosition, width, height);
        	
        	
        	graphics.setColor(Color.gray);
        	graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        	graphics.fillRect(leftPosition - 85, topPosition, 80, 125);
        	
        	graphics.setColor(Color.black);
        	Font font = new Font("Serif", Font.BOLD, 10);
        	
        	
        	// �솕�궓 
        	graphics.drawString("화남 : " + angerRate, leftPosition - 85, topPosition + 10);
        	
        	// 寃쎈챸 
        	graphics.drawString("경멸 : " + contemptRate, leftPosition - 85, topPosition + 25);
        	
        	//�뿭寃⑥� 
        	graphics.drawString("역겨움 : " + disgustRate, leftPosition - 85, topPosition + 40);
        	
        	// �몢�젮�� 
        	graphics.drawString("두려움 : " + fearRate, leftPosition - 85, topPosition + 55);
        	
        	// �뻾蹂� 
        	graphics.drawString("행복 : " + happinessRate, leftPosition - 85, topPosition + 70);
        	
        	// 臾댄몴�젙 
        	graphics.drawString("중립 : " + neutralRate, leftPosition - 85, topPosition + 85);
        	
        	// �뒳�뵒 
        	graphics.drawString("슬픔 : " + sadnessRate, leftPosition - 85, topPosition + 100);
        	
        	// ���씪�� 
        	graphics.drawString("놀람 : " + surpriseRate, leftPosition - 85, topPosition + 115);
        	
        	
        	try{
        		outputFile = "result.jpg";
        		ImageIO.write(image, "jpeg", new File(uploadPath +"/" + outputFile));
         	}catch(Exception e)
        	{
        		System.out.println(e.getMessage());
        	}
        	
        }
        catch(Exception e)
        {
        	System.out.println(e.getMessage());
        }
    }
    
    public void Analaze(String uploadPath, String file) {
    	InputFile = new File(uploadPath+"/"+file);
    	FileInputStream fis = null;
        try {
        	fis = new FileInputStream(InputFile);   	
        }catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        
        DetectEmotion();
        makeResult(uploadPath, file);
    	
        
    }

}
