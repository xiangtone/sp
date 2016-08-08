/**
 * 定时器程序管理
 * 
 */
Ext.onReady(function() {
	
		          Ext.Ajax.request({
				                url : './thread.ered?reqCode=loadQuartzs',
				               success : function(response) {
				               	 	var res=Ext.decode(response.responseText);
				                 if(res.status_1000==1){
				              		 Ext.getCmp("btn1").setDisabled(true);
				              		document.getElementById("quartz1").innerHTML="<font color='#33CC00' size='2px'>已开启</font>";
				                 }else{
					                 Ext.getCmp("btn2").setDisabled(true);
					                 document.getElementById("quartz1").innerHTML="<font color='#33CC00' size='2px'>已关闭</font>";
				                 }
				             },
				              failure : function(response){
				                	var res=Ext.decode(response.responseText);
				                  Ext.MessageBox.alert('提示', res.msg);
				              }
			   });

			var btn1 = new Ext.Button({
						id:'btn1',
						text : '开启',
						applyTo : 'btn1_div',
						//disabled:status_1000==5?true:false,     //设是否可用
						handler : function() {
							Ext.Ajax.request({
					                url     : './thread.ered?reqCode=statrThead',
					                success : function(form, action) {
									         Ext.MessageBox.alert('提示','短信夜间开关模式定时器开启成功');
									          Ext.getCmp("btn2").setDisabled(false);	
									          Ext.getCmp("btn1").setDisabled(true);
									          document.getElementById("quartz1").innerHTML="<font color='#33CC00' size='2px'>已开启</font>";
								       },
							     	failure : function(form, action) {
									         Ext.MessageBox.alert('提示', '开启失败(已经开启过了)');
								       }
			           	 		});
						}
					});
			var btn2 = new Ext.Button({
						id:'btn2',
						text : '停止',
						applyTo : 'btn2_div',
						handler : function() {
								Ext.Ajax.request({
						                url     : './thread.ered?reqCode=stopThead',
						                success : function(form, action) {
										         Ext.MessageBox.alert('提示','短信夜间开关模式定时器停止成功');
										         Ext.getCmp("btn1").setDisabled(false);	
										         Ext.getCmp("btn2").setDisabled(true);
										         document.getElementById("quartz1").innerHTML="<font color='#33CC00' size='2px'>已关闭</font>";
									       },
								     	failure : function(form, action) {
										         Ext.MessageBox.alert('提示', '停止失败(已经暂停过了)');
									       }
			           	 		});
						}
					});
		    var btn21 = new Ext.Button({
		    	        id:'btn21',
						text : '开启',
					    //disabled :  true, 
						applyTo : 'btn21_div',
						handler : function() {
							
							// alert('我不会被上面的提示窗口阻塞哦!');
							Ext.Ajax.request({
					                url     : './thread.ered?reqCode=starUserNum',
					                success : function(form, action) {
									        
									        Ext.MessageBox.alert('提示', '开启');
								       },
							     	failure : function(form, action) {
									         Ext.MessageBox.alert('提示', '开启失败(已经开启过了)');
								       }
							});
						}
					});
					
			var btn22 = new Ext.Button({
						text : '停止',
						applyTo : 'btn22_div',
						handler : function() {
							Ext.MessageBox.alert('提示', '停止');
							// alert('我不会被上面的提示窗口阻塞哦!');
						}
					});
					
					
		   
			var firstWindow = new Ext.Window({
				        id : firstWindow,
				        name : firstWindow,
						title : '<center>短信夜间开关模式定时器</center>', // 窗口标题
						layout : 'fit', // 设置窗口布局模式
						width : 200, // 窗口宽度
						height : 80, // 窗口高度
						// tbar : tb, // 工具栏
						closable : false, // 是否可关闭
						collapsible : true, // 是否可收缩
						maximizable : false, // 设置是否可以最大化
						border : false, // 边框线设置
						pageY : 1, // 页面定位Y坐标
						pageX : 1, // 页面定位X坐标
						constrain : true,
						// 设置窗口是否可以溢出父容器
						
						items : [new Ext.Panel({
									contentEl : 'window1'
								})
								//,new Ext.Panel({
								  //      html:'niao'
								  // })
								]
					});
					
			var secondWindow = new Ext.Window({
						title : '<center>统计日表用户数定时器</center>', // 窗口标题
						layout : 'fit', // 设置窗口布局模式
						width : 200, // 窗口宽度
						height : 80, // 窗口高度
						// tbar : tb, // 工具栏
						closable : false, // 是否可关闭
						collapsible : true, // 是否可收缩
						maximizable : false, // 设置是否可以最大化
						border : false, // 边框线设置
						pageY : 1, // 页面定位Y坐标
						pageX : 250, // 页面定位X坐标
						constrain : true,
						// 设置窗口是否可以溢出父容器
						items : [new Ext.Panel({
									contentEl : 'window2'
								})]
					});
			
			firstWindow.show(); // 显示窗口
			secondWindow.show();
			
	      
		});