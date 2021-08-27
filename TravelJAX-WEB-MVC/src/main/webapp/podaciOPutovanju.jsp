<%@page import="model.Putovanje"%>
<%@page import="model.Destinacija"%>
<%@page import="model.Korisnik"%>
<%@page import="model.DAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
if (request.getSession().getAttribute("korisnik") != null) {
%>



<%
DAO dao = new DAO();
ArrayList<Korisnik> lk = dao.selecKorisnik();
ArrayList<Destinacija> ld = dao.selectDestinacija();
ArrayList<Putovanje> lp = dao.putovanje();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Podaci o putovanju</title>
<link rel="stylesheet" type="text/css" href="still.css">
</head>

<body>
	<div class="content">
	<p>Ulogovani ste kao: ${sessionScope.korisnik.ime}</p>
	<br>
	<a href="Putnik?action=Logout">Logout</a>

	<h1>PODACI O PUTOVANJU</h1>

	<form action="Putnik" method="post">
		<ul>
			<li><label for="imePrezime">IME I PREZIME</label> <select
				name="imePrezime">
					<%
					for (Korisnik k : lk) {
					%>
					<option value="<%=k.getId()%>"><%=k.getIme() + " " + k.getPrezime()%></option>
					<%
					}
					%>
			</select></li>
			<li><label for="destinacija">DESTINACIJA</label> <select
				name="destinacija">
					<%
					for (Destinacija d : ld) {
					%>
					<option value="<%=d.getId()%>"><%=d.getNaziv()%></option>
					<%
					}
					%>
			</select></li>
			<li><label for="datum_polaska">DATUM POLASKA</label> <input
				type="date" name="datum_polaska"></li>
			<li><label for="datum_povratka">DATUM POVRATKA</label> <input
				type="date" name="datum_povratka"><br></li>
			<li><label for="duzina">DUZINA PUTA</label> <input type="number"
				name="duzina"></li>
			<li><label for="cena">CENA KARTE</label> <input type="number"
				name="cena"></li>
			<li class="radioB"><label for="test">PREVOZ</label><br> <label
				for="avion">AVION</label> <input type="radio" name="test"
				value="avion" id="izbor"> <label for="autobus">AUTOBUS</label>
				<input type="radio" name="test" value="autobus" id="izbor">
			</li>
		</ul>
		<input type="submit" name="action" value="Dodaj">
	</form>

	<p>${requestScope.msg}</p>
	<br>
	<table border="1px solid darkgreen">
		<thead>
			<tr>
				<th>PID</th>
				<th>IME</th>
				<th>PREZIME</th>
				<th>DESTINACIJA</th>
				<th>POLAZAK</th>
				<th>POVRATAK</th>
				<th>DUZINA</th>
				<th>CENA</th>
				<th>PREVOZ</th>
				<th>AKCIJA</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Putovanje pom : lp) {
			%>
			<tr>
				<td><%=pom.getIdPutovanja()%></td>
				<td><%=pom.getK().getIme()%></td>
				<td><%=pom.getK().getPrezime()%></td>
				<td><%=pom.getD().getNaziv()%></td>
				<td><%=pom.getVremePolaska()%></td>
				<td><%=pom.getDatumPovratka()%></td>
				<td><%=pom.getDuzinaPuta()%></td>
				<td><%=pom.getCena()%></td>
				<td><%=pom.getP().getNazivPrevoza()%></td>
				<td name="id" value="<%=pom.getIdPutovanja()%>"><a
					href="Putnik?action=Delete&id=<%=pom.getIdPutovanja()%>">DELETE</a></td>
			</tr>
			<%
			}
			%>

		</tbody>
	</table>

	</div>
</body>
</html>

<%
} else {
response.sendRedirect("index.jsp");
}
%>
