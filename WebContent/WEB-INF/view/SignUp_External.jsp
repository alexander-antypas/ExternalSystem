<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form action="/ExternalSystem/definer" method="get">
	<button>ΠΙΣΩ</button>
</form>

<div id="contentup">
	<form action="Servlet" method="POST" enctype="multipart/form-data" oninput="Selector()">
		<h1>ΕΓΓΡΑΦΗ ΣΤΟ ΣΥΣΤΗΜΑ</h1>
		<label for="username">ΟΝΟΜΑ</label>
		<br />
		<input type="text" id="username" name="username" placeholder="ΟΝΟΜΑ" required>
		<br />
		<label for="surname">ΕΠΩΝΥΜΟ</label>
		<br />
		<input type="text" id="surname" name="surname" placeholder="ΕΠΩΝΥΜΟ" required>
		<br />
		<label for="university">ΠΑΝΕΠΙΣΤΗΜΙΟ</label> 
		<input type="text" id="u" list="uni" name="uni" required>
  		<datalist id="uni">
  		<c:forEach items="${departments}" var="dep">
    		<option value="${dep.name}">
    		</c:forEach>
  		</datalist>
		<br />
		<label for="id">Α.Μ. ΧΡΗΣΤΗ</label>
		<p ><font id="par" color="red"></font></p>
		<input type="text"	id="id" name="id" placeholder="Α.Μ. ΧΡΗΣΤΗ" required>
		<p ><font id="check" color="red"></font></p>
		<label	for="title">ΚΩΔΙΚΟΣ</label>
		<br />
		<input type="password" id="password" name="password" placeholder="ΚΩΔΙΚΟΣ" required>
		<br /> 
		<label for="year">ΕΤΟΣ ΓΕΝΝΗΣΗΣ</label>
		<br />
		<input type="number" id="year" name="year" min="1980" max="2019" placeholder="ΕΤΟΣ ΓΕΝΝΗΣΗΣ" required>
		<br /> 
		<label for="age">ΗΛΙΚΙΑ</label> 
		<br />
		<input type="number" id="age" name="age" placeholder="ΗΛΙΚΙΑ" required min="17" max="99">
		<br /> 
		<label for="email">E-MAIL</label> 
		<br />
		<input type="text" id="email" name="email" placeholder="E-MAIL" required>
		<br /> 
		<label for="yoe">ΕΤΟΣ ΕΓΓΡΑΦΗΣ ΣΤΟ ΠΑΝΕΠΙΣΤΗΜΙΟ</label> 
		<br />
		<input type="number" id="yoe" name="yoe" placeholder="ΕΤΟΣ ΕΓΓΡΑΦΗΣ ΣΤΟ ΠΑΝΕΠΙΣΤΗΜΙΟ" required min="2000" max="2019">
		<button name="add_external" id="add" type="submit" value="add">ΕΓΓΡΑΦΗ</button>
	</form>
</div>