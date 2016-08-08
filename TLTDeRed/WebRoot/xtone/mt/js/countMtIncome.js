/**
 * 统计收入
 * @author Dove
 * @since 2011-12-09
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 65, // 标签宽度
						frame : true, //是否渲染表单面板背景色
						defaultType : 'textfield', // 表单元素默认类型
						labelAlign : 'right', // 标签对齐方式
						bodyStyle : 'padding:5 5 5 5', // 表单元素和表单面板的边距
						items : [{
							        id : 'data',
							        xtype : 'datefield',
									fieldLabel : '统计月份:', // 标签
									name : 'data', // name:后台根据此name属性取值 
									format:'Y-m', //日期格式化
									value:new Date(),
									anchor : '100%' // 宽度百分比
								}
								]
					});

			var firstWindow = new Ext.Window({
						title : '<center>月账单统计收入</center>', // 窗口标题
						layout : 'fit', // 设置窗口布局模式
						width : 300, // 窗口宽度
						height : 200, // 窗口高度
						closable : false, // 是否可关闭
						collapsible : true, // 是否可收缩
						maximizable : true, // 设置是否可以最大化
						border : false, // 边框线设置
						constrain : true, // 设置窗口是否可以溢出父容器
						pageY : 20, //页面定位Y坐标
						pageX : document.body.clientWidth / 2 - 300 / 2, //页面定位X坐标
						items : [firstForm], // 嵌入的表单面板
						buttons : [{ // 窗口底部按钮配置
						text : '统计', // 按钮文本
						iconCls : 'tbar_synchronizeIcon', // 按钮图标
						handler : function() { // 按钮响应函数
						firstForm.getForm().submit({
									url : './mtIncome.ered?reqCode=countMtIncome',
									waitMsg : '正在统计收入.....',
									success : function(form, action) {
										Ext.Msg.alert('提示','统计收入成功');
									},
									failure : function(form, action) {
										Ext.Msg.alert('提示', '统计收入失败');// 提示
									}
								});
								
							}
						}]
					});
			firstWindow.show(); // 显示窗口

		});
		
		