function ajax(value,url,spanId,key){
	
	if(value==null){
		return;
	}
	
	$.ajax({
  		url:url+value,
  		dataType:'json',
  		success:function(data){
//  			{"t.address":"建材城西路中腾商务大厦","t.birthday":"1949-10-01","t.dep.name":
//  				"管理员组","t.dep.tele":"000000","t.dep.uuid":1,"t.email":"admin@itcast.cn",
//  				"t.gender":1,"t.name":"超级管理员","t.tele":"12345678","t.username":"admin","t.uuid":1}
  			$("#"+spanId).html(data[key]);
  		}
  	})
  	return "<span id='"+spanId+"'><span>"
}
