// import javax.print.DocFlavor;

// import jdk.internal.org.jline.utils.InputStreamReader;

// import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.*;

// import jdk.internal.org.jline.utils.InputStreamReader;

public class sim {

    public static int Register_File(String reg){  //( R1 )

        if(reg.equals("R0")){
            return Registers_Values[0];
        }
        if(reg.equals("R1")){
            return Registers_Values[1];
        }
        if(reg.equals("R2")){
            return Registers_Values[2];
        }
        if(reg.equals("R3")){
            return Registers_Values[3];
        }
        if(reg.equals("R4")){
            return Registers_Values[4];
        }
        if(reg.equals("R5")){
            return Registers_Values[5];
        }
        if(reg.equals("R6")){
            return Registers_Values[6];
        }
        if(reg.equals("FLAGS")){
            return Registers_Values[7];
        }

        return -1;
    }

    public static void  Program_counter_bn(int pc){

        String mem_addr = Integer.toBinaryString(pc);
        int x= mem_addr.length();
        for(int d=8-x; d>0; d--){
            mem_addr="0"+mem_addr;
        }

        System.out.print(mem_addr + " ");

    }

    public static String[]  OP_to_Type(String opcode){
        String[] type_inst = new String[2];
        if (opcode.equals("00000"))        {
            type_inst[0] = "A";
            type_inst[1] = "add";
        }
        if (opcode.equals("00001"))        {
            type_inst[0] = "A";
            type_inst[1] = "sub";
        }
        if (opcode.equals("00010"))        {
            type_inst[0] = "B";
            type_inst[1] = "mov";
        }
        if (opcode.equals("00011"))        {
            type_inst[0] = "C";
            type_inst[1] = "mov";
        }
        if (opcode.equals("00100"))        {
            type_inst[0] = "D";
            type_inst[1] = "ld";
        }
        if (opcode.equals("00101"))        {
            type_inst[0] = "D";
            type_inst[1] = "st";
        }
        if (opcode.equals("00110"))        {
            type_inst[0] = "A";
            type_inst[1] = "mul";
        }
        if (opcode.equals("00111"))        {
            type_inst[0] = "C";
            type_inst[1] = "div";
        }
        if (opcode.equals("01000"))        {
            type_inst[0] = "B";
            type_inst[1] = "rs";
        }
        if (opcode.equals("01001"))        {
            type_inst[0] = "B";
            type_inst[1] = "ls";
        }
        if (opcode.equals("01010"))        {
            type_inst[0] = "A";
            type_inst[1] = "xor";
        }
        if (opcode.equals("01011"))        {
            type_inst[0] = "A";
            type_inst[1] = "or";
        }
        if (opcode.equals("01100"))        {
            type_inst[0] = "A";
            type_inst[1] = "and";
        }
        if (opcode.equals("01101"))        {
            type_inst[0] = "C";
            type_inst[1] = "not";
        }
        if (opcode.equals("01110"))        {
            type_inst[0] = "C";
            type_inst[1] = "cmp";
        }
        if (opcode.equals("01111"))        {
            type_inst[0] = "E";
            type_inst[1] = "jmp";
        }
        if (opcode.equals("10000"))        {
            type_inst[0] = "E";
            type_inst[1] = "jlt";
        }
        if (opcode.equals("10001"))        {
            type_inst[0] = "E";
            type_inst[1] = "jgt";
        }
        if (opcode.equals("10010"))        {
            type_inst[0] = "E";
            type_inst[1] = "je";
        }
        if (opcode.equals("10011"))        {
            type_inst[0] = "F";
            type_inst[1] = "hlt";
        }
        //will divide according to the type
        //String type_inst {"A", "add"}

        return type_inst;
    }



    public static String RF(String reg_name_bn){

        if(reg_name_bn.equals("000")){
            return "R0";
        }
        if(reg_name_bn.equals("001")){
            return "R1";
        }
        if(reg_name_bn.equals("010")){
            return "R2";
        }
        if(reg_name_bn.equals("011")){
            return "R3";
        }
        if(reg_name_bn.equals("100")){
            return "R4";
        }
        if(reg_name_bn.equals("101")){
            return "R5";
        }
        if(reg_name_bn.equals("110")){
            return "R6";
        }
        if(reg_name_bn.equals("111")){
            return "FLAGS";
        }
        return "null";

    }



