<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class ="list">
	<form action="Servlet" method="POST" enctype="multipart/form-data" >
		<h1>Αίτηση Μεταγγραφής</h1>
		
			USER ID: <sec:authentication property="principal.username" />
			
			<input type="hidden" id="user_id" name="user_id" value="<sec:authentication property="principal.username" />">
			
			<h3> Πιστοποιητικό Οικογειακής Κατάστασης </h3><br /><br />
			Select a file: <input type="file" name="family" size="50"><br /><br />
			<h3>Εκκαθαριστικό της τρέχουσας χρονιάς </h3><br /><br />
			Select a file: <input type="file" name="financially" size="50"><br /><br />
			<h3>Βεβαίωση μόνιμης Κατοικίας </h3><br /><br />
			Select a file: <input type="file" name="locality" size="50"><br /><br />
			<button type="submit" name="application" value="Submit">ΥΠΟΒΟΛΗ</button>
			<br/><br/><br/>
			${result}
			<br/><br/><br/>
	</form>
	
		
</div>