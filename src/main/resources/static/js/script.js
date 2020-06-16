function isNumberKey(evt) {
//	var charCode = (evt.which) ? evt.which : evt.keyCode;
//	
//	if ((charCode >= 48 && charCode <= 57) || charCode <= 31) {
//		return true;
//	}
//	return false;
	
	return Number.isInteger(parseInt(evt.key, 10))
		|| evt.key === 'Delete'
		|| evt.key === 'Backspace'
		|| evt.key === 'ArrowLeft'
		|| evt.key === 'ArrowRight'
		|| evt.key === 'Tab'
}