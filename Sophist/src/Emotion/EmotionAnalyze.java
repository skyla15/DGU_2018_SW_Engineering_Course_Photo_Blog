package Emotion;

import java.net.URI;
import java.io.*;

//MS FACE API�� ���� MSŬ���� ������ �̹��� �м� ������ httpComponent import
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

//json ��ü�� ó���ϱ�(�Ľ�)�ϱ� ���� import
import org.json.*;

//�м� ����� �̹����� ��ġ�� ���� java.awt import
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
            // 프로젝트 시 키를 나중에 변경 필요 
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
        	
        	
        	// 화남 
        	graphics.drawString("Angry : " + angerRate, leftPosition - 85, topPosition + 10);
        	
        	// 경명 
        	graphics.drawString("Cons.. : " + contemptRate, leftPosition - 85, topPosition + 25);
        	
        	//역겨움 
        	graphics.drawString("���ܿ� : " + disgustRate, leftPosition - 85, topPosition + 40);
        	
        	// 두려움 
        	graphics.drawString("�η��� : " + fearRate, leftPosition - 85, topPosition + 55);
        	
        	// 행복 
        	graphics.drawString("�ູ : " + happinessRate, leftPosition - 85, topPosition + 70);
        	
        	// 무표정 
        	graphics.drawString("�߸� : " + neutralRate, leftPosition - 85, topPosition + 85);
        	
        	// 슬픔 
        	graphics.drawString("���� : " + sadnessRate, leftPosition - 85, topPosition + 100);
        	
        	// 놀라움 
        	graphics.drawString("��� : " + surpriseRate, leftPosition - 85, topPosition + 115);
        	
        	
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
