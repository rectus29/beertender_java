function start() {
	gapi.load('auth2', function () {
		auth2 = gapi.auth2.init({
			client_id: '708114846781-bo0iiu7basehi6ueqlopuhuedr609f8h.apps.googleusercontent.com'
		});
	});
}

function signInCallback(authResult) {
	if (authResult['code']) {
		// Send the code to the server
		$.ajax({
			type: 'POST',
			url: 'googleOauthSignin',
			// Always include an `X-Requested-With` header in every AJAX request,
			// to protect against CSRF attacks.
			headers: {
				'X-Requested-With': 'XMLHttpRequest'
			},
			contentType: 'application/octet-stream; charset=utf-8',
			success: function (result) {
				window.location.assign('home');
				// Handle or verify the server response.
			},
			error: function (result) {
				alert("Error while Google Authentication");
				$('#signinButton i').toggleClass('fa-spin');
			},
			processData: false,
			data: authResult['code']
		});
	} else {
		// There was an error.
	}
}