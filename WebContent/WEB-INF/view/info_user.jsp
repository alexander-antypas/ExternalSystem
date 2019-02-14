<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
	
<div id="contentup">
	<h2>Έχεις συμπληρώσει την αίτηση</h2>
	
	<form action="Servlet" method="post">
		<input type="hidden" id="id" name="id" value="<sec:authentication property="principal.username" />">
		<button type="submit" name="check_app" value="check_app">ΔΕΣ ΤΗΝ ΕΞΕΛΙΞΗ ΤΗΣ ΑΙΤΗΣΗΣ</button>
	</form>
	<h1>${message}</h1>
	
</div>