package com.doth.app.ws.security;

import java.io.*;

public class Max {


    public static void main(String []args){

        checkDetailsAreValid("16-76 54 321","AB11");
                /*
        int[] A1= {1, -2, 2, 5};
        int A = 28763;

        solution2("nice", "nicer");
        System.out.println(stringClean("meet", "met"));
*/
    }

    public static void solution1(int N) {
        int enable_print = N % 10;
        while (N > 0) {
            if (enable_print == 0 && N % 10 != 0) {
                enable_print = enable_print * 10;
            }
            else if (enable_print == 1) {
                System.out.print(enable_print + N % 10);
            }
            N = N / 10;
        }
    }

    public static int solution(int[] A) {
        // write your code in Java SE 8
        int product = 1;
        if (A.length > 0) {
            for (int i = 0; i < A.length; i++) {
                product *= A[i];
            }
        }
        if(product == 0) return 0;
        else if(product > 0) return 1;
        else return -1;
    }


    public static void solution2(int A) {
        // write your code in Java SE 8
        char[] w = ("" + A).toCharArray();
        char[] sum;
        int product = 1;
        if (w.length > 0) {
            for (int i = 0; i < w.length; i++) {
                System.out.println('5'+w[i]);
            }
        }

    }


    public static void solution2(String S, String T) {
        // write your code in Java SE 8
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int product = 1;
        for (int i = 0; i < alphabet.length; i++) {
            if(T.equals(S+alphabet[i])){
                System.out.println("ADD "+alphabet[i]);
            }
        }
    }

    private String swapStr(String str){
        char[] c = str.toCharArray();
        char temp = c[0];
        c[0] = c[1];
        c[1] = temp;
        return new String(c);
    }

    public static String stringClean(String S, String T){
        final StringBuilder sb = new StringBuilder();
        char prevChar = '\u0000';
        char rtnChar = '\u0000';
        int count = 0;

        for(int index = 0; index < S.length(); index += 1){
            char currChar = S.charAt(index);

            if (currChar != prevChar){
                    sb.append(currChar);
                prevChar = currChar;
            }else{
                count++;
                if(count == 1){
                    rtnChar = currChar;
                }
                if(count>1)sb.append(currChar);
            }
        }
        if (sb.toString().equals(T)) {
            return  "JOIN "+rtnChar;
        }
        return  null;
    }




    public static void getvalues(String s1) {
        StringBuffer sb = new StringBuffer();
        int l = s1.length();
        if (l % 2 == 0) {
            for (int i = 0; i < s1.length() - 1; i = i + 2) {
                char a = s1.charAt(i);
                char b = s1.charAt(i + 1);
                sb.append(b).append(a);
            }
            System.out.println(sb);
        } else {
            for (int i = 0; i < s1.length() - 1; i = i + 2) {
                char a = s1.charAt(i);
                char b = s1.charAt(i + 1);
                sb.append(b).append(a);
                System.out.println(sb);
            }
            sb.append(s1.charAt(l - 1));
            System.out.println(sb);
        }
    }




































    public static String checkDetailsAreValid(String accountNumber, String bankCode) {
        String returnVale = "";
        String accountNumberCopy = accountNumber.replaceAll("\\s", "");
        String bankCodeCopy = bankCode.replaceAll("\\s", "");
        System.out.println(accountNumberCopy.length() +"..."+ bankCodeCopy.length());
        if(bankCodeCopy.length() != 4 || ( (accountNumberCopy.length() != 10) || (accountNumberCopy.length() != 9) ) ){
            System.out.println(accountNumberCopy);
            return returnVale;
        }

        String result = accountNumberCopy.concat(bankCodeCopy);
        result = result.split("-")[1];
        char[] numbers = "73152486165".toCharArray();
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] resultToChar = result.toCharArray();

        int sum = 0;
        for(int i=0; i <result.length(); i++){
            if(Character.isDigit(resultToChar[i])){
                sum = sum + (Character.getNumericValue(resultToChar[i]) * Character.getNumericValue(numbers[i])) ;
            }else {
                for (int j=0; j <alphabet.length; j++) {
                    if((alphabet[j] - resultToChar[i])==0 ){
                        sum = sum + (Integer.parseInt( "1"+j))  * Character.getNumericValue(numbers[i]) ;
                    }
                }
            }
        }
        System.out.println(sum);
        if(sum%2 == 0){
            returnVale = String.valueOf(sum % 89);
        }else{
            returnVale = String.valueOf(89 - (sum % 89));
        }
        System.out.println(returnVale);
        return returnVale;
    }
























}

