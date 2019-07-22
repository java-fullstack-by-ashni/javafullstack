package com.csvreader;

import java.util.List;

public class Utility {

	public static boolean checkTotalBalance(String openingBalance,String mutation,String totalBalance) {
		double tb;
		boolean tbe=true;
		double ob=Double.valueOf(openingBalance);
		double mt=Double.valueOf(mutation);
		double tbl=Double.valueOf(totalBalance);
		if((ob >= 0) && (mt <=ob)) {
		tb=ob + mt;
		}
		else {
		 tb=ob - mt;
		}
		
		// get length of digit after decimal point of end balance  
		String[] strDecimalLenght=totalBalance.split("\\.");
        if(strDecimalLenght.length > 0) {
    	 String decimalDigits =strDecimalLenght[1];
         tb=setDecimal(decimalDigits.length(),tb);
        }
        
		if(tbl==tb) {
		  tbe=false;
		}
		return tbe;
	}
	
	public static boolean checkDuplicateTransactionRef(List<RecordDTO> list,String transactionReference) {
		
		 RecordDTO rec = list.stream()
				  .filter(record -> transactionReference.equals(record.getReference()))
				  .findAny()
				  .orElse(null);
		return rec==null?false:true;
	}
	
	public static double setDecimal(int tbLength,double tb){
		String str=String.format("%."+tbLength+"f", tb);
		return Double.valueOf(str);

	}
	
	public static void main(String[] args) {
		
		boolean tbe =checkTotalBalance("86.66","44.5","131.16");
		System.out.println(tbe);
		
		/*List<RecordDTO> list=new ArrayList<RecordDTO>();
		RecordDTO obg1=new RecordDTO();
		obg1.setReference("1001");
		RecordDTO obg2=new RecordDTO();
		obg2.setReference("1002");
		list.add(obg1);
		list.add(obg2);
		RecordDTO obj=checkDuplicateTransactionRef(list,"1002");
		System.out.println(obj);*/
		
		
	}

}