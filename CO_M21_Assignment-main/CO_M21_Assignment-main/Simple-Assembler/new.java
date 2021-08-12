import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
// import java.io.*;

class new1{
//     static String Rcode(String R){
//         if(R.equals("R0"))
//         {
//             return "000";
//         }
//         if(R.equals("R1"))
//         {
//             return "001";
//         }if(R.equals("R2"))
//         {
//             return "010";
//         }if(R.equals("R3"))
//         {
//             return "011";
//         }if(R.equals("R4"))
//         {
//             return "100";
//         }if(R.equals("R5"))
//         {
//             return "101";
//         }if(R.equals("R6"))
//         {
//             return "110";
//         }
//         if(R.equals("FLAGS"))
//         {
//             return "111";
//         }
//         else
//             return "wrong r";
//     }

//     static String[]  OPcode(String OP){
//         String[] op = new String[2];
//         if (OP.equals("add"))
//         {
//             op[0] = "00000";
//             op[1] = "A";
//         }
        
//         return op;
//     }

//     static void FinalA(String str){
//         //add R0 R1 R2
//         int i=0;
//         String s=str;
//         while(s.substring(i, i+1).equals(" ")){
//             i++;
//         }
//         str=str.substring(i);
//         // System.out.println(str);
//         // System.out.println(s);
//         String[] A = str.split(" ", 0);

//         String[] op = OPcode(A[0]);
//         String inst = op[1];
//         // System.out.println(inst);

//         String opc = op[0];
//         // System.out.println(opc);

//         // System.out.println(A[1]);
//         String r0 = Rcode(A[1]);
//         String r1 = Rcode(A[2]);
//         String r2 = Rcode(A[3]);

//         String display = opc + "00" + r0+r1+r2;
//         System.out.println(display);
//     }



    // static void FullFinal(String str)
    // {

    // }

    public static void main(String[] args) throws Exception {
		FileReader reader = new FileReader("C:/file.txt");
		BufferedReader br = new BufferedReader(reader);
		String str = "";
		while ((str = br.readLine()) != null) {
            if(str.isEmpty()){
                continue;
            }
			System.out.println(str);
		}
		br.close();
	}
}

