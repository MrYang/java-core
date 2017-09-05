package com.zz.algorithm;

public class Sort {

    private int[] array = new int[]{10, 3, 5, 20, 45, 7, 26, 78, 26, 35};

    /**
     * 基本思想：在要排序的一组数中，假设前面(n-1)[n>=2] 个数已经是排
     * 好顺序的，现在要把第n个数插到前面的有序数中，使得这n个数
     * 也是排好顺序的。如此反复循环，直到全部排好顺序。
     * 时间复杂度O(n^2), 空间复杂度O(1)
     */
    public void insertSort() {
        int temp = 0;
        for (int i = 1; i < array.length; i++) {
            int j = i - 1;
            temp = array[i];
            for (; j >= 0 && temp < array[j]; j--) {
                array[j + 1] = array[j];  //将大于temp的值整体后移一个单位
            }
            array[j + 1] = temp;
        }
    }

    /**
     * 算法先将要排序的一组数按某个增量d（n/3,n为要排序数的个数）分成若干组，每组中记录的下标相差d.
     * 对每组中全部元素进行直接插入排序，然后再用一个较小的增量（d/3）对它进行分组，
     * 在每组中再进行直接插入排序。当增量减到1时，进行直接插入排序后，排序完成。
     * 插入排序上进行的改进，在插入排序中，如果一个较小的数字在后面的话，移动起来会很浪费时间，
     * 希尔排序的思路是通过设置不为1的间距，将一些较小的数字移动的前面，从而减少移动的次数
     */
    public void shellSort() {
        int h = 1;
        while (h < array.length / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < array.length; i++) {
                for (int j = i; j >= h && array[j] < array[j - h]; j -= h) {
                    exch(j, j - h);
                }
            }
            h = h / 3;
        }
    }

    /**
     * 在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
     * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
     * 时间复杂度O(n^2), 空间复杂度O(1)
     */
    public void selectSort() {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            exch(min, i);
        }
    }

    /**
     * 堆排序是一种树形选择排序，是对直接选择排序的有效改进。
     * 堆的定义如下：具有n个元素的序列（h1,h2,...,hn),当且仅当满足（hi>=h2i,hi>=2i+1）
     * 或（hi<=h2i,hi<=2i+1）(i=1,2,...,n/2)时称之为堆。在这里只讨论满足前者条件的堆。
     * 由堆的定义可以看出，堆顶元素（即第一个元素）必为最大项（大顶堆）。
     * 完全二叉树可以很直观地表示堆的结构。堆顶为根，其它为左子树、右子树。
     * 初始时把要排序的数的序列看作是一棵顺序存储的二叉树，调整它们的存储序，使之成为一个堆，
     * 这时堆的根节点的数最大。然后将根节点与堆的最后一个节点交换。然后对前面(n-1)个数重新调整使之成为堆。
     * 依此类推，直到只有两个节点的堆，并对它们作交换，最后得到有n个节点的有序序列。
     * 从算法描述来看，堆排序需要两个过程，一是建立堆，二是堆顶与堆的最后一个元素交换位置。
     * 所以堆排序有两个函数组成。一是建堆的渗透函数，二是反复调用渗透函数实现排序的函数。
     */
    public void heapSort() {

    }

    /**
     * 在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，
     * 让较大的数往下沉，较小的往上冒。即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
     * 时间复杂度O(n^2), 空间复杂度O(1)，
     */
    public void bubbleSort() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length - 1; j++) {
                if (array[j] > array[j + 1]) {
                    exch(j, j + 1);
                }
            }
        }
    }

    /**
     * 选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,
     * 一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分。
     */
    public void quickSort() {

    }

    /**
     * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，
     * 即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列。
     */
    public void mergeSort() {
    }

    /**
     * 基数排序
     * 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。然后，从最低位开始，
     * 依次进行一次排序。这样从最低位排序一直到最高位排序完成以后,数列就变成一个有序序列。
     */
    public void raidxSort() {
    }

    private void exch(int i, int j) {
        int t = array[j];
        array[j] = array[i];
        array[i] = t;
    }

    public static void main(String[] args) {
        Sort s = new Sort();
        s.quickSort();

        for (int a : s.array) {
            System.out.println(a);
        }
    }
}