    public static void Operations_typeA(String operation_name, String instruction){
        //00000 00 000 000 000
        String opcode = instruction.substring(0,5);
        String reg1 = instruction.substring(7,10);
        String reg2 = instruction.substring(10,13);
        String reg3 = instruction.substring(13);




        int Rv1 = Register_File(RF(reg1)); //R1
        int Rv2 = Register_File(RF(reg2)); //R2
        int Rv3 = Register_File(RF(reg3)); //R3


        if(operation_name.equals("add")){

            Rv1 = Rv2 + Rv3;
            if(Rv1>65535){
                String mem_addr = Integer.toBinaryString(Rv1);
                int x = mem_addr.length();

                for (int d = 16 - x; d > 0; d--) {
                    mem_addr = "0" + mem_addr;
                }

                String final_ = mem_addr.substring(mem_addr.length() - 16);
                Rv1 = Integer.parseInt(final_, 2);
                
                Registers_Values[7] = 8;
            } else{
                Registers_Values[7] = 0;
            }

            //set the register in Register_values
            //flags = 0
        }
        if(operation_name.equals("sub")){


            Rv1 = Rv2 - Rv3;

            if(Rv1<0){

                Rv1 = 0;
                Registers_Values[7] =8;

            } else{
                Registers_Values[7] = 0;
            }

            //flag = 1
        }
        if(operation_name.equals("mul")){
            //mov r1 f
            //flag =0
            Rv1 = Rv2 *Rv3;

            if(Rv1>65535){
                String mem_addr = Integer.toBinaryString(Rv1);
                int x = mem_addr.length();

                for (int d = 16 - x; d > 0; d--) {
                    mem_addr = "0" + mem_addr;
                }

                String final_ = mem_addr.substring(mem_addr.length() - 16);
                Rv1 = Integer.parseInt(final_, 2);
                
                Registers_Values[7] = 8;
            } else{
                Registers_Values[7] = 0;
            }

        }
        if(operation_name.equals("xor")){
            //mov r1 f
            //flag =0
            Rv1 = Rv2 ^ Rv3;
            Registers_Values[7] = 0;

        }
        if(operation_name.equals("or")){
            //mov r1 f
            //flag =0
            Rv1 = Rv2 | Rv3;
            Registers_Values[7] = 0;
        }
        if(operation_name.equals("and")){
            //mov r1 f
            //flag =0
            Rv1 = Rv2 & Rv3;
            Registers_Values[7] = 0;
        }

        Registers_Values[Integer.parseInt(reg1, 2)] = Rv1;

    }

    public static int Divison_of_inst(String[]  type_inst, String instruction){

        if(type_inst[0].equals("A")){

            //division of instruction 00000 00 001 010 011
            //call add
            Operations_typeA(type_inst[1], instruction);
            return program_counter_int+1;

        }
        if(type_inst[0].equals("B")){
            int line_number = Operation_typeB(type_inst[1], instruction);
            return line_number;
        }

        if(type_inst[0].equals("C")){
            int line_number = Operation_typeC(type_inst[1], instruction);
            return line_number;
        }
        if(type_inst[0].equals("D")){
            int line_number = Operation_typeD(type_inst[1], instruction);
            return line_number;
        }
        if(type_inst[0].equals("E")){

            int line_number = Operation_typeE(type_inst[1], instruction);
            return line_number;
        }


        return program_counter_int + 1;
    }
//A B C D
    public static int Operation_typeB(String operation_name, String instruction){
        String reg1 = instruction.substring(5,8);
        String num_bn = instruction.substring(8);
        int num = Integer.parseInt(num_bn, 2);
        int Rv1 = Register_File(RF(reg1));

        if(operation_name.equals("mov")){
            Rv1 = num;
            Registers_Values[7]=0;
            
        }
        if(operation_name.equals("rs")){
            Rv1 /= Math.pow(2,num);
            Registers_Values[7]=0;
            
        }
        if(operation_name.equals("ls")){
            Rv1 *= Math.pow(2,num);
            Registers_Values[7]=0;
            
        }

        Registers_Values[Integer.parseInt(reg1, 2)] = Rv1;
        return program_counter_int + 1;

    }

    public static int Operation_typeC(String operation_name, String instruction){
        String opcode = instruction.substring(0,5);
        String reg1 = instruction.substring(10,13);
        String reg2 = instruction.substring(13);

        int Rv1 = Register_File(RF(reg1)); //R1
        int Rv2 = Register_File(RF(reg2)); //R2

        if(operation_name.equals("mov")){
            Registers_Values[Integer.parseInt(reg1, 2)] = Rv2;
            Registers_Values[7]=0;
        }
        if(operation_name.equals("div")){
            int quotient = Rv1/Rv2;
            int remainder = Rv1%Rv2;
            Registers_Values[Integer.parseInt("000", 2)] = quotient;
            Registers_Values[Integer.parseInt("001", 2)] = remainder;
            Registers_Values[7]=0;
        }
        if(operation_name.equals("not")){
            Rv1 = ~Rv2;
            Registers_Values[Integer.parseInt(reg1, 2)] = Rv1;
            Registers_Values[7]=0;
        }
        if(operation_name.equals("cmp")){

            if(Rv1== Rv2){
                Registers_Values[7]=1;
            }
            if(Rv1> Rv2){
                Registers_Values[7]=2;
            }
            if(Rv1< Rv2){
                Registers_Values[7]=4;
            }
        }

        return program_counter_int + 1;
    }

