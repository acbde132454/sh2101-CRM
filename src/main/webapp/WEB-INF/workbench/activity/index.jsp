<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/layer/theme/default/layer.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<%--分页插件--%>
<link rel="stylesheet" href="/crm/jquery/bs_pagination/jquery.bs_pagination.min.css" />
<script type="text/javascript" src="/crm/jquery/bs_pagination/en.js"></script>
<script type="text/javascript" src="/crm/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>

<script type="text/javascript" src="/crm/jquery/layer.js"></script>

<script type="text/javascript">

	$(function(){
		
		
		
	});
	
</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="createActivityForm" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" name="owner" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="name" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="startDate" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="endDate" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="cost" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" name="description" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" onclick="createOrUpdate($(this))" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" id="updateActivityForm" role="form">
						<input type="hidden" name="id" id="aid">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" name="owner" id="edit-marketActivityOwner">
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" name="name" id="edit-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="startDate" id="edit-startTime">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="endDate" id="edit-endTime">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" name="cost" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" name="description" id="edit-describe">

								</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="createOrUpdate($(this))">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="owner" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTime">
				    </div>
				  </div>

					<%--
						type=submit
						触发onclick事件，同时还会触发提交表单事件
					--%>
				  <button type="button" onclick="queryActivity()" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" id="createActivityBtn" class="btn btn-primary" data-toggle="modal" data-target="#createActivityModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" onclick="updateActivity()" class="btn btn-default" data-toggle="modal"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" onclick="deleteBatch($(this))"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				  <button type="button" class="btn btn-warning" onclick="deleteBatch($(this))"><span class="glyphicon glyphicon-minus"></span> 批量删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="father" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityListBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div style="margin-top: -30px" id="activityPage">
				</div>
			</div>
			
		</div>
		
	</div>
