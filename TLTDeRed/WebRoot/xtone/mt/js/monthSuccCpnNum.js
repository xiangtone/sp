/**
 * 报警
 * 
 */
Ext.onReady(function() {
			var btn1 = new Ext.Button({
						id:'btn1',
						text : '开启',
						applyTo : 'btn1_div',
						//disabled:status_1000==5?true:false,     //设是否可用
						handler : function() {
							Ext.Ajax.request({   
					                url     : './monthSuccCpnNum.ered?reqCode=startMonthSuccCpnNum',
					                success : function(form, action) {
									         Ext.MessageBox.alert('提示','开启成功');
									        
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
						              //  url     : './thread.ered?reqCode=stopThead',
						                success : function(form, action) {
										         Ext.MessageBox.alert('提示','停止成功');
										       
									       },
								     	failure : function(form, action) {
										         Ext.MessageBox.alert('提示', '停止失败(已经暂停过了)');
									       }
			           	 		});
						}
					});
			var firstWindow = new Ext.Window({
				        id : firstWindow,
				        name : firstWindow,
						title : '<center>统计月限手机号码人数</center>', // 窗口标题
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
					
		
			
			firstWindow.show(); // 显示窗口
			
			
	      
		});