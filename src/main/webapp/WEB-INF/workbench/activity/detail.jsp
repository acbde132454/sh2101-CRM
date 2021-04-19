<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/crm/jquery/layer/theme/default/layer.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="/crm/jquery/layer.js"></script>


<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});
	});
	
</script>

</head>
<body>
	
	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel1">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
						<input type="hidden" id="id" />
                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="noteContent"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="updateRemark()" id="updateRemarkBtn">更新</button>
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
                    <h4 class="modal-title" id="myModalLabel">修改市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-marketActivityOwner">
                                    <option>zhangsan</option>
                                    <option>lisi</option>
                                    <option>wangwu</option>
                                </select>
                            </div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-startTime" value="2020-10-10">
                            </div>
                            <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-endTime" value="2020-10-20">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" value="5,000">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>市场活动-${activity.name} <small>${activity.startDate} ~ ${activity.endDate}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" data-toggle="modal" data-target="#editActivityModal"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.startDate}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${activity.endDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${activity.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${activity.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${activity.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${activity.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${activity.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 30px; left: 40px;" id="activityRemarkDiv">
		<div class="page-header">
			<h4>备注</h4>
		</div>

		<c:forEach items="${activity.activityRemarks}" var="activityRemark">
			<div class="remarkDiv" id="${activityRemark.id}div" style="height: 60px;">
				<img title="zhangsan" src="${activityRemark.img}" style="width: 30px; height:30px;">
				<div style="position: relative; top: -40px; left: 40px;" >
					<h5 id="${activityRemark.id}h5">${activityRemark.noteContent}</h5>
					<font color="gray">市场活动</font> <font color="gray">-</font> <b>${activity.name}</b> <small style="color: gray;"> ${activity.createTime} ${activity.createBy}</small>
					<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
						<a class="myHref" onclick="openEditRemark('${activityRemark.noteContent}','${activityRemark.id}')" href="javascript:void(0);">
							<span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span>
						</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="myHref" href="javascript:void(0);" onclick="deleteRemark('${activityRemark.id}')">
							<span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span>
						</a>
					</div>
				</div>
			</div>
		</c:forEach>
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2" placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" onclick="saveActivityRemark()">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>
</body>
<script>
	//保存市场活动备注
	function saveActivityRemark() {
		$.post("/crm/workbench/saveActivityRemark",{
			'noteContent' : $('#remark').val(),
			//js中获取EL表达式的内容
			'activityId' : '${activity.id}'
		},function(data){
			if(data.ok){
				layer.alert(data.message, {icon: 6});
				//方式一:js实现
				//见常用代码
				var activityRemark = data.t;
				//方式二:通过后台添加备注成功后把内容返回到前台，再次动态拼接页面的备注内容
				$('#remarkDiv').before("<div id="+activityRemark.id+"div class=\"remarkDiv\" style=\"height: 60px;\">\n" +
						"\t\t\t\t<img title=\"zhangsan\" src="+activityRemark.img+" style=\"width: 30px; height:30px;\">\n" +
						"\t\t\t\t<div style=\"position: relative; top: -40px; left: 40px;\" >\n" +
						"\t\t\t\t\t<h5 id="+activityRemark.id+"h5>"+activityRemark.noteContent+"</h5>\n" +
						"\t\t\t\t\t<font color=\"gray\">市场活动</font> <font color=\"gray\">-</font> <b>"+'${activity.name}'+"</b> <small style=\"color: gray;\"> ${activity.name} ${activity.createBy}</small>\n" +
						"\t\t\t\t\t<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">\n" +
						"\t\t\t\t\t\t<a class=\"myHref\" onclick=\"openEditRemark('"+activityRemark.noteContent+"','"+activityRemark.id+"')\"  href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>\n" +
						"\t\t\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;\n" +
						"\t\t\t\t\t\t<a class=\"myHref\" onclick=\"deleteRemark('"+activityRemark.id+"')\" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>\n" +
						"\t\t\t\t\t</div>\n" +
						"\t\t\t\t</div>\n" +
						"\t\t\t</div>")
				//清空文本域内容
				$('#remark').val("");

				//新生成的DOM元素js css失效了，重新再写一遍

				$(".remarkDiv").mouseover(function(){
					$(this).children("div").children("div").show();
				});

				$(".remarkDiv").mouseout(function(){
					$(this).children("div").children("div").hide();
				});
				$(".myHref").mouseover(function(){
					$(this).children("span").css("color","red");
				});

				$(".myHref").mouseout(function(){
					$(this).children("span").css("color","#E6E6E6");
				});

			}else{
				layer.alert(data.message, {icon: 5});
			}
		},'json')
	}
	//修改市场活动备注
	function openEditRemark(noteContent,id) {
		//弹出修改备注模态窗口
		$('#editRemarkModal').modal('show');
		//设置备注内容
		$('#noteContent').val(noteContent);

		//设置隐藏域的id
		$('#id').val(id);
	}

	function updateRemark() {
		//发送异步请求，更新对应的备注内容
		//点击更新按钮，修改备注内容
		$.post("/crm/workbench/updateRemark",{
			'noteContent' : $('#noteContent').val(),
			'id' : $('#id').val()
		},function(data){
			if(data.ok){
				layer.alert(data.message, {icon: 6});
				//关闭模态窗口
				$('#editRemarkModal').modal('hide');
				$("#" + $('#id').val() + "h5").html($('#noteContent').val());
			}else{
				layer.alert(data.message, {icon: 5});
			}
		},'json');
	}

	//删除备注
	function deleteRemark(id) {
		//area: ['100px', '50px']:修改窗口宽高
		layer.msg('确定删除该条吗？', {
			time: 0, //不自动关闭,
			anim: 1
			,btn: ['确定', '取消']
			,yes: function(index){
				layer.close(index);
				//删除
				$.post("/crm/workbench/deleteRemark",{
					'id' : id
				},function(data){
					if(data.ok){
						layer.alert(data.message, {icon: 6});
						//删除指定备注对应dom元素 自己删除自己
						$("#" + id + "div").remove();
					}else{
						layer.alert(data.message, {icon: 5});
					}
				},'json')

			}
		});
	}
</script>
</html>