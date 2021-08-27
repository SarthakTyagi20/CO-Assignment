import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

// import jdk.internal.org.jline.utils.InputStreamReader;


class main8{

    static int[] registers = new int[8];

    static String Rcode(String R){

        if(R.equals("R0"))
        {

            return "000";
        }
        if(R.equals("R1"))
        {
            return "001";
        }
        if(R.equals("R2"))
        {
            return "010";
        }if(R.equals("R3"))
        {
            return "011";
        }if(R.equals("R4"))
        {
            return "100";
        }if(R.equals("R5"))
        {
            return "101";
        }if(R.equals("R6"))
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
            if(arr.length != 3){
                System.out.println("Syntax error in the instruction");
                doWeStop = true;
                return op;
            }

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
        if (OP.equals("cmp"))
        {
            op[0] = "01110";
            op[1] = "C";

        }
        if (OP.equals("not"))
        {
            op[0] = "01101";
            op[1] = "C";

        }
        if (OP.equals("div"))
        {
            op[0] = "00111";
            op[1] = "C";

        }


        if (OP.equals("ls"))
        {
            op[0] = "01001";
            op[1] = "B";

        }

        if (OP.equals("ld"))
        {
            op[0] = "00100";
            op[1] = "D";

        }
        if (OP.equals("st"))
        {
            op[0] = "00101";
            op[1] = "D";

        }
        if (OP.equals("jmp"))
        {
            op[0] = "01111";
            op[1] = "E";

        }
        if (OP.equals("jlt"))
        {
            op[0] = "10000";
            op[1] = "E";

        }
        if (OP.equals("jgt"))
        {
            op[0] = "10001";
            op[1] = "E";

        }
        if (OP.equals("je"))
        {
            op[0] = "10010";
            op[1] = "E";

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
            System.out.println("Syntax Error in instruction");
            return;
        }
        if((r0.equals("111"))||(r1.equals("111"))||(r2.equals("111"))){
            System.out.println("Illegal use of FLAGS register");
            doWeStop = true;
            return;
        }

        display = display + r0 + r1 + r2;
        System.out.println(display);
    }



