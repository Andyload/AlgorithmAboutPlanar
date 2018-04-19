package com.qq;

import java.util.Arrays;
import java.util.Collections;

import edu.princeton.cs.algs4.Point2D;

public class AlgorithmAboutPlanar {

	public static void main(String[] args) {
		int totalNum = 20000;
		Point2D[] point2ds = new Point2D[totalNum];
		for (int num = 0; num < totalNum; num++) {
			double x = Math.random();
			double y = Math.random();
			Point2D point2d = new Point2D(x, y);
			point2ds[num] = point2d;
		}
		long startTime = System.currentTimeMillis();
		System.out.println("两点最近距离：" + getNearestDistance(point2ds));
		System.out.println("全部遍历花费时间：" + (System.currentTimeMillis() - startTime));

		System.out.println("================================================");
		startTime = System.currentTimeMillis();
		//对points数组进行x坐标从小到大排序
		Collections.sort(Arrays.asList(point2ds), Point2D.X_ORDER);
		System.out.println("排序花费时间: " + (System.currentTimeMillis() - startTime));
		System.out.println("两点最近距离：" + getNearestDistance_2(point2ds));
		System.out.println("分治法花费时间：" + (System.currentTimeMillis() - startTime));
	}
	
	private static double getNearestDistance_2(Point2D[] points) {
		if (points == null || points.length == 0 || points.length == 1) {
			return 0.0;
		}
		//记录points中两点之间最近的距离
		double nearestDistance = 0.0;

		if (points.length <= 3) {
			//判断points数组长度是否小于或等于3，如果条件满足，则直接计算两两之间的距离，例如有a,b 2个点，则直接计算ab两个点即可，如果有a,b,c 3个点，
			//则分别计算ab,bc,ac 3个点点之间的距离，然后判断最小值并记录起来
			
			nearestDistance = getDistanceBetweenPointAndPoint(points[0], points[1]);//先计算ab两个点的距离
			if (points.length == 3) {
				//如果points.length == 3代表还有ac和bc的距离还没计算
				double distance = getDistanceBetweenPointAndPoint(points[0], points[2]);//计算ac两个点的距离
				if (distance < nearestDistance) {
					nearestDistance = distance;
				}
				distance = getDistanceBetweenPointAndPoint(points[1], points[2]);//计算bc两个点的距离
				if (distance < nearestDistance) {
					nearestDistance = distance;
				}
			}
		} else {
			//如果points.length要大于3，则把points分成两个数组，每个数组的点数最多相差一个点（比如points有5个点，那就分成2和3或3和2，如果有4个点，就分成2和2）
			//达到两个数组数目将近平衡
			int leftLength = points.length / 2;
			int rightLength = points.length - leftLength;
			Point2D[] leftPoints = new Point2D[leftLength];
			Point2D[] rightPoints = new Point2D[rightLength];
			for (int index = 0; index < points.length; index++) {
				if (index < leftLength) {
					leftPoints[index] = points[index];
				} else {
					rightPoints[index - leftLength] = points[index];
				}
			}
			//⚠️这里使用了递归，通过把问题不断地分解成几个子问题，让问题变得更加简单和清晰明了
			double leftNearestDistance = getNearestDistance_2(leftPoints);//求出左边数组点之间的最近距离
			double rightNearestDistance = getNearestDistance_2(rightPoints);//求出右边数组点之间的最近距离
			if (leftNearestDistance > rightNearestDistance) {
				nearestDistance = rightNearestDistance;
			} else {
				nearestDistance = leftNearestDistance;
			}
			//算出左边数组和右边数组的最近距离是nearestDistance
			//以下逻辑是证明左边数组和右边数组是否各存在至少一个点，它们的距离要小于nearestDistance
			double mid_x = ((points[leftLength]).x() + points[leftLength - 1].x()) / 2;
			double firstX = mid_x - nearestDistance;
			double endX = mid_x + nearestDistance;
			for (int leftIndex = 0; leftIndex < leftPoints.length; leftIndex++) {
				if (leftPoints[leftIndex].x() > firstX) {
					for (int rightIndex = 0; rightIndex < rightPoints.length; rightIndex++) {
						double topY = leftPoints[leftIndex].y() + nearestDistance;
						double donwY = leftPoints[leftIndex].y() - nearestDistance;
						double rightY = rightPoints[rightIndex].y();
						if (rightPoints[rightIndex].x() < endX && rightY > donwY && rightY < topY) {
							double nearerDistance = getDistanceBetweenPointAndPoint(leftPoints[leftIndex],
									rightPoints[rightIndex]);
							if (nearerDistance < nearestDistance) {
								nearestDistance = nearerDistance;
							}
						}
					}
				}
			}
		}
		return nearestDistance;
	}
	
	// 计算两点之间的最近距离
		private static double getNearestDistance(Point2D[] points) {
			if (points == null || points.length == 0 || points.length == 1) {
				return 0;
			}
			// 坐标点的数目
			int length = points.length;
			// 记录最短距离
			double nearestDistance = -1;
			// 从角标为0的坐标点开始遍历算两点的距离
			for (int index = 0; index < length; index++) {
				for (int last_index = 0; last_index < length; last_index++) {
					if (last_index == index) {
						// 同一个坐标点不算距离
						continue;
					}
					// 获取两个坐标点的距离
					double pDistance = getDistanceBetweenPointAndPoint(points[index], points[last_index]);
					if (nearestDistance < 0 || pDistance < nearestDistance) {
						nearestDistance = pDistance;
					}
				}
			}
			return nearestDistance;
		}
		// 计算两个坐标点的距离
		private static double getDistanceBetweenPointAndPoint(Point2D point, Point2D point2) {
			// x轴的距离
			double x = point.x() - point2.x();
			// y轴的距离
			double y = point.y() - point2.y();
			// 求斜边的距离
			return Math.sqrt(x * x + y * y);
		}

}
