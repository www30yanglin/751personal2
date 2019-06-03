<%@ page language="java"    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title> Queue contrast
    </title>
    <script type="text/javascript"src="https://echarts.baidu.com/echarts2/doc/example/www2/js/echarts-all.js"></script>
</head>
<body>
<div id="vote" class="wrap">
        <div id="main" style="height:400px;"></div>
            <script type="text/javascript">
                var myChart = echarts.init(document.getElementById('main'));
                option = {
                    title : {
                        text: 'Queue contrast'
                    },
                    tooltip : {
                        trigger: 'axis'
                    },
                    legend: {
                        data:['PriorityBlockingQueue','BlockingQueue','LockFreePriorityQueue']
                    },
                    toolbox: {
                        show : true,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            magicType : {show: true, type: ['line', 'bar']},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    calculable : true,
                    xAxis : [
                        {
                            type : 'category',
                            boundaryGap : false,
                            //[1,100,500,1000,2000,5000,10000]
                            data :[${opData}]

                        }
                    ],
                    yAxis : [
                        {
                            type : 'value',
                            axisLabel : {
                                formatter: '{value} msec'
                            }
                        }
                    ],
                    series : [
                        {
                            name:'PriorityBlockingQueue',
                            type:'line',
                            data:[${map1}],
                            markPoint : {
                                data : [
                                    {type : 'max', name: 'fastest'},
                                    {type : 'min', name: 'slowest'}
                                ]
                            }
                        },
                        {
                            name:'BlockingQueue',
                            type:'line',
                            data:[${map2}],
                            markPoint : {
                                data : [
                                    {type : 'max', name: 'fastest'},
                                    {type : 'min', name: 'slowest'}
                                ]
                            }

                        },
                        {
                            name:'LockFreePriorityQueue',
                            type:'line',
                            data:[${map3}],
                            markPoint : {
                                data : [
                                    {type : 'max', name: 'fastest'},
                                    {type : 'min', name: 'slowest'}
                                ]
                            }

                        }
                    ]
                };

                myChart.setOption(option);
            </script>

</div>
</body>
</html>
