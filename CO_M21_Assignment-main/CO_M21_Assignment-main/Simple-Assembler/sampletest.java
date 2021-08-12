import java.util.*;
import java.io.*;

public class sampletest{
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String str = scn.nextLine();
        // String str = " add R0 R1 R2";
        // int i=0;
        // String s=str;
        // while(s.substring(i, i+1).equals(" ")){
        //     i++;            
        // }
        // str=str.substring(i);
        // System.out.println(str);
        // // System.out.println(s);
        // String[] arr = str.split(" ", 0);
        // // System.out.println();
        // for(String a : arr)
        //     System.out.println(a);

//        System.out.println(arr[0]+ "it's empty");
        // System.out.println(".");
        // System.out.println(arr[0]);
        // System.out.println(".");
        int num = Integer.parseInt(str);
        String imm = Integer.toBinaryString(num);
        int x= imm.length();
        for(int d=8-x; d>0; d--){
            imm="0"+imm;
        }
        System.out.println(imm);
        scn.close();
    }
}