package expertsender.webservice;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class ExpertSender {
   String apikey;
   String xmlPostData;

   public ExpertSender(String apikey){
    this.apikey = apikey;
}


   public void buildSubMessage(String listId, String emailAddress, String firstName){
try{
StringWriter sw = new StringWriter();
DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

Document document = documentBuilder.newDocument();
document.setXmlStandalone(true);

Element e1 = document.createElement("ApiRequest");
e1.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
e1.setAttribute("xmlns:xs","http://www.w3.org/2001/XMLSchema");
document.appendChild(e1);

Element e2 = document.createElement("ApiKey");
e2.appendChild(document.createTextNode(this.apikey));
e1.appendChild(e2);

Element e3 = document.createElement("Data");
e3.setAttribute("xsi:type","Subscriber");
e1.appendChild(e3);

Element eMode = document.createElement("Mode");
eMode.appendChild(document.createTextNode("AddAndUpdate"));
e3.appendChild(eMode);

Element eListId = document.createElement("ListId");
eListId.appendChild(document.createTextNode(listId));
e3.appendChild(eListId);

Element eEmail = document.createElement("Email");
eEmail.appendChild(document.createTextNode(emailAddress));
e3.appendChild(eEmail);

Element eFirstName = document.createElement("Firstname");
eFirstName.appendChild(document.createTextNode(firstName));
e3.appendChild(eFirstName);


TransformerFactory transformerFactory = TransformerFactory.newInstance();
Transformer transformer = transformerFactory.newTransformer();
transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
DOMSource source = new DOMSource(document);
StreamResult result =  new StreamResult(sw);
transformer.transform(source, result);

this.xmlPostData = sw.toString();
 }

   catch (ParserConfigurationException e) {
		this.xmlPostData = "";
	}
   catch (TransformerConfigurationException e){
		this.xmlPostData = "";
	}
	catch (TransformerException e){
		this.xmlPostData = "";
	}
      
   }

   public void buildSendMessage(String emailAddress){
try{
StringWriter sw = new StringWriter();
DocumentBuilderFactory documentBuilderFactory =  DocumentBuilderFactory.newInstance();
DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

Document document = documentBuilder.newDocument();
document.setXmlStandalone(true);
Element e1 = document.createElement("ApiRequest");
e1.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
e1.setAttribute("xmlns:xs","http://www.w3.org/2001/XMLSchema");
document.appendChild(e1);

Element e2 = document.createElement("ApiKey");
e2.appendChild(document.createTextNode(this.apikey));
e1.appendChild(e2);

Element e3 = document.createElement("Data");
e1.appendChild(e3);


Element eReceiver = document.createElement("Receiver");
e3.appendChild(eReceiver);

Element eEmail = document.createElement("Email");
eEmail.appendChild(document.createTextNode(emailAddress));
eReceiver.appendChild(eEmail);

TransformerFactory transformerFactory = TransformerFactory.newInstance();
Transformer transformer = transformerFactory.newTransformer();
transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
DOMSource source = new DOMSource(document);
StreamResult result =  new StreamResult(sw);
transformer.transform(source, result);
this.xmlPostData = sw.toString();
   }

catch (ParserConfigurationException e) {
		this.xmlPostData = "";
	}
catch (TransformerConfigurationException e){
		this.xmlPostData = "";
	}
	catch (TransformerException e){
		this.xmlPostData = "";
	}

      
   }



   public String subscribeToList(){
try{ 
DefaultHttpClient httpClient = new DefaultHttpClient();
HttpPost postRequest = new HttpPost("https://api.esv2.com/Api/Subscribers/");

StringEntity input = new StringEntity(this.xmlPostData);
input.setContentType("text/xml");
postRequest.setEntity(input);

HttpResponse resp = httpClient.execute(postRequest);

int ExpsStatusCode = resp.getStatusLine().getStatusCode();

String wsResponse = String.valueOf(ExpsStatusCode);

httpClient.getConnectionManager().shutdown();

return wsResponse;
}

catch (UnsupportedEncodingException e){
	return "UnsupportedEncodingException";
}
catch (IOException e){
	return "IOException";
}

      
   }

   public String sendTransactional(String templateId){
 try{
DefaultHttpClient httpClient = new DefaultHttpClient();
HttpPost postRequest = new HttpPost("http://api.esv2.com/Api/Transactionals/" + templateId);

StringEntity input = new StringEntity(this.xmlPostData);
input.setContentType("text/xml");
postRequest.setEntity(input);

HttpResponse resp = httpClient.execute(postRequest);

int ExpsStatusCode = resp.getStatusLine().getStatusCode();

String wsResponse = String.valueOf(ExpsStatusCode);

httpClient.getConnectionManager().shutdown();

return wsResponse;
   }
 
 catch (UnsupportedEncodingException e){
		return "UnsupportedEncodingException";
	}
	catch (IOException e){
		return "IOException";
	}

 
}


 }