    static void FinalB(String[] op, String[] arr){

        String display = "";
        display = display + op[0];
//        System.out.println("our loved ones = "+display);

        String r0 = Rcode(arr[1]);



//        System.out.println(r0);
        if(r0.equals("wrong reg")){
            System.out.println("Syntax Error in instruction");
            return;
        }
        if(r0.equals("111")){
            System.out.println("Illegal use of FLAGS register");
            doWeStop = true;
            return;
        }
        //ADD ERROR FOR $
        String r1 = arr[2].substring(1);
        boolean is_float = true;
        for(int ji =0; ji<256; ji++)
        {
            if(r1.equals(range[ji])){
                is_float = false;
                break;
            }
        }
        if(arr[2].charAt(0)!='$'){

            System.out.println("Error! $ not inserted before an immediate value");

            doWeStop=true;
            return;
        }
        if(is_float==true)
        {
            System.out.println("Invalid immediate. It should be an Integer in range [0,255]");
            doWeStop = true;
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

        String flag = "FLAGS";
        String reg1 = Rcode(arr[1]);

        String reg2 = Rcode(arr[2]);
        String  flag_arr = arr[2];

        if(reg1.equals("111")){
            System.out.println("Illegal use of FLAGS register");
            doWeStop = true;
            return;
        }

        if((reg1.equals("wrong reg")) || (reg2.equals("wrong reg"))){
//            System.out.println("#########################");
            System.out.println("Syntax Error in instruction");
            return;
        }



//        System.out.println("####################");
//        System.out.println(reg1);
//        System.out.println(reg2);

        str = str + reg1 + reg2;

        System.out.println(str);
    }

    static void FinalD(String[] op, String[] arr){
        String str = "";
        str = str + op[0];
        String reg1 = Rcode(arr[1]);
        if(reg1.equals("wrong reg")){
            System.out.println("Syntax Error in instruction");
            return;
        }
        if(reg1.equals("111")){
            System.out.println("Illegal use of FLAGS register");
            doWeStop = true;
            return;
        }
        str = str + reg1;
        int num=0;
        int flag=0;
        for(int i=0; i<variables.size(); i++){

            String c = variables.get(i)[0];  //variable's name from our list
            String varvarvar = arr[2];   //variable's name

            if(varvarvar.equals(c)){
                // str = str + labesls.get(i);
                num = Integer.parseInt(variables.get(i)[1]);
                flag=1;
                break;
            }
        }
        if(flag==0){
            System.out.println("Error! trying to use undefined variable");
            doWeStop=true;
            return;
        }
        String mem_addr = Integer.toBinaryString(num);
        int x= mem_addr.length();
        for(int d=8-x; d>0; d--){
            mem_addr="0"+mem_addr;
        }
        str = str + mem_addr;
        System.out.println(str);
    }



    static void FinalE(String [] op , String [] arr){
        String str = "";
        str = str + op[0];
        str = str + "000";
        int num=0;
        int flag=0; //To check if the label is present in labesls or not
        for(int i=0; i<labels_with_memory.size(); i++){
            String label_in_arr = arr[1];
            String label_of_Arraylist = labels_with_memory.get(i)[0];

            if(label_in_arr.equals(label_of_Arraylist)){
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
        int x= mem_addr.length();
        for(int d=8-x; d>0; d--){
            mem_addr="0"+mem_addr;
        }
        str = str + mem_addr;
        System.out.println(str);
    }


    static void FinalF(){
        System.out.println("1001100000000000");
    }


    static void FullFinal(String[] arr)    {


//        System.out.println("entering the full final ");

        String op[];
        op = OPcode(arr);
        String type = op[1];
        int arr_size = arr.length;
//        System.out.println("string type = " +type);
        if(type == "A"){
            if(arr_size!= 4){
                doWeStop = true;
                System.out.println("Syntax Error in the instruction.");
                return;
            }
            FinalA(op, arr);
        }
        if(type == "B"){
            if(arr_size!= 3){
                doWeStop = true;
                System.out.println("Syntax Error in the instruction.");
                return;
            }
            // System.out.println("entering the final B");
            FinalB(op, arr);
        }
        if(type == "C"){
            if(arr_size!= 3){
                doWeStop = true;
                System.out.println("Syntax Error in the instruction.");
                return;
            }
            FinalC(op, arr);
        }
        if(type == "D"){
            if(arr_size!= 3){
                doWeStop = true;
                System.out.println("Syntax Error in the instruction.");
                return;
            }
            FinalD(op, arr);
        }
        if(type == "E"){
            if(arr_size!= 2){
                doWeStop = true;
                System.out.println("Syntax Error in the instruction.");
                return;
            }
            FinalE(op, arr);
        }
        if(type == "F"){
            if(arr_size!= 1){
                doWeStop = true;
                System.out.println("Syntax Error in the instruction.");
                return;
            }
//            System.out.println("**");
            FinalF();
        }
    }

    static boolean doWeStop= false;
    static ArrayList<String> labesls = new ArrayList<String>();
    static ArrayList<String[]> labels_with_memory = new ArrayList<String[]>(); //some element in this array = {"label", "5"}
    static ArrayList<String[]> variables = new ArrayList<String[]>();  //this vairble's any element will be {"X", "2"} = tomAndJerry
    static int number_of_vars;
    static int count_lines;
    static  String[] range = new String[256];


    static boolean CheckForRun(String[] arr,ArrayList<String[]> Loki ){

//        System.out.println("this is check for run.............");
        //add r1 r2 r3
        //lable add r1 r2 r3
        //IsLable true Arr2 = arr[1 to n]

//        System.out.println("entered the check for run");
        boolean IsTheEnd = false;  //If true then there's some error.
        boolean[] typoLable = LabelAndOperator(arr); //{true, flase} //{IsTypo, IsLabel}
//        System.out.println("..........");
//        System.out.println(typoLable[1]);
//        System.out.println("is typo + " + typoLable[0]);
//        System.out.println("is label + " +typoLable[1]);

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

//            System.out.println("/////////////in the else");
            if(typoLable[1]==false){  //IsLabel: when there's not a label
//                System.out.println("all okay");
//                System.out.println(":::::::::::::::::::::::::::::");

            } else{
//                System.out.println("**did we came here?");
                //when there's a label
                String[] new_arr = new String[(arr.length)-1];
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

//                System.out.println("YYYYYYYYYYYYYYY");
//                System.out.println(arr[0]);
            }
        }


//        System.out.println("after this is full final");

        FullFinal(arr);
//        System.out.println("2222222222222222222");

//        System.out.println("after the fullfinal ");
//        System.out.println(doWeStop);
        return doWeStop;
    }

    static void labels_with_memory_maker(ArrayList<String[] > Loki){
        //This function will tell us if there is typo in label
        //This function will run in main before everything
        //Let's call this a Odin Function it'll run before Loki

//        System.out.println("now we are inside of label maker");


//        System.out.println("-----------------");

        boolean InValidLabel_for_loki = false;
//        System.out.println(Loki.size());
        for(int ii=0; ii<Loki.size(); ii++)
        {
            String[] arr = Loki.get(ii); //{"label:", "add", "reg0","reg1","reg2"}

            //here we will check label is correct or not i.e. is there collen at the end?
            String A[] = {"add", "sub", "mov", "ld", "st", "mul", "div", "rs", "ls", "var",
                    "xor", "or", "and", "not", "cmp", "jmp", "jlt", "jgt", "je", "hlt"};
            String AA[] = {"add", "sub", "mov", "ld", "st", "mul", "div", "rs", "ls", "var",
                    "xor", "or", "and", "not", "cmp", "jmp", "jlt", "jgt", "je", "hlt", "R0","R1","R2","R3","R4","R5","R6","FLAGS"};
            int an = A.length;

//            System.out.println("////////////////");
//            System.out.println(arr.length);
//            System.out.println(arr[0]);
//            System.out.println(arr[1]);


            String first = arr[0];
//
            boolean second_syntax_error = false;

            boolean IsLable = true;  //if there's a label than true else flase
            boolean IsTypo = false;//if there's typo than true else false
            boolean InValidLabel = false;
            boolean[] typoLable = {IsTypo, IsLable, InValidLabel};

//            System.out.println("so this is first = " + first);
//            System.out.println(first);

            for(int i =0; i<A.length; i++)
            {
                if(first.equals(A[i])){

                    IsLable = false;
//                    System.out.println("got it "+ first + " is first and "+ A[i] + " is A[i]");
                    break;

                }
            }
//            System.out.println("this is Label + " + IsLable);
//            System.out.println("~~~~");
//            System.out.println(IsLable);

            if(IsLable == true){
                //kunal:
//                System.out.println(")))))))))))))))))))))))))))");
////                System.out.println("is label should be false, is it?");
                String Aa = first;  //kunal:

                int n = Aa.length(); //6

                String[] arrayA = Aa.split(""); //[k,u,n,a,l,:]

                String collen = arrayA[n-1]; //5th =:

                if(collen.equals(":")){


                    String label = Aa.substring(0, n-1);
                    //checking if label is made of any commands like add, mul or div

                    for(int iii=0; iii<AA.length; iii++)
                    {

                        if((label.equals(AA[iii]) )|| (!label.matches("[A-Za-z0-9_]+")) ){
//                            System.out.println("You cannot use this Label");
                            IsTypo = true;
                            InValidLabel = true;
                            InValidLabel_for_loki = true;

                            System.out.println("Line number = " + (ii+1));
                            System.out.println("You cannot use this label. Illegal Label name.");
                            doWeStop = true;


                            break;

                        }


                    }
                    if(IsLable == true && InValidLabel == true && InValidLabel_for_loki == true){
                        break;
                    }

//                    System.out.println(doWeStop);
//                    System.out.println("^^^^^^^^^^^^^^^^^^");
//                    System.out.println(InValidLabel_for_loki);
                    if(arr.length == 1){
                        System.out.println("Line number " + (ii +1));
                        System.out.println("Syntax error in label.");
                        doWeStop = true;
                        break;
                    }
                    if(InValidLabel_for_loki == false){


                        //now we are sure that there's a label and it's a valid so now we will check if arr[1]
                        //that is in this valid label case will be our operator that is add or mul ect

                        //checking wheather arr[1]= second has valid sytax or not

                        String second = arr[1]; //

                        for(int ij = 0; ij<A.length; ij ++){

                            if(second.equals(A[ij])){

                                second_syntax_error = true;
                                break;
                            }

                        }

//                        System.out.println("????????????");
//                        System.out.println(second_syntax_error);

                        if(second_syntax_error == false)
                        {
                            System.out.println("Line number " + (ii+1));
                            System.out.println("Error!!! There is typo in instruction name.");
                            doWeStop = true;
                            break;
                            //this means after label the syntax of operator is not correct

                        }
                        //so now label is correct and command after label is also correct so now we will save label and it's line number

                        String[] label_memory = new String[2];
//                        System.out.println("NNNNNNNNNNNNNNNNN");

                        for(int rk =0 ; rk<labels_with_memory.size(); rk++){
                            String label_name = labels_with_memory.get(rk)[0];

                            if(label.equals(label_name)){
                                System.out.println("Error!! This label is already defined before. Line number " + (ii+1));
                                doWeStop = true;
                                break;
                            }
                        }

                        label_memory[0] = label;
//                        System.out.println(label);
                        label_memory[1] = Integer.toString(ii-number_of_vars);
//                        System.out.println(label_memory[1]);
                        labels_with_memory.add(label_memory);

                    }
                    else{
//                        System.out.println("%%%%%%%%%%%%%%%");
                        System.out.println("Line number " + (ii+1));
                        System.out.println("Error!!! There's typo in label.");
                        doWeStop = true;
                        break;
                    }



                }
                else{
                    System.out.println("Line number " + (ii+1));
                    System.out.println("Error!!! Sytax error in label declaration.");
//                    System.out.println("ttttttttttttttttttttttttttttt");
                    InValidLabel_for_loki = true;
                    IsTypo = true;

                    doWeStop = true;
                    break;
                }
            }


        }
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(doWeStop);

//        System.out.println("this is end of label maker");
//        System.out.println("at the end of label maker value of doWeStop + " );
//        System.out.println(doWeStop);
    }

    static  boolean[] LabelAndOperator(String[] arr){

        String A[] = {"add", "sub", "mov", "ld", "st", "mul", "div", "rs", "ls", "var",
                "xor", "or", "and", "not", "cmp", "jmp", "jlt", "jgt", "je", "hlt"};
        int an = A.length;
//        String B[] = {"reg0","reg1","reg2","reg3","reg4","reg5","reg6","FLAGS"};
        //Two conditions either there's  a Lable or Not

        //arr[0] will be either lable or command

        String first = arr[0];


        boolean IsLable = false;  //if there's a label than true else flase
        boolean IsTypo = false;//if there's typo than true else false
        boolean InValidLabel = false;
        boolean[] typoLable = {IsTypo, IsLable, InValidLabel};


        for(int i =0; i<an; i++)
        {

            if(first.equals(A[i])){
                IsLable = false;

                return typoLable;

            } else{
                IsLable = true;
            }
        }
//        System.out.println("@@@@@@@@@@@@@");

//        System.out.println(IsLable);

        if(IsLable== true){

//            System.out.println("9999999999999999");
            String Aa = first;  //kunal:
            int n = Aa.length(); //6
            String[] arrayA = Aa.split(""); //[k,u,n,a,l,:]
            String collen = arrayA[n-1]; //5th =:

//            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
//            System.out.println(collen);
            if(collen.equals(":")){
                String label = Aa.substring(0, n-1);

                for(int ii=0; ii<A.length; ii++)
                {

                    if(label.equals(A[ii])){
//                        System.out.println("You cannot use this Label");
                        IsTypo = true;
                        InValidLabel = true;
                        break;
                    }
                }

//                IsTypo = false;
            }
            else{
//                System.out.println("33333333333333333");
                IsTypo = true;
            }
        }

        typoLable[0] = IsTypo;
        typoLable[1] = IsLable;
        typoLable[2] = InValidLabel;

//        System.out.println("8888888888888888888888");
//        System.out.println(typoLable[1]);
//        System.out.println(typoLable[0]);
//        System.out.println(typoLable[2]);
        return typoLable;
        //If code comes here it means there's not a typo in code.
        //if IsLabel is true then there's label and arr[1] is the operator
        //otherwise arr[0] is operator
    }
    //https://www.baeldung.com/java-check-string-number
    static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    static void MisUsedVar(ArrayList<String[]> Loki) {

//        int number_of_vars =0;  //this is same as line number
        ArrayList<String[]> vars = new ArrayList<String[]>();

        //{"X", "2"}  name of label and line number


        //making the arraylist of variables
        int ii;
        int jj = 0;
        String var = "var";
        boolean VarError = false;

        for (ii = 0; ii < Loki.size(); ii++) {

            String[] tomAndJerry = new String[2];    //{"X", "2"}

            String curr = Loki.get(ii)[0];

            if (curr.equals(var)) {
                if(Loki.get(ii).length != 2){
                    System.out.println("Error on line number " + (ii+1));
                    System.out.println("Syntax error in the instruction");

                    doWeStop = true;

                    return;
                }

//                System.out.println("''''''''''''''''''");
                tomAndJerry[0] = Loki.get(ii)[1];
                String num_before_hlt = Integer.toString(ii);
                tomAndJerry[1] = num_before_hlt;

                vars.add(tomAndJerry); //this will add X; (var X)

            } else {
                jj = ii;
//                System.out.println("this is number of variables " + jj+1);
                break;  //this loop will break when they stop entering vars
            }
        }

        // now jj in the number of line after they've stop giving vars
        // to check weather they gave vars after that i.e. illigal use of vars.
//        System.out.println("we calculated the number of vars");

        for (int k = jj; k < Loki.size(); ++k) {
            String curr = Loki.get(k)[0];

            if (curr.equals(var)) {

//                System.out.println("var not used at the beginning, this illigal var is used at line number = " + k);
                VarError = true;
                System.out.println("there's a variabel on line " + (k + 1));
                System.out.println("All variable must be declared at the begining");
                doWeStop = true;
                break;
            }


        }
        String A[] = {"add", "sub", "mov", "ld", "st", "mul", "div", "rs", "ls", "var",
                "xor", "or", "and", "not", "cmp", "jmp", "jlt", "jgt", "je", "hlt", "R0","R1","R2","R3","R4","R5","R6","FLAGS"};

        if(VarError == false){
            for(int i =0; i<vars.size(); i++ ){
                //i = tomAndJerry

                String[]  tomJerry = vars.get(i);
                String var_name = tomJerry[0];

                for(int j =0; j<A.length; j++){
                    if(var_name.equals(A[j])){
                        VarError = true;
                        doWeStop = true;
                        break;

                    }
                    if(isNumeric(var_name)){
                        VarError = true;
                        doWeStop= true;
                        break;
                    }
                    if(!var_name.matches("[A-Za-z0-9_]+")){
                        VarError = true;
                        doWeStop = true;
                        break;
                    }

                }
                if(VarError==true){
                    System.out.println("Error!! Illegal use of Variable name on line "+ (i+1));
                    doWeStop = true;
                    break;
                }

            }
        }
//        System.out.println("there's no other variables");
//      6  0 var x
//      7  1 var y
//      8  2 var z

//      0  3 cmp R1 R2        jj = 3
//      1  4 var k            k =4
//      2  5 jgt label

//      3  6 cmp R3 R4
//      4  7 move r1 r3
//      5  8 label: hlt

        //total instruction = length 0f Loki
        //Loki.size = 9
        //jj = 3
        //so there are jj number of vars
        //so now numbering of instruction i.e. number of cmp R1 R2 will be consider 0
        //so size - jj is 6
        //so memory address of first var will be 6

        //now we have [{"x", "0"},{"y", "1"},{"z", "2"}]
        //adding size-jj to all above
//        System.out.println("* "+ jj);

//        System.out.println("///////////////" + number_of_vars);

        if (VarError == false) {
            number_of_vars = jj;
//        System.out.println( number_of_vars);
//        System.out.println("Loki size = "+ Loki.size());
            int size_Loki = Loki.size();
            int adder_to_tomandjerry = size_Loki - jj;
//        System.out.println("----- "+ adder_to_tomandjerry);


            for (int i = 0; i < vars.size(); i++) {
                String[] adding_to_variables = new String[2];

                String[] indi_vars = vars.get(i); // {"X", "2"}
                //now adding adder_to_tomandjerry to 2
//            int line_number = indi_variables[1];

                int num = Integer.parseInt(indi_vars[1]);

                String var_name = indi_vars[0];
                int line_of_var = num + adder_to_tomandjerry;
                // System.out.println(line_of_var);

                for(int rk =0; rk<variables.size(); rk++){
                    if(var_name.equals(variables.get(rk)[0])){
                        System.out.println("Error!! Can't repeat names of the variables. Line number " + (i+1));
                        doWeStop = true;
                        break;
                    }
                }

                adding_to_variables[0] = var_name;
                adding_to_variables[1] = Integer.toString(line_of_var);

                variables.add(adding_to_variables);
            }

//        System.out.println("sizzzzzzz "+ variables.size());
//        String[] arr = variables.get(0);
//        System.out.println(variables.get(0)[0] + " " + variables.get(0)[1]);
//        System.out.println("we added variables in that ArrayList");

        }
    }
    static void here_goes_hlt(ArrayList<String[]> Loki){
        int size_loki = Loki.size();
        boolean hlt_received = false;
        for(int i =0; i<Loki.size(); i++)
        {
            String[] arr = Loki.get(i);

            //if there's a hlt in the Loki then the size of arr must be either 1 or 2.
            //if size if 1 then it must be hlt
            //if size if 2 then it can be other functions also so we will check for label

            int size_arr = arr.length;

            if(size_arr<=2){
                if((size_arr==1) && (arr[0].equals("hlt")) ){
                    //This one must be hlt
                    //we have already checked for error in command's name in the function labels_with_memory_maker
                    if(i+1 != size_loki){
                        //this means that hlt is not at the end so
                        System.out.println("Line number " + (i+1));
                        System.out.println("hlt NOT used at the end.");
                        hlt_received = true;
                        doWeStop = true;
                        break;
                    } else{
                        //so this means it's at the end and making
                        if(hlt_received == true)
                        {
                            //it means that hlt is already in above function
                            System.out.println("Line number "+ (i+1));
                            System.out.println("There's already a hlt in function.");
                            doWeStop = true;
                            break;
                        } else{
                            //hlt is not recived before so making it true
                            hlt_received = true;
                        }
                    }

                }
                else if(size_arr ==2){

                    //now this can be anything so we will check wheather it's hlt with label or not
                    //so if arr[1] is hlt then there's hlt with label
                    //other wise it's another function

                    if(!arr[1].equals("hlt")){
                        continue;
                    } else if(arr[1].equals("hlt")){

                        if(i+1 != size_loki){
                            hlt_received = true;  //QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ
                            //this means that hlt is not at the end so
                            System.out.println("Line number " + (i+1));
                            System.out.println("hlt NOT used at the end.");
                            doWeStop = true;
                            break;
                        } else{
                            if(hlt_received == true)
                            {
                                //it means that hlt is already in above function
                                System.out.println("Line number "+ (i+1));
                                System.out.println("There's already a hlt in function.");
                                doWeStop = true;
                                break;
                            } else{
                                //hlt is not recived before so making it true
                                hlt_received = true;
                            }
                        }

                    }
                }
            }

        }
        if(hlt_received==true){

        } else{
            int n = Loki.size() + 1;
            System.out.println("Line number " +n);
            System.out.println("Missing hlt instruction");
            doWeStop = true;

        }
    }

// In MisUsedVariable we assumed that there's only one hlt function and made the variabels
// In labbels_with_memory_maker we used the number_of_variables's value that we got from above function

// In here_goes_hlt we made checked for double hlt function if there's some error in hlt it would mean that
// there's error in variabels it would mean that there's error in checking label it would mean there's error in checking hlt
//and we are in loop
// for this to end we have to enter values and check




    public static void main(String[] args) throws Exception {
        // FileReader reader = new FileReader("D:\\Sem_2\\CO\\CO-Assignment-main2\\CO_M21_Assignment-main\\CO_M21_Assignment-main\\Simple-Assembler\\input33.txt");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        for(int ijj =0; ijj <=255; ijj++){

            String  a = Integer.toString(ijj);
            range[ijj] = a;


        }

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

//            while(str.substring(i, i+1).equals(" ") ){
//                i++;
//            }

//            str=str.substring(i);

//            String[] ar = str.split(" ", 0);
//            //splitting done
//            String[] arr = new String[ar.length];
//
//            int j=0;
//
//            for(i=0; i<ar.length; i++){
//                if(ar[i].trim().length() == 0){
//                    // System.out.println("blank");
//                    continue;
//                }
//                else{
//                    arr[j]=ar[i];
//                    // System.out.println("inside loop "+arr[j]);
//                    j++;
//                }
//            }

            str = str.replaceAll("\t", " ");
            str = str.trim();

            str = str.replaceAll("( )+", " ");

            String[]  arr = str.split(" ");

            Loki.add(arr);
            count_line += 1;
            // System.out.println();
        }

        //Loki is here.

//        System.out.println("code starts from here");

        //After MisUsedVar will come labels_with_memory_maker this,
        //that also will implemented same as MisUsedVar.
        //First run that function and check for doWeStop
        //And After that we will check for hlt same as above method
        //if Loki passes all three function then we will run the programm

        MisUsedVar(Loki);
//        System.out.println("did we reach here");
//        System.out.println(doWeStop);

        if(doWeStop == false) {
//            System.out.println("this is after MisUsedVar");
//            System.out.println("-------"+number_of_vars);
            labels_with_memory_maker(Loki);

//            System.out.println("this is after label maker");
//            System.out.println(doWeStop);
//            System.out.println("]]]]]" + number_of_vars);

            if(doWeStop == false) {

//                    System.out.println("so label maker went well");

//                    System.out.println("this is before hlt");
                here_goes_hlt(Loki);
//                    System.out.println("this is after hlt");
//                    System.out.println("knew it");
//                    System.out.println(doWeStop);
                if(doWeStop == false) {
//                        System.out.println(Loki.size());
//                        System.out.println("enter the dragon");
                    int iii = 0;
                    for (iii = 0; iii < Loki.size(); iii++) {

//                            System.out.println("where did it happed bro");

                        String[] indiviual_arr = Loki.get(iii);

                        boolean doWeStop = CheckForRun(indiviual_arr, Loki);

//                            System.out.println(doWeStop+"^^^^^^^^^^^^");
//                            System.out.println("after checkfor run");
//                            System.out.println("0000000000000000");
//                            System.out.println(doWeStop);

                        if (doWeStop == true) {
                            System.out.println("Line number is "  +(iii+1) );
//                                System.out.println("here it did happen");
                            break;
                        }

                    }
                }
            }
        }

        br.close();
    }
}
