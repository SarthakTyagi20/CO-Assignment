import java.util.*;
// import java.io.*;


class mains{
    static String Rcode(String R){

        if(R.equals("reg0"))
        {
            return "000";
        }
        if(R.equals("reg1"))
        {
            return "001";
        }if(R.equals("reg2"))
        {
            return "010";
        }if(R.equals("reg3"))
        {
            return "011";
        }if(R.equals("reg4"))
        {
            return "100";
        }if(R.equals("reg5"))
        {
            return "101";
        }if(R.equals("reg6"))
        {
            return "110";
        }
        if(R.equals("FLAGS"))
        {
            return "111";
        }
        else
            return "wrong r";
    }

    static String[]  OPcode(String OP){

        String[] op = new String[2];

        if (OP.equals("add"))
        {
            op[0] = "00000";
            op[1] = "A";
        }
        // if (OP.equals("add"))
        // {
        //     op[0] = "00000";
        //     op[1] = "A";
        // }
        if (OP.equals("sub"))
        {
            op[0] = "00001";
            op[1] = "A";

        }
        if (OP.equals("mul"))
        {
            op[0] = "00110";
            op[1] = "A";

        }
        if (OP.equals("xor"))
        {
            op[0] = "01010";
            op[1] = "A";

        }
        if (OP.equals("or"))
        {
            op[0] = "01011";
            op[1] = "A";

        }
        if (OP.equals("and"))
        {
            op[0] = "01100";
            op[1] = "A";

        }
        if (OP.equals("mov"))
        {
            op[0] = "00010";
            op[1] = "B";

        }
        if (OP.equals("rs"))
        {
            op[0] = "01000";
            op[1] = "B";

        }
        if (OP.equals("ls"))
        {
            op[0] = "01001";
            op[1] = "B";

        }

        return op;
    }

    static void FinalA(String[] op, String[] arr ){

        String display = "";
        display = display + op[0] + "00";
        String r0 = Rcode(arr[1]);
        String r1 = Rcode(arr[2]);
        String r2 = Rcode(arr[3]);

        display = display + r0 + r1 + r2;
        System.out.println(display);
    }

    static void FinalB(String[] op, String[] arr ){

        String display = "";
        display = display + op[0];
        String r0 = Rcode(arr[1]);
        int num = Integer.parseInt(arr[2].substring(1));
        String imm = Integer.toBinaryString(num);
        int x= imm.length();
        for(int d=8-x; d>0; d--){
            imm="0"+imm;
        }
        display = display + r0 + imm;
        System.out.println(display);
    }


    static void FullFinal(String str)
    {
        int i=0;
        String s=str;
        while(s.substring(i, i+1).equals(" ")){
            i++;
        }
        str=str.substring(i);
        // System.out.println(str);
        // System.out.println(s);
        String[] arr = str.split(" ", 0);
        
        String op[];
        op = OPcode(arr[0]);
        String type = op[1];
        if(type == "A"){

            FinalA(op, arr);
        }
        if(type == "B"){

           FinalB(op, arr);
        }
        if(type == "C"){

//            FinalC(op);
        }
        if(type == "D"){

//            FinalD(op);
        }
        if(type == "E"){

//            FinalE(op);
        }
        if(type == "F"){

//            FinalF(op);
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String s = scn.nextLine();        
        FullFinal(s);

        scn.close();
    }
}