    public static int Operation_typeD(String operation_name, String instruction){

        String reg1 = instruction.substring(5,8);
        String num_bn = instruction.substring(8);
        int temp_pc = Integer.parseInt(num_bn, 2);
        int Rv1 = Register_File(RF(reg1));
        if(operation_name.equals("ld")){
            Rv1 = Integer.parseInt(MEM[temp_pc]);
            Registers_Values[Integer.parseInt(reg1, 2)] = Rv1;
            Registers_Values[7]=0;            
        }
        else if(operation_name.equals("st")){
            int y = Rv1;
            String mem_addr = Integer.toBinaryString(y);
            int x = mem_addr.length();

            for (int d = 16 - x; d > 0; d--) {
                mem_addr = "0" + mem_addr;
            }


            MEM[temp_pc] = mem_addr;
            Registers_Values[7]=0;     
        }
        
        return program_counter_int + 1;
    }




    public static int Operation_typeE(String operation_name, String instruction){
        //DONE DONE DONE
        String opcode = instruction.substring(0,5);
        String memory_address = instruction.substring(8);
        int temp_ma = Integer.parseInt(memory_address, 2);


        if(operation_name.equals("jmp")){
            Registers_Values[7]=0;
            return temp_ma;

        }
        if(operation_name.equals("jlt")){

            if(Registers_Values[7]==4){
                Registers_Values[7]=0;
                return temp_ma;
            }
            else{
                Registers_Values[7]=0;
                return program_counter_int + 1;
            }

        }
        if(operation_name.equals("jgt")){

            if(Registers_Values[7]==2){
                Registers_Values[7]=0;
                return temp_ma;
            }
            else{
                Registers_Values[7]=0;
                return program_counter_int + 1;
            }

        }
        if(operation_name.equals("je")){

            if(Registers_Values[7]==1){
                Registers_Values[7]=0;
                return temp_ma;
            }
            else{
                Registers_Values[7]=0;
                return program_counter_int + 1;
            }

        }


        return program_counter_int + 1;
    }

    public static String Memory_method(String program_counter){

        int pc = Integer.parseInt(program_counter,2);
        String inst_es = MEM[pc];

        return inst_es;

    }

    public static int execution_star(String inst_es){
        //opcode
        //type check
        //add mul sub xor or
        //add r1 r2 r3
        //updating vlaues of these registers


        String opcode = inst_es.substring(0,5); //"00000"

        String[] type_inst = OP_to_Type(opcode); //{"A", "add"}

        int line_number = Divison_of_inst(type_inst, inst_es);
        
        //
        return line_number;

    }

    public static void printer_reg(){

        for(int i =0; i<Registers_Values.length ; i++) {

            int reg_in_int = Registers_Values[i];

            String mem_addr = Integer.toBinaryString(reg_in_int);
            int x = mem_addr.length();

            for (int d = 16 - x; d > 0; d--) {
                mem_addr = "0" + mem_addr;
            }

            String final_ = mem_addr.substring(mem_addr.length() - 16);
            System.out.print(final_ + " ");

        }

        System.out.println();
    }

    static int program_counter_int = 0;
    static int[] Registers_Values = new int[8];  //register file array

    static String[] MEM = new String[256];


    public static void main(String[] args) throws IOException {


        // FileReader reader = new FileReader("D:\\Sem_2\\CO\\CO-Assignment-main2\\CO_M21_Assignment-main\\CO_M21_Assignment-main\\SimpleSimulator\\input.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
//        PrintWriter writer = new PrintWriter("D:\\Sem_2\\CO\\CO-Assignment-main\\CO_M21_Assignment-main\\CO_M21_Assignment-main\\SimpleSimulator\\graph.txt", "UTF-8");

        String str = " ";

        int count_line = 0;
        int i=0;

        for(int j=0; j<256; j++){
            MEM[j]="0000000000000000";
        }

        while ((str = br.readLine()) != null) {

            if (str.isEmpty()) {
                // count_line =0;
                continue;
            }

            //splitting intiated
            str = str.trim();

            MEM[i]=str;
            i++;

        }

        //arr = [00010, 001, 00000100]
        //mov
        //MEM_odin is memory
        //Instruction = arr
        String inst = MEM[program_counter_int];

        while(!inst.equals("1001100000000000")){

            Program_counter_bn(program_counter_int);

            int line_number = execution_star(inst); //"jmp 5"

            printer_reg();
//            writer.println(program_counter_int);
            program_counter_int = line_number;
            inst = MEM[program_counter_int];
        }
//        writer.println(program_counter_int);
        Program_counter_bn(program_counter_int);
        Registers_Values[7] = 0;
        printer_reg();

        for(int j=0; j<256; j++){
            System.out.println(MEM[j]);
        }

        br.close();
    }
}
