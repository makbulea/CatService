$(document).ready(function () {
    $('#divCustomValueHeight').hide();
	$('#divCustomValueWidth').hide();
	$('#divTextValue').hide();

$('#recordType').change(function () {

	var recordType = $('#recordType').val();

	if(recordType == "Tag"){
	$('#divCustomValueHeight').hide();
    $('#divCustomValueWidth').hide();
    $('#divTextValue').hide();
    $('#divTagValue').show();
    $('.tagValue').prop('required',true);
	}
	else if(recordType == "Custom"){
	$('#divTagValue').hide();
	$('#divTextValue').hide();
	$('#divCustomValueHeight').show();
	$('#divCustomValueWidth').show();
	$('.customValueWidth').prop('required',true);
	$('.customValueHeight').prop('required',true);
	}
	else{
    $('#divTextValue').show();
   	$('#divTagValue').hide();
   	$('#divCustomValueHeight').hide();
   	$('#divCustomValueWidth').hide();
   	$('.textValue').prop('required',true);
	}
});
});
