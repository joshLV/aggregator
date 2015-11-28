/**
 * 
 */
package com.wsheng.aggregator.algorithm.sort.insert;

import java.util.Random;


/**
 * 
 * ϣ��������С�������� diminishing increment sort)
 * ��С�����������зָ�����������С�
 * ������ÿ�ζ�����ָ���������е�index��ʼ���в��롣
 * 
 * 
 * �Ƚ��������ż�¼���зָ�����ɸ������зֱ����ֱ�Ӳ�������
 * �����������еļ�¼��������ʱ���ٶ�ȫ���¼����һ��ֱ�Ӳ������� 
 * 
 * @author Josh Wang(Sheng)
 *
 * @email  josh_wang23@hotmail.com
 */
public class ShellInsertionSort {
	
	
	public static void main(String[] args) {
		Random random = new Random(10);
		
		int arraysize = 5;
		double[] sorted = new double[arraysize];
		System.out.println(" ------- Before Sort --------");
		for (int j=0; j < arraysize; j++) {
			sorted[j] = random.nextDouble()*100;
			System.out.print(sorted[j] + " ");
		}
		System.out.println();
		
		ShellInsertionSort insertionSort = new ShellInsertionSort();
		insertionSort.sort(sorted);
		
		System.out.println(" ------- After Sort ---------");
		for(int j=0; j < arraysize; j++) {
			System.out.print(sorted[j] + " ");
		}
		System.out.println();
		
	}
	
	public void sort(double[] sorted) {
		int[] incs = {7,5,3,1};
		int num = incs.length;
		
		int inc = 0;
		for (int j=0; j < num; j++) {
			inc = incs[j];
			sort(sorted, inc);
		}
	}
	
	private void sort(double[] sorted, int inc) {
		int sortedLen = sorted.length;
		for (int j = inc+1; j < sortedLen; j++) {
			if (sorted[j] < sorted[j-1]) {
				sorted[0] = sorted[j];// �ȱ����Ԫ��, �ҵ�����λ�ú���лָ����롣
			//	sorted[j] = sorted[j-1];// ǰ����Ǹ�����, ��û����仰����Ӱ������������Ϊ������������(inc)���򣬶�����ֱ�Ӻ�ǰһ��Ԫ�رȽϡ�
				
				// Ѱ�Ҳ���λ��
				int insertPos = j; // j = inc+1�� Ĭ�ϴ��������λ�ÿ�ʼ����:inc+1
				for (int k = j-inc; k>=0; k-=inc) {// k = j-(inc+1)-1
					if (sorted[k] > sorted[0]) {
						sorted[k+inc] = sorted[k]; // ����ǰԪ�غ���inc������λ��
						if (k - inc <= 0) {
							insertPos = k; // ��ʱ����λ�þ���k����
						}
					} else {
						insertPos = k + inc;
						break;
					}
				}
				sorted[insertPos] = sorted[0];
			}
		}
	}
}
