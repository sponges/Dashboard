$(document).ready(function () {
	loadNavbarMobileToggle();

	$("#alert-remove").click(function() {
		$("#alert").hide();
	});
});

function loadNavbarMobileToggle() {
	var toggle = $('#nav-toggle');
	var menu = $('#nav-menu');

	toggle.click(function() {
		$(this).toggleClass('is-active');
		menu.toggleClass('is-active');
	});
}