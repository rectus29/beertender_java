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

function pulse() {

}

jQuery(document).ready(function ($) {


	//    sidebar dropdown menu auto scrolling

	$('#sidebar .sub-menu > a').click(function () {
		var o = ($(this).offset());
		diff = 250 - o.top;
		if (diff > 0)
			$("#sidebar").scrollTo("-=" + Math.abs(diff), 500);
		else
			$("#sidebar").scrollTo("+=" + Math.abs(diff), 500);
	});


	//    sidebar toggle

	$(function () {
		function responsiveView() {
			var wSize = $(window).width();
			if (wSize <= 768) {
				$('#container').addClass('sidebar-close');
				$('#sidebar > ul').hide();
			}

			if (wSize > 768) {
				$('#container').removeClass('sidebar-close');
				$('#sidebar > ul').show();
			}
		}

		$(window).on('load', responsiveView);
		$(window).on('resize', responsiveView);
	});

	$('.fa-bars').click(function () {
		if ($('#sidebar > ul').is(":visible") === true) {
			$('#main-content').css({
				'margin-left': '0px'
			});
			$('#sidebar').css({
				'margin-left': '-210px'
			});
			$('#sidebar > ul').hide();
			$("#container").addClass("sidebar-closed");
		} else {
			$('#main-content').css({
				'margin-left': '210px'
			});
			$('#sidebar > ul').show();
			$('#sidebar').css({
				'margin-left': '0'
			});
			$("#container").removeClass("sidebar-closed");
		}
	});

	// custom scrollbar
	$("#sidebar").niceScroll({
		styler: "fb",
		cursorcolor: "#4ECDC4",
		cursorwidth: '3',
		cursorborderradius: '10px',
		background: '#404040',
		spacebarenabled: false,
		cursorborder: ''
	});

	//  $("html").niceScroll({styler:"fb",cursorcolor:"#4ECDC4", cursorwidth: '6', cursorborderradius: '10px', background: '#404040', spacebarenabled:false,  cursorborder: '', zindex: '1000'});

	// widget tools

	$('.panel .tools .fa-chevron-down').click(function () {
		var el = jQuery(this).parents(".panel").children(".panel-body");
		if (jQuery(this).hasClass("fa-chevron-down")) {
			jQuery(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
			el.slideUp(200);
		} else {
			jQuery(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
			el.slideDown(200);
		}
	});

	$('.panel .tools .fa-times').click(function () {
		jQuery(this).parents(".panel").parent().remove();
	});


	//    tool tips

	$('.tooltips').tooltip();

	//    popovers

	$('.popovers').popover();


	// custom bar chart

	if ($(".custom-bar-chart")) {
		$(".bar").each(function () {
			var i = $(this).find(".value").html();
			$(this).find(".value").html("");
			$(this).find(".value").animate({
				height: i
			}, 2000)
		})
	}
});
