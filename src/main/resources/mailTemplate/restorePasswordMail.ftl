<html>
	<body>
		<img src="${server_url}/api/mailtracker.png?mailtoken=${trackingToken.token}" style="display: none;">
		<img src="${server_url}/img/beer.png" width="40px">
		<h3>Bonjour ${user.lastName} ${user.firstName}</h3>

		Pour finaliser la procédure de restoration de votre mot de passe veuillez suivre le lien suivant : <br/>
		<a href="${server_url}/restorepassword/${uid}">${server_url}/restorepassword/${uid}</a><br/>
		<br/>
		<br/>
		<b>Attention : le lien ci-dessus n'est valable que pendant 24h</b><br/>
		<br/>
		Cordialement,<br/>
		<a href="${server_url}">${server_url}</a>
	</body>
</html>