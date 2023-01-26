package fr.vallfeur.deepl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Deepl {

	enum Lang{
		BG, CS, DA, DE, EL, ENsGB, ENsUS, EN, ES, ET, FI, FR, HU, IT, JA, LT, LV, NL, PL, PTsPT, PTsBR, PT, RO, RU, SL, SV, ZH
	}

	private final String key;
	
	/**
	 * @param key your auth key
	 */
	public Deepl(String key) {
		this.key = key;
	}


	/*
		@param text: the thing that you want to translate
		@param lang: the language (all languages are in the enum: Deepl.Lang)

		return content:
		0: content
		1: code --> 1: pass ; 0: fail ; 2: exception
	 */
	public String[] translate(String text, Lang lang){
		try{
			HttpURLConnection connection = (HttpURLConnection) new URL("https://api-free.deepl.com/v2/translate?auth_key="+key+"&text="+URLEncoder.encode(text, StandardCharsets.UTF_8)+"&target_lang="+lang.name()).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
	            
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder infos = new StringBuilder();
			String line;
	            
			while ((line = in.readLine()) != null) {
				infos.append("\n").append(line);
			}
			in.close();

			String[] value;

			JSONObject content = (JSONObject) new JSONParser().parse(URLDecoder.decode(infos.toString(), StandardCharsets.UTF_8));

			if(content.get("message") != null){
				value = new String[]{content.get("message").toString(), "0"};
			}else{
				JSONArray array = (JSONArray) content.get("translations");
				JSONObject json = (JSONObject) array.get(0);

				value = new String[]{json.get("text").toString(), "1"};
			}

			return value;

		}catch(IOException ex){
			return new String[]{"[DeepLApi] Error: URL looks incorrect! (check key and language)\nURL: https://api-free.deepl.com/v2/translate?auth_key="+key+"&text="+URLEncoder.encode(text, StandardCharsets.UTF_8)+"&target_lang="+lang.name(), "2"};

		} catch (ParseException e) {
			return new String[]{"[DeepLApi] Error: JsonParse failed " + new RuntimeException(e), "2"};

		}
	}
	
}
