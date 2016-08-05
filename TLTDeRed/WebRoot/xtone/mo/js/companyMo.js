/**
 * 短信上行日表
 * 
 * @author liaopengjie
 * @since 2011-09-28
 * 
 */
Ext.onReady(function() {
			//超时时间
			Ext.Ajax.timeout = 120000;
			
			var companyStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'mo.ered?reqCode=queryCompany'
						}),
				reader : new Ext.data.JsonReader({}, 
								[{name : 'value'},
								 {name : 'text'}
								]),
				baseParams : {}
			});
	// areaStore.load(); //如果mode : 'local',时候才需要手动load();

	var companyCombo = new Ext.form.ComboBox({
				id:"companyName",
				hiddenName : 'companyNameId',
				fieldLabel : '合作方',
				emptyText : '全部合作方',
				triggerAction : 'all',
				store : companyStore,
				displayField : 'text',
				valueField : 'value',
				loadingText : '正在加载数据...',
				mode : 'remote', // 数据会自动读取,如果设置为local又调用了store.load()则会读取2次；也可以将其设置为local，然后通过store.load()方法来读取
				forceSelection : true,
				typeAhead : true,
				resizable : true,
				editable : false,
				anchor : '100%',
				width : 120
			});
	var mostateCombo=new Ext.form.ComboBox({
		id : 'mostate',
		hiddenName : 'mostateId',
		triggerAction : 'all',
		store : new Ext.data.SimpleStore({
					fields : ['value', 'text'],
					data : [['0', '正常'], ['23', '超限(日限,月限)'], ['4444', '省份屏蔽(省份关闭,到量,0-8点关闭,地市屏蔽)'],
							['1', '扣量'], ['2', '达到日限'], ['3', '达到月限'],
							['41', '省份关闭'], ['42', '省份到量'], ['43', '0-8点关闭'],
							['44', '地市屏蔽'], ['99', '白号码'], ['5', '灰号码']]
				}),
		displayField : 'text',
		valueField : 'value',
		mode : 'local',
		listWidth : 260, // 下拉列表的宽度,默认为下拉选择框的宽度
		forceSelection : true,
		typeAhead : true,
		emptyText : '上行状态',
		// editable : false,
		resizable : true,
		width : 120
	});
				// 复选框
			var sm = new Ext.grid.CheckboxSelectionModel();
						// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});
			var cm = new Ext.grid.ColumnModel([rownum, sm,
			{
						header : '合作方',
						dataIndex : 'company',
						width : 100,
						sortable : true
					},
					{
						header : '合作方名称',
						dataIndex : 'companyname',
						width : 100,
						sortable : true
					},{
						header : '业务名称',
						dataIndex : 'gamename',
						width : 100,
						sortable : true,
						id : 'sqltext'
					}, {
						header : '手机',
						dataIndex : 'cpn',
						width : 80,
						sortable : true
					},{
						header : 'linkid',
						dataIndex : 'linkid',
						sortable : true,
						width : 150
					}
					, {
						header : '省份',
						dataIndex : 'provincename',
						sortable : true,
						width : 80
					}, {
						header : '城市',
						dataIndex : 'city',
						sortable : true,
						width : 80
					},  {
						header : '下发状态',
						dataIndex : 'tocompstat',
						width : 80,
						sortable : true,
						renderer:renderTocompstat
					}, {
						header : '合作方接收状态',
						dataIndex : 'comprecstat',
						width : 80,
						sortable : true,
						renderer:renderComprecstat
					}, {
						header : '时间',
						dataIndex : 'addate',
						sortable : true,
						width : 150	
					},{
						header : '上行状态',
						dataIndex : 'memo',
						width : 80,
						sortable : true
					}
			]);
			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'mo.ered?reqCode=queryMo'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{       name : 'company'
										},
									       {       
									        name : 'companyname'
										}, {
											name : 'gamename'
										}, {
											name : 'cpn'
										}, {
											name : 'provincename'
										}, {
											name : 'city'
										}, {
											name : 'linkid'
										} , {
											name : 'tocompstat'
										}, {
											name : 'comprecstat'
										}, {
										     name:'addate'
										},{
										     name:'memo'
										}
										])
					});

			// 翻页排序时带上查询条件
			store.on('beforeload', function() {
						var tableName = Ext.getCmp('tableName').getValue();
						var mttime1 =Ext.getCmp('mttime1').getValue();
				        var mttime2 =Ext.getCmp('mttime2').getValue();
				        
				        if(!Ext.isEmpty(mttime1)){
					          mttime1= tableName.format('Y-m-d').toString()+" "+mttime1;
						}
						if(!Ext.isEmpty(mttime2)){
						          mttime2= tableName.format('Y-m-d').toString()+" "+mttime2;
						}
				        
						if (!Ext.isEmpty(tableName)) {
							tableName = tableName.format('Ymd').toString()+"company_mo";
						}
						
						this.baseParams = {
							    tableName : tableName,
							    companyName : Ext.getCmp('companyName').getValue(),
								cpn : Ext.getCmp('cpn').getValue(),
								gameName : Ext.getCmp('gameName').getValue(),
								provinceName : Ext.getCmp('provinceName').getValue(),
								city : Ext.getCmp('city').getValue(),
								tocompstat: Ext.getCmp('tocompstat').getValue(),
								comprecstat: Ext.getCmp('comprecstat').getValue(),
								mttime1 : mttime1,
								mttime2 : mttime2,
								mostate: Ext.getCmp('mostate').getValue()
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
						title :  '<span style="font-weight:normal">上行日表数据查询</span>',
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
									id : 'tableName',
									name : 'tableName',
									xtype : 'datefield',
									emptyText : '请输入日期',
									format : 'Y-m-d',
									width : 120
								}, '-', 
						
					/*	{
									id : 'companyName',
									name : 'companyName',
									xtype : 'textfield',
									emptyText : '请输入合作方',
									width : 120
								},*/
								companyCombo, '-',
								mostateCombo,'-',
								{
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
									
								
								},   '-',	'->',	
								{
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
														data : [['1', '成功(DELIVRD)'], ['0', '失败']]
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
										}),
								'-',
								{
									xtype : 'textfield',
									id : 'mttime1',
									name : 'mttime1',
									emptyText : '(hh:mm:ss)开始时间',
									width : 120
								},
								'-',	{
									xtype : 'textfield',
									id : 'mttime2',
									name : 'mttime2',
									emptyText : '(hh:mm:ss)结束时间',
									width : 120
								}
								]
			        });
			      tbar.render(this.tbar);
			     }
			    },
						bbar : bbar
					});
					//跳转进去不加载
			//store.load({
					//	params : {
							//start : 0,
							
							//limit : bbar.pageSize
						//}
					//});
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
				var tableName = Ext.getCmp('tableName').getValue();
				var mttime1 =Ext.getCmp('mttime1').getValue();
				var mttime2 =Ext.getCmp('mttime2').getValue();
			
				if(Ext.isEmpty(tableName)){
				  Ext.Msg.alert('系统温馨提示','童学,请您记得输入要查询的日期，呵呵!');
				}
				else{
					if(!Ext.isEmpty(mttime1)){
					          mttime1= tableName.format('Y-m-d').toString()+" "+mttime1;
					}
					if(!Ext.isEmpty(mttime2)){
					          mttime2= tableName.format('Y-m-d').toString()+" "+mttime2;
					}
					tableName = tableName.format('Ymd').toString()+"company_mo";
					
				  store.load({
								params : {
									start : 0,
									limit : bbar.pageSize,
									tableName : tableName,
									companyName : Ext.getCmp('companyName').getValue(),
									cpn : Ext.getCmp('cpn').getValue(),
									gameName : Ext.getCmp('gameName').getValue(),
									provinceName : Ext.getCmp('provinceName').getValue(),
									city : Ext.getCmp('city').getValue(),
									tocompstat: Ext.getCmp('tocompstat').getValue(),
								    comprecstat: Ext.getCmp('comprecstat').getValue(),
									mttime1 : mttime1,
									mttime2 : mttime2,
									mostate: Ext.getCmp('mostate').getValue()
								}
					    	});
				}
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
};

});
		
		