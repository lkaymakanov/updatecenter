/**
function obtainElementById(kind, srcId){
	var objs = new Array();
	var objects = document.getElementsByTagName(kind);
//alert(objects.length);
	for (var i=0; i < objects.length; i++){
		var obj = objects[i];
		var ids = (obj.id).split(':');
		var last = ids[ids.length - 1];
		if (last.substring(0, srcId.length) == srcId) {
//alert(last.substring(0, srcId.length) + '|' + srcId);			
			objs[objs.length] = obj;
		}
	}
	return objs;
}
*/

function obtainElementById(modalForm){
	return document.getElementById(modalForm+':'+'modalMsg');
}

function errMsg() {
	var modalMsgs = obtainElementById('modalForm');
	if ((modalMsgs.innerHTML.trim()).length > 0) {
		Richfaces.showModalPanel('msgModal');
		return true;
	}	
	return false;
}


function showLibModal(){
	var modalMsgs = obtainElementById('modalForm2');
	if ((modalMsgs.innerHTML.trim()).length > 0) {
		Richfaces.showModalPanel('msgModal2');
		return true;
	}
	return false;
 }