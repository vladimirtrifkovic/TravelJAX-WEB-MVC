<%@page import="model.VrstaPrevoza"%>
<%@page import="model.Destinacija"%>
<%@page import="model.DAO"%>
<%@page import="model.Korisnik"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
	DAO dao = new DAO();
	ArrayList<Korisnik> lk = dao.selecKorisnik();
	for(Korisnik k : lk){
		out.println(k);
	}
%>
<br>
<br>
<%
	
	ArrayList<Destinacija> dl = dao.selectDestinacija();
	for(Destinacija d : dl){
		out.println(d);
	}
%>
<br>
<br>
<%
	
	ArrayList<VrstaPrevoza> vp = dao.prevoz();
	for(VrstaPrevoza p : vp){
		out.println(p);
	}
%>
<br>
<br>
<%
	
	int idk = dao.select_id_prevoza("avion");
	out.println("id prevoza je: " + idk);
	out.println(dao.selecKorisnikByIdAndPassword("pero", "pero123").toString());
%>
</body>
</html>