/**
 * 
 */
package com.wsheng.aggregator.algorithm.sort.exchange;

/**
 * �������򣺵ݹ�
 * ͨ��һ�����򣬽����ż�¼�ָ�ɶ����������֣�����һ���ּ�¼�Ĺؼ��ֶ�����һ���ּ�¼�Ĺؼ���С(����Ҫѡ��һ���ο�ֵ��pivotkey)��
 * ��ɷֱ���������ּ�¼�������������Դﵽ������������
 * 
 * ���������ǣ�
 * ʹ������ָ��low��high����ֵ�ֱ�����Ϊ���е�ͷ�����е�β��Ĭ������pivotkey(����ֵ)Ϊ��һ����¼(����һ��Ԫ�ص�ֵ(��ͣ���ڱ仯)Ϊpivotkey),
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
		int tmp = list[low]; // Ĭ��ָ������ĵ�һ��ֵ��Ϊ���ᣬ��pivotkey, �Ƚ������ֵ���ο�ֵ���ݴ�����
		System.out.println();
		System.out.println("�ο�ֵ��" + tmp);
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
		 * �˳�whileѭ���� low = high,��ʱlow��high��ָ���м�ͬһλ�ã�
		 * ��Ҫ����ʱ��λ�õ�ֵ����Ϊ��һ���ο�ֵ
		 */
		list[low] = tmp; // list[high] = tmp;
		
		System.out.println("current low: " + low + " current high: " + high);
		System.out.println("After revert .... ");
		print(list);
		return low; // ���ֽ�����ֵ��Ϊ�´λ��ֵĻ��㣬��ʱlow��high��� return high;
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
