package chimpPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class MakeApicalls {
	public String domain,client_id,client_secret,
	postAccessTokenUrl,postApproveEmailUrl,access_token,audience_value;
	InputStream input;
	MakeApicalls() {
		try {
			getPropertyValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getUsertoken() throws ClientProtocolException, IOException{
		
	      HttpClient client = HttpClientBuilder.create().build();;
		  HttpPost post = new HttpPost(postAccessTokenUrl);
		  JSONObject json = new JSONObject();
		  json.put("audience",audience_value);
	      json.put("client_id",client_id);
	      json.put("client_secret",client_secret);
	      json.put("grant_type", "client_credentials");
		  StringEntity se = new StringEntity( json.toString());
		  post.setEntity(se);
		  post.addHeader("Content-Type", "application/json");
		  HttpResponse response = client.execute(post);
		  BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		  String strloine=rd.readLine(); 
		  //System.out.println(" --- " +strloine);
		  JSONObject jsonObj = new JSONObject(strloine);
		  access_token=jsonObj.get("access_token").toString();
		  System.out.println("access_token" + access_token);
		  return access_token;
		
	}
	public void activateUser(String userId,String access_token) throws ClientProtocolException, IOException{
		
		  HttpClient client = HttpClientBuilder.create().build();;
		  HttpPost post = new HttpPost(postApproveEmailUrl+userId);
		  JSONObject json = new JSONObject();
	      json.put("email","body.email");
	      json.put("email_verified",true);
	      
		  StringEntity se = new StringEntity( json.toString());
		  post.setEntity(se);
		  post.addHeader("Content-Type", "application/json");
		  post.addHeader("Authorization", access_token);
		  
		  HttpResponse response = client.execute(post);
		  System.out.println(response.toString());
//		  To Display the response		  
//		  BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//		  String strloine=rd.readLine(); 
//		  //System.out.println(" --- " +strloine);
//		  JSONObject jsonObj = new JSONObject(strloine);
//		  System.out.println(jsonObj.get("access_token").toString());
//		  String access_token=jsonObj.get("access_token").toString();
//		  return access_token;
//		
	}
	
	public void getPropertyValues() throws IOException
	{
		Properties prop = new Properties();
		String propFileName = "MakeApicalls.properties";
		input = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (input != null) {
			prop.load(input);
			client_id=prop.getProperty("client_idconf");
			client_secret=prop.getProperty("client_secretconf");
			postAccessTokenUrl=prop.getProperty("postAccessTokenUrlconf");
			postApproveEmailUrl=prop.getProperty("postApproveEmailUrlconf");
			audience_value=prop.getProperty("audience_valueconf");
			
		}
		else 
		{
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		
	}

}
