<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="expertsender.webservice.ExpertSender" %>
<%@ page import="java.lang.Integer" %>

<%!
    private String ExpsStatusCode;
    private String apikey;
 %>

<%
String emailaddress = request.getParameter("em");
String templateId = request.getParameter("tid");
String fn = request.getParameter("fn");
String list = request.getParameter("list");
int brand = Integer.parseInt(request.getParameter("brand"));
int action = Integer.parseInt(request.getParameter("action"));

if (brand != 0 && action != 0){

switch (brand) {
//Sweepstakes 1
case 1:  apikey = "mvQyn0GVrB5Mp2th1Cf9";
    break;
//bSaving 2
case 2:  apikey = "6NFdpekVhYtdQLlyHkvQ";
    break;
//Quick Approval 3
case 3:  apikey = "sg2Adimd1W8JBBzeeMdg";
      break;
}

ExpertSender exps = new ExpertSender(apikey);

switch (action) {
case 1:
	exps.buildSubMessage(list, emailaddress, fn);
	ExpsStatusCode = exps.subscribeToList();
    break;
case 2:
	exps.buildSendMessage(emailaddress);
	ExpsStatusCode = exps.sendTransactional(templateId);
    break;
}


}
else{
	ExpsStatusCode = "Missing request data";
}
%>


<%=ExpsStatusCode%>
