/**
 * 
 */
package com.wsheng.aggregator.algorithm.sort.exchange;

/**
 * �������򣺵ݹ�
 * ͨ��һ�����򣬽����ż�¼�ָ�ɶ����������֣�����һ���ּ�¼�Ĺؼ��ֶ�����һ���ּ�¼�Ĺؼ���С��
 * ��ɷֱ���������ּ�¼�������������Դﵽ������������
 * 
 * ���������ǣ�
 * ʹ������ָ��low��high����ֵ�ֱ�����Ϊ���е�ͷ�����е�β��Ĭ������pivotkey(����ֵ)Ϊ��һ����¼������һ��Ԫ�ؾ�һֱΪpivotkey��),
 * 1�����ȴ�high��ʼ��ǰ������һ��С��pivotkey�ļ�¼��pivotkey���ڵ�λ�ý��н�����
 * 2��Ȼ���low��ʼ������һ������pivotkey�ļ�¼�ʹ�ʱpivotkey����λ�ý��н���
 * �ظ�����2��ֱ��low=highΪֹ��
 * 
 * @author Josh Wang(Sheng)
 *
 * @email  josh_wang23@hotmail.com
 */
public class QuickSort {
	
	public static void quickSort(int[] list, int low, int high) {
		if (low < high) {
			int middle = getMiddle(list, low, high);
			
			quickSort(list, low, middle -1);
			
			quickSort(list, middle + 1, high);
		}
	}

	// ���������λ��
	private static int getMiddle(int[] list, int low, int high) {
		int tmp = list[low]; // ÿ��Ĭ��ָ������ĵ�һ��ֵ��Ϊ���ᣬ��pivotkey, �Ƚ������ֵ�ݴ�����
		while (low < high) {
			while (low < high && list[high] >= tmp) {
				high --; // list[high]��ֵ���ű�
			}
			list[low] = list[high]; // ������С��ֵ�ƶ����׶ˡ�
			
			System.out.println();
			print(list);
			
			while (low < high && list[low] <= tmp) {
				low ++; // list[low]��ֵ���ű�
			}
			list[high] = list[low]; // ��������ֵ�ƶ����߶ˡ�
			
			System.out.println();
			print(list);
		}
		
		/*
		 * ��Ҫ��һ�������ݴ�������ֵ�ָ��� ��Ϊ���ϵĹ�����ʵ���ǽ������ֵ
		 * ���ϵ��滻������ֵ�Ĺ��̣�debug�鿴����list�в��ϵĻ��������list�е�ĳ��Ԫ�س����ظ��Ĺ��̣�ԭ���������ֵ���滻Ϊ���е�һ��ֵ�ˣ�
		 * ��while ѭ��֮��low�Ѿ�ָ��list���м䣬
		 * ���Խ��ݴ�������ֵ�ָ���low����/high��λ��
		 * �����¼��β list[high] = tmp; ���ݴ�������ֵ���лָ��Ա��´εݹ����
		 */
		list[low] = tmp; 
		
		return low; // ���������ֵ, return high ҲOK
	}
	
	private static void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		int a[] = {49, 38, 65, 97, 76, 13, 27, 50, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18};
		System.out.println(" Before Sort");
		print(a);
		
		quickSort(a, 0, a.length-1);
		System.out.println();
		
		System.out.println(" After Sort");
		
		print(a);
	}
}
