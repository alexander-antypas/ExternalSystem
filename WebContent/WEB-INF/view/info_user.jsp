<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
	

	<div id="content1">
		<h3>Έχεις συμπληρώσει την αίτηση</h3>
		
		<form action="Servlet" method="post">
			<input type="hidden" id="id" name="id" value="<sec:authentication property="principal.username" />">
			<button type="submit" name="check_app" value="check_app">ΔΕΣ ΤΗΝ ΕΞΕΛΙΞΗ ΤΗΣ ΑΙΤΗΣΗΣ</button>
		</form>
		<h3>${message}</h3>
	</div>

