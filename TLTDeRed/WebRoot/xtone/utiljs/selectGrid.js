/**
 * 选中grid总的数据可以复制
 * 
 * @author liaopengjie
 * @since 2011-09-28
 * 
 */
 if (!Ext.grid.GridView.prototype.templates) { 
Ext.grid.GridView.prototype.templates = {}; 
} 
Ext.grid.GridView.prototype.templates.cell = new Ext.Template( 
'<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' , 
'<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' , 
'</td>' 
);

