<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="/crm/jquery/echarts.min.js"></script>
<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>

<html>
<head></head>

<body>

    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main1" style="width: 800px;height:400px;margin: 10px auto"></div>
    <div id="main2" style="width: 800px;height:500px;margin: 10px auto"></div>

    <script type="text/javascript">

        //发送异步请求
        $.post("/crm/workbench/chart/transaction",function(data){

            // 基于准备好的dom，初始化echarts实例，容器的获取只能用原生态js实现
            var myChart1 = echarts.init(document.getElementById('main1'));


            // 指定图表的配置项和数据
            var option1 = {
                title: {
                    text: '交易统计图表柱状图'
                },
                tooltip: {},
                legend: {
                    data:['交易统计']
                },
                xAxis: {
                    data: data.stage
                },
                yAxis: {},
                series: [{
                    name: '交易统计',
                    type: 'bar',
                    data: data.num
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart1.setOption(option1);

            var myChart2 = echarts.init(document.getElementById('main2'));
            var option2 = {
                title: {
                    text: '交易统计图表饼状图',
                    subtext: '纯属虚构',
                    left: 'left'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '80%',
                        data: data.pie[0].pie,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            myChart2.setOption(option2);
        },'json')

    </script>
</body>
</html>