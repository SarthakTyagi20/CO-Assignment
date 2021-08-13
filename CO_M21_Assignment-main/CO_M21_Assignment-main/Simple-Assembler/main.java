import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;
// import java.io.*;

class main88{

    static int[] registers = new int[8];

    static String Rcode(String R){

        if(R.equals("reg0"))
        {

            return "000";
        }
        if(R.equals("reg1"))
        {
            return "001";
        }
        if(R.equals("reg2"))
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
            {   doWeStop=true;
                return "wrong reg";}
    }

    static String[]  OPcode(String[] arr){

        String[] op = new String[2];
        String OP = arr[0];
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
            if(arr[2].subSequence(0, 1).equals("$"))
            {op[0] = "00010";
             op[1] = "B";
            }
            else{
                op[0] = "00011";
                op[1] = "C";
            }

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

        if (OP.equals("hlt"))
        {
            op[0] = "10011";
            op[1] = "F";

        }

        return op;
    }

    static void FinalA(String[] op, String[] arr ){

        String display = "";
        display = display + op[0] + "00";
        String r0 = Rcode(arr[1]);
        String r1 = Rcode(arr[2]);
        String r2 = Rcode(arr[3]);
        if((r0.equals("wrong reg"))||(r1.equals("wrong reg"))||(r2.equals("wrong reg"))){
            return;
        }

        display = display + r0 + r1 + r2;
        System.out.println(display);
    }

//    static void FinalB(String[] op, String[] arr ){
//
//        String display = "";
//        display = display + op[0];
//        String r0 = Rcode(arr[1]);
//        int num = Integer.parseInt(arr[2].substring(1));
//        //ADD ERROR FOR $
//
//        String imm = Integer.toBinaryString(num);
//        int x= imm.length();
//        for(int d=8-x; d>0; d--){
//            imm="0"+imm;
//        }
//        display = display + r0 + imm;
//        System.out.println(display);
//    }

    static void FinalB(String[] op, String[] arr){

        String display = "";
        display = display + op[0];
        String r0 = Rcode(arr[1]);
        if(r0.equals("wrong reg")){
            return;
        }
        //ADD ERROR FOR $
        if(arr[2].charAt(0)!='$'){
            System.out.println("Error! $ not inserted before an immediate value");
            doWeStop=true;
            return;
        }
        int num = Integer.parseInt(arr[2].substring(1));
        if((num<0)||(num>255)){
            System.out.println("Error! the number inserted is out of range");
            doWeStop=true;
            return;
        }

        String imm = Integer.toBinaryString(num);
        int x= imm.length();
        for(int d=8-x; d>0; d--){
            imm="0"+imm;
        }
        display = display + r0 + imm;
        System.out.println(display);
    }


    static void FinalC(String [] op , String [] arr){
        String str = "";
        str = str + op[0];
        str = str + "00000";
        String reg1 = Rcode(arr[1]);
        String reg2 = Rcode(arr[2]);
        if((reg1.equals("wrong reg"))||(reg2.equals("wrong reg"))){
            return;
        }
        str = str + reg1 + reg2;
        System.out.println(str);
    }


    static void FinalD(String [] arr , String [] op){
        String str = "";
        str = str + op[0];
        // str = str + Rcode(arr[1]);
        String r0 = Rcode(arr[1]);
        if(r0.equals("wrong reg")){
            return;
        }
        String var = arr[2];
        int idx_var_present = -1;
        boolean notPresent = true;
        for(int i =0 ; i<variables.size() ; i++){
            if(var == variables.get(i)[0]){
                notPresent = false;
                idx_var_present = i;
                break;
            }
        }
        if(!notPresent){
            int var_value_decimal = Integer.parseInt(variables.get(idx_var_present)[1]);
            String var_value_binary = Integer.toBinaryString(var_value_decimal);
            str = str + var_value_binary;
            System.out.println(str);
        }

    }



    static void FinalE(String [] op , String [] arr){
        String str = "";
        str = str + op[0];
        str = str + "000";
        int num=0;
        int flag=0; //To check if the label is present in labesls or not
        for(int i=0; i<labels_with_memory.size(); i++){
            if(arr[1]==labels_with_memory.get(i)[0]){
                // str = str + labesls.get(i);
                num = Integer.parseInt(labels_with_memory.get(i)[1]);
                flag=1;
                break;
            }
        }
        if(flag==0){
            System.out.println("Error! trying to use undefined label");
            doWeStop=true;
            return;
        }
        String mem_addr = Integer.toBinaryString(num);
        str = str + mem_addr;
        System.out.println(str);
    }


    static void FinalF(){
        System.out.println("1001100000000000");
    }


    static void FullFinal(String[] arr)    {
        String op[];
        op = OPcode(arr);
        String type = op[1];
        if(type == "A"){
            FinalA(op, arr);
        }
        if(type == "B"){
           FinalB(op, arr);
        }
        if(type == "C"){
           FinalC(op, arr);
        }
        if(type == "D"){
//            FinalD(op);
        }
        if(type == "E"){
           FinalE(op, arr);
        }
        if(type == "F"){
           FinalF();
        }
    }

    static boolean doWeStop= false;
    static ArrayList<String> labesls = new ArrayList<String>();
    static ArrayList<String[]> labels_with_memory = new ArrayList<String[]>(); 
    static ArrayList<String[]> variables = new ArrayList<String[]>();  //this vairble's any element will be {"X", "2"}

