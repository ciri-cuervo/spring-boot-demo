JS.form = {

	bindMozErrormessge : function() {
		$('input[title], select[title], textarea[title]').each(function() {
			$(this).attr('x-moz-errormessage', $(this).attr('title'));
		});
	}

};

$(document).ready(function() {
	JS.form.bindMozErrormessge();
});
