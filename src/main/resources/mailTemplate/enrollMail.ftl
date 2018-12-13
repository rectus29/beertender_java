<html>
	<body>
		<img src="${server_url}/api/mailtracker.png?mailtoken=${trackingToken.token}" style="display: none;">
		<img src="${server_url}/img/beer.png" width="40px">
		<h3>Bonjour ${user.name}</h3>
		<div>
			Bienvenu dans le BeerTender, suivez le lien pour vous connectez,<br/>
			<br/>
			<a href="${server_url}/enroll/${mailToken.token}">c'est par ici</a>
		</div>
	</body>
</html>