    static boolean CheckForRun(String[] arr,ArrayList<String[]> Loki ){
        //add r1 r2 r3
        //lable add r1 r2 r3
        //IsLable true Arr2 = arr[1 to n]
        boolean IsTheEnd = false;  //If true then there's some error.
        boolean[] typoLable = LabelAndOperator(arr); //{true, flase} //{IsTypo, IsLabel}

        if(typoLable[0]==true){
            if(typoLable[2]==false){
                System.out.println("there's typo in command");
                doWeStop = true;
                return doWeStop;
            } else{
                System.out.println("Invalid Use Of Label");
                doWeStop = true;
                return doWeStop;
            }

        } else{
            if(typoLable[1]==false){  //IsLabel: when there's not a label

            } else{   //when there's a label
                String[] new_arr = new String[arr.length-1];
                //arr[0] is out label
                //adding it to labels list
                int nn = arr[0].length();
                String real_label = arr[0].substring(0,nn-1);
                labesls.add(real_label);
                for(int i=0; i<new_arr.length; i++)
                {
                    new_arr[i] = arr[i+1];
                }
                arr = new_arr;
            }
        }
        FullFinal(arr);
        return doWeStop;
    }



    static  boolean[] LabelAndOperator(String[] arr){

        String A[] = {"add", "sub", "mov", "ld", "st", "mul", "div", "rs", "ls",
                    "xor", "or", "and", "not", "cmp", "jmp", "jlt", "jgt", "je", "hlt"};
        int an = A.length;
        String B[] = {"reg0","reg1","reg2","reg3","reg4","reg5","reg6","FLAGS"};
        //Two conditions either there's  a Lable or Not

        //arr[0] will be either lable or command

        String first = arr[0];
        boolean IsLable = false;  //if there's a label than true else flase
        boolean IsTypo = false;//if there's typo than true else false
        boolean InValidLabel = false;
        boolean[] typoLable = {IsTypo, IsLable, InValidLabel};


        for(int i =0; i<an; i++)
        {
            if(first == A[i]){
                IsLable = false;
                return typoLable;

            } else{
                IsLable = true;
            }
        }

        if(IsLable== true){
            String Aa = first;  //kunal:
            int n = Aa.length(); //6
            String[] arrayA = Aa.split(""); //[k,u,n,a,l,:]
            String collen = arrayA[n-1]; //5th =:
            if(collen==":"){
                String label = Aa.substring(0, n-1);
                for(int ii=0; ii<A.length; ii++)
                {
                    if(label.equals(A[ii])){
//                        System.out.println("You cannot use this Label");
                        IsTypo = true;
                        InValidLabel = true;
                    }
                }
                IsTypo = false;
            }
            else{
                IsTypo = true;
            }
        }
        return typoLable;
        //If code comes here it means there's not a typo in code.
        //if IsLabel is true then there's label and arr[1] is the operator
        //otherwise arr[0] is operator
    }

    static void MisUsedVar(ArrayList<String[]> Loki){
        int number_of_vars =0;  //this is same as line number
        //{"X", "2"}  name of label and line number
        int ii;
        int jj =0;
        String var = "var";
        boolean VarError = false;

        for(ii=0; ii<Loki.size(); ii++){

//            String[]
//            String prev = Loki.get(ii)[0];
            String curr = Loki.get(ii)[0];
            if(curr==var){
//                variables.add(Loki.get(ii)[1]); //this will add X; (var X)

            }  else{
                jj=ii;
                break;  //this loop will break when they stop entering vars
            }
        }
        //now jj in the number of line after they've stop giving vars
        //to check weather they gave vars after that i.e. illigal use of vars.


        for(int k =jj+1; k<Loki.size(); k++ )
        {
            String curr = Loki.get(k)[0];
            if(curr==var){
                System.out.println("var not used at the beginning");
                VarError = true;
                break;
            }
            else{
                //Else there's no variables in the program
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        FileReader reader = new FileReader("D:\\Sem_2\\CO\\assignment1\\Simple-Assembler\\input.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = " ";

        int count_line = 0;

        ArrayList<String[]> Loki = new ArrayList<String[]>();

        //Giving as a good array
        while ((str = br.readLine()) != null) {

            if(str.isEmpty()){
                // count_line =0;
                continue;
            }
            boolean a = true;

            //splitting intiated
            int i=0;

            while(str.substring(i, i+1).equals(" ")){
                i++;
            }

            str=str.substring(i);

            String[] ar = str.split(" ", 0);
            //splitting done
            String[] arr = new String[ar.length];

            int j=0;

            for(i=0; i<ar.length; i++){
                if(ar[i].trim().length() == 0){
                    // System.out.println("blank");
                    continue;
                }
                else{
                    arr[j]=ar[i];
                    // System.out.println("inside loop "+arr[j]);
                    j++;
                }
            }
            Loki.add(arr);
            count_line += 1;
            // System.out.println();
        }

        //Loki is here.

        int iii =0;
        for(iii=0; iii<Loki.size(); iii++)
        {
            String[] indiviual_arr = Loki.get(iii);
            boolean doWeStop = CheckForRun(indiviual_arr, Loki);
            if(doWeStop==true){
                break;
            }

        }

        br.close();
    }
}