</body>
<script>

	//点击查询按钮
	function queryActivity(){
		refresh(1,3);
	}

	//调用页面刷新的方法 参数1:当前页码 参数2:每页记录数
	refresh(1,3);

	//刷新页面的方法
	function refresh(page,pageSize){
		$.post("/crm/workbench/activity/list",{
			"page" : page,
			"pageSize" : pageSize,
			"name" : $('#name').val(),
			"owner" : $('#owner').val(),
			"startDate" : $('#startTime').val(),
			"endDate" : $('#endTime').val()
		},function(data){
			//清空tbody中的内容
			$('#activityListBody').html("");
			//append:在当前DOM元素最后面添加一个新的内容
			var activities = data.pageInfo.list;
			for(var i = 0; i < activities.length; i++){
				var activity = activities[i];
				$('#activityListBody').append("<tr class=\"active\">\n" +
						"\t\t\t\t\t\t\t<td><input type=\"checkbox\" class='son' value="+activity.id+" /></td>\n" +
						"\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"location.href = '/crm/workbench/toDetail?id="+activity.id+"' \">"+activity.name+"</a></td>" +
						"                            <td>"+activity.owner+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
						"\t\t\t\t\t\t</tr>");
			}
			console.log($('#activityListBody').html())
			$("#activityPage").bs_pagination({
				currentPage: data.pageInfo.pageNum, // 当前页
				rowsPerPage: data.pageInfo.pageSize, // 每页显示的记录条数
				maxRowsPerPage: 20, // 每页最多显示的记录条数
				totalPages: data.pageInfo.pages, // 总页数
				totalRows: data.pageInfo.total, // 总记录条数
				visiblePageLinks: 3, // 显示几个卡片
				showGoToPage: true,
				showRowsPerPage: true,
				showRowsInfo: true,
				showRowsDefaultInfo: true,
				//回调函数，用户每次点击分页插件进行翻页的时候就会触发该函数
				onChangePage : function(event, obj){
					//刷新页面，obj.currentPage:当前点击的页码
					// pageList(obj.currentPage,obj.rowsPerPage);

				    refresh(obj.currentPage,obj.rowsPerPage);
				}
			});
		},'json')
	}

	//异步查询所有者信息
	$('#createActivityBtn').click(function () {
		//create-marketActivityOwner
		$.post("/crm/workbench/queryAllOwners",function(data){
			var content = "";
			for(var i = 0 ; i < data.length; i++){
				content += "<option value="+data[i].id+">"+data[i].name+"</option>";
			}
			//html text:innerHTML innerText
			$('#create-marketActivityOwner').html(content);
		},'json')
	});

	//日历插件
	$("#create-startTime").datetimepicker({
		language:  "zh-CN",
		format: "yyyy-mm-dd",//显示格式
		minView: "day",//设置只显示到月份
		initialDate: new Date(),//初始化当前日期
		autoclose: true,//选中自动关闭
		todayBtn: true, //显示今日按钮
		clearBtn : true,
		pickerPosition: "bottom-left"
	});
	$("#create-endTime").datetimepicker({
		language:  "zh-CN",
		format: "yyyy-mm-dd",//显示格式
		minView: "hour",//设置只显示到月份
		initialDate: new Date(),//初始化当前日期
		autoclose: true,//选中自动关闭
		todayBtn: true, //显示今日按钮
		clearBtn : true,
		pickerPosition: "bottom-left"
	});


	var selectedLength = 0;

	//全选和全不选 bind:绑定各种事件 参数1:事件名称 参数2:触发的函数 event:触发的事件类型
	$('#father').bind('click',function (event) {
		//操作元素属性的函数:attr:操作自定义属性 <a name=""> prop:操作固有属性 <a href="#">
		/*if($(this).prop("checked")){
			$('.son').each(function () {
				$(this).prop('checked',true);
			});
		}else{
			$('.son').each(function () {
				$(this).prop('checked',false);
			});
		}*/
		$('.son').each(function () {
			$(this).prop('checked',$('#father').prop('checked'));
		});

		//统计father是否勾中
		if($('#father').prop("checked")){
			//勾中和所有的son是长度相同
			selectedLength = $('.son').length;
		}else{
			selectedLength = 0;
		}
	});

	//根据son勾中的个数决定father是否勾中
	/**
	 * 页面动态生成的dom元素，原先的事件会失效
	 * 解决方案:会把元素的事件绑定到其父元素中(父元素也不能是动态生成的)
	 */
	//给指定元素绑定指定事件:事件委托 参数1:事件类型 参数2:绑定的元素 参数2:触发的函数
	$('#activityListBody').on('click','.son',function () {
		$('.son').each(function () {
			//通过选择器获取勾中的son的个数
			var checkedLength = $('.son:checked').length;
			//所有son的个数
			var allLength = $('.son').length;
			if(checkedLength == allLength){
				$('#father').prop('checked',true);
			}else{
				$('#father').prop('checked',false);
			}
		});
	});
	
	//修改市场活动js
	function updateActivity() {
		var checkedLength = $('.son:checked').length;
		if(checkedLength == 0){
			layer.alert("至少选中一条记录", {icon: 5});
			return;
		}else if(checkedLength > 1){
			layer.alert("只能操作一条记录", {icon: 5});
			return;
		}else{
			//修改市场活动
			//弹出模态窗口
			$('#editActivityModal').modal('show');
			//不是jquery对象,是js对象
			var id = $('.son:checked')[0].value;
			//把id号的值赋给修改模态窗口的隐藏域，为后面修改给后台传递id号服务的
			$('#aid').val(id);
			//获取勾中的id号，查询当前市场活动
			$.post("/crm/workbench/queryById",{'id':id},function(data){

				//data:Activity对象的json串
				//获取当前市场活动的所有者
				var owner = data.owner;
				//把查询出来的数据设置到模态窗口中
				$('#edit-marketActivityName').val(data.name);
				$('#edit-startTime').val(data.startDate);
				$('#edit-endTime').val(data.endDate);
				$('#edit-cost').val(data.cost);
				$('#edit-describe').val(data.description);
				//异步查询所有者信息
				$.post("/crm/workbench/queryAllOwners",function(data){
					var content = "";
					for(var i = 0 ; i < data.length; i++){
						content += "<option value="+data[i].id+">"+data[i].name+"</option>";
					}
					//html text:innerHTML innerText
					$('#edit-marketActivityOwner').html(content);

					//让页面的所有者的下拉列表框显示的是当前市场活动对应的所有者
					//比较当前市场活动的所有者和查询出来的所有者,如果相等，把对应的option给选中即可
					$('#edit-marketActivityOwner option').each(function () {
						if($(this).val() == owner){
							$(this).prop('selected',true);
						}
					});
				},'json')
			},'json')

		}
	}

	//完成创建或者修改
	function createOrUpdate(btn) {
		var createForm = $('#createActivityForm').serialize();
		var updateForm = $('#updateActivityForm').serialize();
		if("保存"== btn.text()){
			//保存
			//serialize:表单序列化，会把表单中的所有表单项拼接成 name=..&name=..&..

			//create-marketActivityOwner
			$.post("/crm/workbench/createOrUpdate",createForm,function(data){
				if(data.ok){
					layer.alert(data.message, {icon: 6});
				}else{
					layer.alert(data.message, {icon: 5});
				}
				//关闭模态窗口
				$('#createActivityModal').modal('hide');
				//清空表单 querySelector:ECMAScript6.0才可以使用 根据选择器查找
				document.querySelector('#createActivityForm').reset();
				//刷新页面，获取最新数据
				refresh(1,3);
			},'json');
		}else{
			//修改
			$.post("/crm/workbench/createOrUpdate",updateForm,function(data){
				if(data.ok){
					layer.alert(data.message, {icon: 6});
				}else{
					layer.alert(data.message, {icon: 5});
				}
				//关闭模态窗口
				$('#editActivityModal').modal('hide');
				//清空表单 querySelector:ECMAScript6.0才可以使用 根据选择器查找
				document.querySelector('#updateActivityForm').reset();
				//刷新页面，获取最新数据
				refresh(1,3);
			},'json');
		}
	}

	$('#activityListBody').on('click','.son',function () {
		//点击son统计son勾中的个数
		selectedLength = $('.son:checked').length;
	})


	//单删和批量删除
	function deleteBatch(btn) {

		if("删除" == btn.text().replace(/\s*/g,"")){
			if(selectedLength == 0){
				layer.alert("至少选中一条记录", {icon: 5});
				return;
			}else if(selectedLength > 1){
				layer.alert("只能选中一条记录", {icon: 5});
				return;
			}else{
				//开始单删操作
				layer.msg('确定删除选中的数据吗？', {
					time: 0 //不自动关闭
					,btn: ['确定', '取消']
					,yes: function(index){
						layer.close(index);
						//删除
						deleteActivity();
					}
				});
			}
		}else if("批量删除" == btn.text().replace(/\s*/g,"")){
			//批量删除
			if(selectedLength == 0){
				layer.alert("至少选中一条记录", {icon: 5});
				return;
			}else{
				//开始批量删除操作
				layer.msg("确定删除选中的"+selectedLength+"条数据吗？", {
					time: 0 //不自动关闭
					,btn: ['确定', '取消']
					,yes: function(index){
						layer.close(index);
						//删除
						deleteActivity();
					}
				});
			}
		}

	}

	function deleteActivity() {
		//删除
		var ids = [];
		$('.son').each(function () {
			if($(this).prop('checked')){
				//push:向数组中放入元素
				ids.push($(this).val());
			}
		});
		//join:把数组内容默认以,号形式拼接成字符串，也可以指定分隔符
		//发送异步删除请求
		$.post("/crm/workbench/deleteActivity",{"ids" : ids.join()},function(data){
			if(data.ok){
				layer.alert(data.message, {icon: 6});
				refresh(1,3);
			}else{
				layer.alert(data.message, {icon: 5});
			}
		},'json')
	}

</script>
</html>