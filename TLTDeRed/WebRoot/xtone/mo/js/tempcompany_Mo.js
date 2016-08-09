/**
 * 短信上行临时表
 * 
 * @author liaopengjie
 * @since 2011-09-28
 * 
 */
Ext.onReady(function() {
				// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();
						// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});
			var cm = new Ext.grid.ColumnModel([rownum, sm,
					{
						header : '合作方名称',
						dataIndex : 'companyname',
						width : 100,
						sortable : true
					},{
						header : '业务名称',
						dataIndex : 'gamename',
						width : 100,
						id : 'sqltext'
					}, {
						header : '手机',
						dataIndex : 'cpn',
						width : 80,
						sortable : true
					},{
						header : 'linkid',
						dataIndex : 'linkid',
						width : 250
					},{
						header : '下发状态',
						dataIndex : 'tocompstat',
						width : 80,
						sortable : true,
						renderer:renderTocompstat
					}, {
						header : '接收状态',
						dataIndex : 'comprecstat',
						width : 80,
						sortable : true,
						renderer:renderComprecstat
					}, {
						header : '时间',
						dataIndex : 'addate',
						sortable : true,
						width : 150
						
					}
			]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'tempMo.ered?reqCode=queryTemtMo'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{       name : 'companyname'
										}, {
											name : 'gamename'
										}, {
											name : 'cpn'
										}, {
											name : 'linkid'
										} , {
											name : 'tocompstat'
										}, {
											name : 'comprecstat'
										}, {
										     name:'addate'
										}
										])
					});

			// 翻页排序时带上查询条件
			store.on('beforeload', function() {
					var addate = Ext.getCmp('addate').getValue();
						this.baseParams = {
								companyName : Ext.getCmp('companyName').getValue(),
								cpn : Ext.getCmp('cpn').getValue(),
								gameName : Ext.getCmp('gameName').getValue(),
								provinceName : Ext.getCmp('provinceName').getValue(),
								city : Ext.getCmp('city').getValue(),
								tocompstat: Ext.getCmp('tocompstat').getValue(),
								comprecstat: Ext.getCmp('comprecstat').getValue(),
								addate : addate
						};
					});
					

					

			var pagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						hiddenName : 'pagesize',
						typeAhead : true,
						triggerAction : 'all',
						lazyRender : true,
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
								}),
						valueField : 'value',
						displayField : 'text',
						value : '50',
						editable : false,
						width : 85
					});
			var number = parseInt(pagesize_combo.getValue());
			pagesize_combo.on("select", function(comboBox) {
						bbar.pageSize = parseInt(comboBox.getValue());
						number = parseInt(comboBox.getValue());
						store.reload({
									params : {
										start : 0,
										limit : bbar.pageSize
									}
								});
					});

			var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						// emptyMsg
						// :
						// "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});
			var grid = new Ext.grid.GridPanel({
						title :  '<span style="font-weight:normal">合作方下行日表数据查询</span>',
						iconCls: 'database_refreshIcon',
						renderTo : 'companyMoDiv',// 和JSP页面的DIV元素ID对应
						height : 500,
						// width:600,
						autoScroll : true,
						region : 'center',// 和VIEWPORT布局模型对应，充当center区域布局
						store : store,
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						},
						stripeRows : true,
						frame : true,
						//autoExpandColumn : 'sqltext',
						cm : cm,
					//	plugins : expander,
						tbar : [
						{
									id : 'addate',
									name : 'addate',
									xtype : 'datefield',
									emptyText : '请输入日期',
									format : 'Y-m-d',
									width : 120
								},'-', 
						
						{
									id : 'companyName',
									name : 'companyName',
									xtype : 'textfield',
									emptyText : '请输入合作方',
									width : 120
								}, '-', {
									xtype : 'textfield',
									id : 'cpn',
									name : 'cpn',
									emptyText : '请输入手机号码',
									width : 120
									
								
								}, '-',{
									xtype : 'textfield',
									id : 'gameName',
									name : 'gameName',
									emptyText : '请输入业务名称',
									width : 120
								}, '-',{
									xtype : 'textfield',
									id : 'provinceName',
									name : 'provinceName',
									emptyText : '省份',
									width : 100
									
									
								}, '-',{
									xtype : 'textfield',
									id : 'city',
									name : 'city',
									emptyText : '城市',
									width : 100
									
								
								},  '-',
								 '->',  {
									text : '查 询',
									iconCls : 'previewIcon',
									width : 100,
									handler : function() {
										query();
									}
								}, '-', {
									text : '刷 新',
									iconCls : 'arrow_refreshIcon',
									width : 100,
									handler : function() {
										store.reload();
									}
								}],
						listeners : {
			     'render' : function() {
			      var tbar = new Ext.Toolbar({
			      	
			         items : [
			         new Ext.form.ComboBox({
											id : 'tocompstat',
											hiddenName : 'tocompstatId',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
														fields : ['value', 'text'],
														data : [['1', '成功'], ['0', '失败']]
													}),
											displayField : 'text',
											valueField : 'value',
											mode : 'local',
											listWidth : 120, // 下拉列表的宽度,默认为下拉选择框的宽度
											forceSelection : true,
											typeAhead : true,
											emptyText : '下发状态',
											// editable : false,
											resizable : true,
											width : 120
										}),
			         	       '-',
			         	        new Ext.form.ComboBox({
											id : 'comprecstat',
											hiddenName : 'comprecstatId',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
														fields : ['value', 'text'],
														data : [['0', '成功(0)'], ['1', '失败']]
													}),
											displayField : 'text',
											valueField : 'value',
											mode : 'local',
											listWidth : 120, // 下拉列表的宽度,默认为下拉选择框的宽度
											forceSelection : true,
											typeAhead : true,
											emptyText : '接收状态',
											// editable : false,
											resizable : true,
											width : 120
										})
								
								]
			        });
			      tbar.render(this.tbar);
			     }
			    },
						bbar : bbar
					});
					//跳转进去
			store.load({
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
			grid.on('sortchange', function() {
						grid.getSelectionModel().selectFirstRow();
					});

			bbar.on("change", function() {
						grid.getSelectionModel().selectFirstRow();
					});
			/**
			 * 布局
			 */
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});
			/**
			 * 查询
			 */
			function query() {
				var addate = Ext.getCmp('addate').getValue();
				
				  store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								companyName : Ext.getCmp('companyName').getValue(),
								cpn : Ext.getCmp('cpn').getValue(),
								gameName : Ext.getCmp('gameName').getValue(),
								tocompstat: Ext.getCmp('tocompstat').getValue(),
								comprecstat: Ext.getCmp('comprecstat').getValue(),
								addate : addate
								
							}
						});
			};
			//输出改变字体颜色渲染器
function renderComprecstat(value){
	if(value=='0'){
		return "<span style='color:green;'>成功</span>"
	}else{
		return "<span style='color:red;'>"+value+"</span>"
	}
};
//输出改变字体颜色渲染器
function renderTocompstat(value){
	if(value=='1'){
		return "<span style='color:green;'>成功</span>"
	}else{
		return "<span style='color:red;'>"+value+"</span>"
	}
}
		});