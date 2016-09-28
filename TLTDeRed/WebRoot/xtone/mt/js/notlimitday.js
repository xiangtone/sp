/**
 * 短信下行临时表
 * @author liaopengjie
 * @since 2011-09-26
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
						header : '合作方',
						dataIndex : 'company',
						width : 100,
						sortable : true
					},{
						header : '合作方名称',
						dataIndex : 'partnername',
						width : 100,
						id : 'sqltext'
					}, {
						header : '备注',
						dataIndex : 'remark',
						width : 300,
						sortable : true
					}
			]);

		/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'notLimit.ered?reqCode=queryNotLimit'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{       name : 'company'
										}, {
											name : 'partnername'
										}, {
											name : 'remark'
										}
										])
					});

			// 翻页排序时带上查询条件
			store.on('beforeload', function() {
					
						this.baseParams = {
								
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
						title :  '<span style="font-weight:normal">夜间开关不执行合作方(0点8点)</span>',
						iconCls: 'database_refreshIcon',
						renderTo : 'notlimitDayDiv',// 和JSP页面的DIV元素ID对应
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
						tbar : [{
									text : '增加',
									iconCls : 'page_addIcon',
									handler : function() {
											Ext.Msg.alert('提示', '开发中.........!');
									}
								}, '-', {
									text : '刷新',
									iconCls : 'arrow_refreshIcon',
									handler : function() {
										store.reload();
									}
								}],
						bbar : bbar
					});
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
		
			
		});