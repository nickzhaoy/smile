var url=location.search;
//?type=1&state=2

var Request = new Object();
if(url.indexOf("?")!=-1)
{
    var str = url.substr(1)     //type=1&state=2
    strs = str.split("&");  // type=1&state=2
    for(var i=0;i<strs.length;i++)
    {
//    	strs[0]   type=1
    	        //type                               1
        Request[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
    }
}
