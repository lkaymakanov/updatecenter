
//init image link map
var map = new   Map(); 
map.set('delete.png', 'delete_dsbl.png' );
map.set('publish.png', 'publish_dsbl.png');
map.set('deploy.png', 'deploy_dsbl.png');
map.set('stoppublish.png', 'stoppublish_dsbl.png');

map.set('delete_dsbl.png', 'delete.png' );
map.set('publish_dsbl.png', 'publish.png');
map.set('deploy_dsbl.png', 'deploy.png');
map.set('stoppublish_dsbl.png', 'stoppublish.png' );


/**exit from application*/
function exit(){
	document.getElementById('containerform:logOut').click();
}

/**obtain modal dialog element*/
function obtainElementById(modalForm){
	return document.getElementById(modalForm+':'+'modalMsg');
}

/**shows the modal dialog if any message  is available*/
function errMsg() {
	var modalMsgs = obtainElementById('modalForm');
	if ((modalMsgs.innerHTML.trim()).length > 0) {
		Richfaces.showModalPanel('msgModal');
		return true;
	}	
	return false;
}

function manageBtnHover(btn){
	btn.style.opacity = 0.5;
}

function manageBtnMouseLeave(btn){
	btn.style.opacity = 1.0;
}
/**
jQuery.each(jQuery('input.manageapp'), function(ind, val){console.log(ind); console.log(val.disabled)});

function invertImage(image){
	 if(image.disabled == false) 
	 var res=image.src.split('/');  
	 image.src= '../images/'+ map.get(res[res.length-1]);
}


function createImages(){	
	jQuery.each(jQuery('input.manageapp'), function(ind, image){invertImage(image);});
}
*/