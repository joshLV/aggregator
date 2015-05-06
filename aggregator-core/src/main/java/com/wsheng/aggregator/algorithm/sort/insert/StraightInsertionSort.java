/**
 * 
 */
package com.wsheng.aggregator.algorithm.sort.insert;

import java.util.Random;


/**
 * �����������ֱ�Ӳ�������ϣ����������
 * ֱ�Ӳ�������Ѱ�Ҳ���λ�ã�����һ����¼���뵽�Ѿ��ź����������С�
 * 1. sorted����ĵ�0��λ��û�з����ݡ�// �����1��ʼ�����ݡ�
 * 2. ��sorted�ڶ������ݿ�ʼ���� //Ҳ���Ǵ�sorted[2]��ʼ������Ϊ��0��λ��û�����ݡ�
 * 
 * �㷨���裺
 * ��������ݱ���ǰ�������ҪС��˵��������Ҫ��ǰ���ƶ�
 * a.���Ƚ������ݱ��ݵ�sorted�ĵ�0λ�õ��ڱ���
 * b.Ȼ�󽫸�����ǰ���Ǹ����ݺ��ơ�
 * c.Ȼ����ǰ�������Ҳ���λ�á����Ӻ���ǰ������
 * d.�ҵ�����λ�ú�0λ�õ��Ǹ����ݲ��뵽��Ӧ��λ�á�
 * ʱ�临�Ӷ�ΪO(n*n),�����ż�¼����Ϊ����ʱ��ʱ�临�Ӷ������O(n)
 * 
 * 
 * @author Josh Wang(Sheng)
 *
 * @email  josh_wang23@hotmail.com
 */
public class StraightInsertionSort {

	public void sort(double[] sorted) {
		int sortedLen = sorted.length;
		for (int j = 2; j < sortedLen; j++) {
			if (sorted[j] < sorted[j-1]) {
				sorted[0] = sorted[j];// �ȱ����Ԫ�أ��ҵ�����λ�ú󣬽��лָ����롣
				sorted[j] = sorted[j-1]; // ǰ����Ǹ�����
				
				// Ѱ�Ҳ���λ��, �ڵ�0��λ�õ���j-2��λ��֮�����һ��Ԫ��֮���������Ѱ�Һ��ʵĲ���λ��
				int insertPos = 0;// Ĭ���ڵ�0��λ��
				for (int k = j-2; k >=0; k--) { // j�ӵڶ���λ�ÿ�ʼ����˵�ǰ����λ��k��j-2��λ�ÿ�ʼ��
					if (sorted[k] > sorted[0]) { // �����ǰλ�û��Ǳ��ڱ�������Ȼ���ǲ���λ�ã���Ҫ��������
						sorted[k+1] = sorted[k];
					} else {
						insertPos = k + 1; // ��Ϊ���ݴ�1��ʼ�����Բ���λ��������һ��λ��
						break;
					}
				}
				sorted[insertPos] = sorted[0];
			}
		}
	}
	
	public static void main(String[] args) {
		Random random = new Random(6);
		
		int arraysize = 10;
		double[] sorted = new double[arraysize];
		System.out.println(" ------- Before Sort --------");
		for (int j=1; j < arraysize; j++) {
			sorted[j] = random.nextDouble()*100;
			System.out.print(sorted[j] + " ");
		}
		System.out.println();
		
		StraightInsertionSort insertionSort = new StraightInsertionSort();
		insertionSort.sort(sorted);
		
		System.out.println(" ------- After Sort ---------");
		for(int j=1; j < arraysize; j++) {
			System.out.print(sorted[j] + " ");
		}
		System.out.println();
		
	}
}
