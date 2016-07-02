function getDayDisplay()
{
	var weeks = ["星期天","星期一","星期二","星期三","星期四","星期五","星期六"];
	var myDate = new Date();    
	var value = "今天是：" + myDate.getFullYear() + "年" 
		+ (myDate.getMonth()+1) + "月" + myDate.getDate() 
		+ "日 " + weeks[myDate.getDay()];
	return value;
}

function joBaseObject(id,pid,name)
{
	var obj = {};
	obj.id = id;
	obj.pid = pid;
	obj.name = name;
	return obj;
}

function joBaseSelectOption(id,name)
{
	var obj = {};
	obj.id = id;
	obj.name = name;
	return obj;
}

function GetRadioValue(RadioName)
{
    var obj;    
    obj=document.getElementsByName(RadioName);
    if(obj!=null)
    {
        var i;
        for(i=0;i<obj.length;i++)
        {
            if(obj[i].checked)
            {
                return obj[i].value;            
            }
        }
    }
    return null;
}

function setRadioCheck(RadioName,value)
{
	var obj; 
    obj=document.getElementsByName(RadioName);
    if(obj!=null)
    {
        var i;
        for(i=0;i<obj.length;i++)
        {
            if(obj[i].value==value)
            {
            	obj[i].checked = "checked";  
            }
            else
            {
            	obj[i].checked = "";  
            }
        }
    }
}

function isNullOrEmpty(strVal) 
{
	if (strVal == '' || strVal == null || strVal == undefined) 
	{
		return true;
	} 
	else 
	{
		return false;
	}
}

function getAjaxValue(url,onSuccess)
{
	$.ajax(
	{
		url: url,
		type : "get",
		async: true,
		success:function(data)
		{
			onSuccess(data);
		}
	}); 
}

function isPositiveInteger(value)
{
	 if((/^(\+|-)?\d+$/.test(value)) && value>0 )
		 return true;
	 
	 return false;
}



