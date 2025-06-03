package com.example.vitalsense.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.min

/**
 * 动画曲线图表
 * 
 * @param dataPoints 数据点列表
 * @param color 曲线颜色
 * @param modifier 修饰符
 * @param animationSpec 动画规格
 */
@Composable
fun AnimatedChart(
    dataPoints: List<Float>,
    color: Color,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 1000)
) {
    // 没有数据点时不绘制
    if (dataPoints.isEmpty()) return
    
    // 创建动画进度
    val animatedProgress = remember { Animatable(0f) }
    
    // 启动进度动画
    LaunchedEffect(key1 = dataPoints) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(1f, animationSpec)
    }
    
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        
        val maxValue = dataPoints.maxOrNull() ?: 1f
        val minValue = dataPoints.minOrNull() ?: 0f
        val range = maxOf(maxValue - minValue, 1f)
        
        // 计算每个数据点的x坐标
        val xStep = width / (dataPoints.size - 1)
        
        // 创建路径
        val path = Path()
        val firstPoint = dataPoints.first()
        val firstY = height - ((firstPoint - minValue) / range) * height * animatedProgress.value
        
        path.moveTo(0f, firstY)
        
        dataPoints.forEachIndexed { index, point ->
            // 目标点，根据数据计算
            val targetX = index * xStep
            val targetY = height - ((point - minValue) / range) * height * animatedProgress.value
            
            // 如果是第一个点，就直接移到位置
            if (index == 0) {
                path.moveTo(targetX, targetY)
            } 
            // 否则使用曲线连接
            else {
                val previousX = (index - 1) * xStep
                val previousY = height - ((dataPoints[index - 1] - minValue) / range) * height * animatedProgress.value
                
                val controlX1 = previousX + xStep / 3
                val controlX2 = targetX - xStep / 3
                
                path.cubicTo(
                    controlX1, previousY, 
                    controlX2, targetY, 
                    targetX, targetY
                )
            }
        }
        
        // 绘制曲线
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 3f)
        )
        
        // 绘制数据点
        val pointRadius = min(width, height) * 0.01f
        dataPoints.forEachIndexed { index, point ->
            val x = index * xStep
            val y = height - ((point - minValue) / range) * height * animatedProgress.value
            
            drawCircle(
                color = color,
                radius = pointRadius,
                center = Offset(x, y)
            )
        }
    }
}
