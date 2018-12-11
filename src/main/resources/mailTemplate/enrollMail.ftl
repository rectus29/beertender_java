<html>
<body>
	<img src="${server_url}/api/mailtracker.png?mailtoken=${trackingToken}">
<h3>Bonjour ${user.name}</h3>
<div>
	Bienvenu dans le BeerTender, suivez le lien pour vous connectez,<br/>
	<br/>
	<a href="${server_url}/enroll/${mailToken}"></a>
</div>
</body>

</html>