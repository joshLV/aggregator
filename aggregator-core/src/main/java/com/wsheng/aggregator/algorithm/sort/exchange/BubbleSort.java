package com.wsheng.aggregator.algorithm.sort.exchange;


/**
 * ð��������ר������Ѳ�����������ݽ��������һ�������㷨��
 * 
 * �����������嵥��ֻ��һ��������������Ļ����������㷨�����������㷨��
 * 
 * �����������嵥�е�������������еģ���ô�����㷨�ͳ����������㷨�ˡ�
 * 
 * �����ʹ�������㷨֮ǰһ��Ҫ���ء�
 * 
 * ���㷨�ĺ���˼����ɨ�������嵥��Ѱ�ҳ������������  ���� ����Ŀ�����ҵ���������Ŀ�󣬽�����Ŀ��λ��Ȼ�����ɨ�衣
 * �ظ�����Ĳ���ֱ��������Ŀ����˳�����кá�
 * 
 * ð�ݣ�ÿһ�����򶼻��ҵ���������Ԫ�ز�������ײ�����С��Ԫ�ؾ��������ˣ��о���ð��һ����
 * 
 * 
 * @author Josh Wang(Sheng)
 *
 * @email  josh_wang23@hotmail.com
 */
public class BubbleSort {

	public static void sort(int[] a) {
		for (int i = 0; i< a.length; i++) {
			int temp = 0;
			for (int j = 0; j < a.length -i - 1; j++) {
				if (a[j] > a[j+1]) {// ǰ��Ⱥ�����ʱ��
					temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
				}
			}
		}
	}
	
	private static void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}
	
	
	public static void main(String[] args) {
		int a[] = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 5, 4, 62, 99, 98, 54, 56, 17, 18};
		System.out.println(" Before Sort");
		print(a);
		
		sort(a);
		
		System.out.println();
		
		System.out.println(" After Sort");
		
		print(a);
	}
}
