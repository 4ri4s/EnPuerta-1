$(document).ready(function() {

	$('.datepicker').datepicker({
		format:'dd/mm/yyyy',
		defaultDate:'1/01/1990',
		setDefaultDate: false
	});
	
	$('select').formSelect();

});