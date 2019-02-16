<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page import="gr.hua.dit.database.Servlet" %>



<div id="content1">
	${message_for_external}
	<br />
	<form:form action="${pageContext.request.contextPath}/authUser"
		method="POST">
		<h1>ΣΥΝΔΕΣΗ</h1>
		<label for="author">Α.Μ. ΧΡΗΣΤΗ</label>
		<br />
		<input type="text" id="user_id" name="username" placeholder="Α.Μ. ΧΡΗΣΤΗ" required>
		<br />
		<label for="password">ΚΩΔΙΚΟΣ</label>
		<br />
		<input type="password" id="password" name="password" placeholder="ΚΩΔΙΚΟΣ" required>
		<input type="submit" value="ΣΥΝΔΕΣΗ">
		<br />
	</form:form>
	<h3>ΔΕΝ ΕΧΕΙΣ ΛΟΓΑΡΙΑΣΜΟ?</h3>
	<form action="Servlet" method="post">
	<button name="show_dep" type="submit" value="show_dep">ΕΓΓΡΑΦΗ ΣΤΟ ΣΥΣΤΗΜΑ</button>
	</form>
</div>