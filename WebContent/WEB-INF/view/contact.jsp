<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div id=help>
		
		
		<h1>Γραμματείες Τμημάτων:</h1>

    	<h2>&#8921; ΟΙΚΙΑΚΗΣ ΟΙΚΟΝΟΜΙΑΣ ΚΑΙ ΟΙΚΟΛΟΓΙΑΣ</h2>

		<p>*τηλ:210-9549117 και 210-9549109</p>

    	<h2>&#8921; ΕΠΙΣΤΗΜΗΣ ΔΙΑΙΤΟΛΟΓΙΑΣ - ΔΙΑΤΡΟΦΗΣ</h2>

		<p>*τηλ: 210-9549111 και 210 - 9549114</p>

    	<h2>&#8921; ΓΕΩΓΡΑΦΙΑΣ</h2>

		<p>*τηλ: 210-9549150 και 210-9549151/<p>

    	<h2>&#8921; ΠΛΗΡΟΦΟΡΙΚΗΣ και ΤΗΛΕΜΑΤΙΚΗΣ</h2>

		<p>*τηλ: 210-9549400 και 2109549444</p>

		<h2>&#8921; ΤΟΠΟΘΕΣΙΑ</h2>
		<p>*Για το τμήμα της πληροφορικής και τηλεματικής: Ομηρου 9, Ταύρος </p>
		<p>*Για τα υπόλοιπα τμήματα: Ελευθερίου Βενιζέλου 70, Καλλιθέα</p>
		<div id="googleMap" style="width:100%;height:400px;"></div>

		<script>
			function myMap() {
			var mapProp= {
  			center:new google.maps.LatLng(51.508742,-0.120850),
  			zoom:5,
			};
			
			var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
			}
			var marker = new google.maps.Marker({position: myCenter});
			var marker = new google.maps.Marker({
				  position:myCenter,
				  animation:google.maps.Animation.BOUNCE
				});

			marker.setMap(map);
		</script>

		<!-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAp-toMhPfKuMl__r4ABBKpBWM_YdRwGCI&callback=myMap"></script> -->
		
		 
	</div>