$(document).ready(function() {
	initializeApplication();
});
initializeApplication = function() {
    $.ajaxSetup({ cache: false });
	menuEvent.drawMenu();
};

