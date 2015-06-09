/**
 * 
 */
package com.wsheng.aggregator.programs;

import java.io.IOException;
import java.util.Scanner;


/**
 * case: ����4, 3(�б��д���һ�� �����������飺
 * ���Բ��޶�ѭ������
 * 
 * 1  2  3
 * 10 11 4
 * 9  12 5
 * 8  7	 6
 * 
 * ��������� ****�ҹ���********
 * 
 * ����1��pop��������������ʼ�𽥼��� �� �������ʹ�ù���3�㶨 while (value <= row * col)
 * pop3������ 1 2 3, 4 5 6
 * pop2������ 7 8, 9 10,
 * pop1����: 11, 12
 * 
 * ����ѭ��ִ��3�Σ�ÿ��ѭ������2��ѭ��,2��ѭ���У��ȴ����У��ٴ����� 
 * ����2�� pop��λ��
 * 
 * ����3�� pop�����ݣ���1��ʼ�𽥼�1�����ӵ�row * colʱ�˳�ѭ����
 * 
 * ����4�� pop�ķ��������У������У������У������У������У������� ... (���ٽ�ֵʱ��������,�ٽ�ֵ�𽥱仯)
 * 
 * @author Josh Wang(Sheng)
 *
 * @email  josh_wang23@hotmail.com
 */
public class PopulateSquareArray {
	public static void main(String[] args) throws IOException {
		// int row = System.in.read();
		// int col = System.in.read();
		Scanner scanner = new Scanner(System.in);
		int row = scanner.nextInt();
		int col = scanner.nextInt();
		
		System.out.println(" current row: " + row + " current col: " + col);
		
		int[][] array = populate(row, col);
		print(array);
		
		scanner.close();
	}
	
	private static void print(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] < 10)
					System.out.print(" " + array[i][j] + " "); 
				else 
					System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private static int[][] populate(int row, int col) {
		int[][] array = new int[row][col];
		
		int num = 1;
		int currentRow = 0, currentCol = 0;
		boolean forwardPop = true;
		
		for (int orientation = col; orientation >= 1; orientation--) { // ����1
			if (forwardPop) { // ����populate row and col
				// populate row
				for (int i = 0; i < orientation; i++) { 
					array[currentRow][currentCol] = num++;
					currentCol++;
				}
				currentRow ++; // ������һ��
				
				// populate col, if i == col, need to adjust the orientation from row to col
				currentCol--; // �������Խ��
				for (int j = 0; j < orientation; j++) { 
					array[currentRow][currentCol] = num++;
					currentRow++;
				}
				currentCol --; // ����ǰһ��
			} else { // ����populate row and col
				currentRow --; // �������Խ��
				for (int i = 0; i < orientation; i++) { // populate row
					array[currentRow][currentCol] = num++;
					currentCol --; 
				}
				currentRow --; // ������һ��
				
				currentCol ++ ; // �������Խ��
				for (int j = 0; j < orientation; j++) {
					array[currentRow][currentCol] = num++;
					currentRow--;
				}
				
				// ������ļӻ���
				currentRow ++ ; 
				
				currentCol ++; // ������һ��
			}
		
			forwardPop = !forwardPop;
		}
		
		return array;
	}
}
