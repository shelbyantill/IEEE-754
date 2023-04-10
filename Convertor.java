
package CSC4101Assignment2;

/**
 * @author shelbyantill
 */
public class Convertor {
    public static void main(String[]args){
        IEEE754(.25);
//        System.out.println("0 01111111 10000000000000000000000");
//        System.out.println("0 01111111111 1000000000000000000000000000000000000000000000000000");
    }
    
    public static void IEEE754(double d){
        String s;
        String exp32="";
        String frac32="";
        String exp64="";
        String frac64="";
        
        if(d<0){
           s="1";
           d=-d;
        }
        else if (d==0){
            s="0";
            
            for(int i = 0; i<11;i++){
                exp64+="0";
            }
            for (int i = 0; i<52;i++){
                frac64 +="0";
            }
            
            exp32= exp64.substring(3,11);
            frac32 = frac64.substring(0,23);
        }
        else{
           s="0";
        }
        
        if(d<1 && d!=0){
            
            int exp = getDenormExp(d);
            
            exp32 = getWholeBinary(exp+127).substring(3,11); //only 8 
            exp64= getWholeBinary(exp+1023);
            
            double input = d*Math.pow(2, -exp)%1;
            
            String fractBinary= getFractBinary(input); 
            
            frac64=fractBinary;
            frac32= frac64.substring(0,23); 
            
        }
        else if (d>=1){
            int wholePart= (int)d;
            double decimalPart = d % wholePart;

            String wholeBinary= getWholeBinary(wholePart); //first part of decimal
            
            String fractBinary= getFractBinary(decimalPart); //second part of decimal

            String bothBinary = wholeBinary+fractBinary;
            int dec = wholeBinary.length();
            int exp= getNormExp(bothBinary, dec);

            exp32 = getWholeBinary(exp+127).substring(3,11); //only 8 
            exp64= getWholeBinary(exp+1023);
            
            frac64=getFract(exp, dec, bothBinary);
            frac32= getFract(exp, dec, bothBinary).substring(0,23); 
            
        }
           
        System.out.println("Input: " + d);
        System.out.println(s+" "+ exp32+ " "+frac32);
        System.out.println(s+" "+ exp64+ " "+frac64);
        
        
    }
    
    public static String getWholeBinary(int wholeNum){
        StringBuilder str = new StringBuilder();
        
        while(wholeNum!=0){
            str.append(wholeNum%2);
            wholeNum=wholeNum/2;
        }
        
        while(str.length()<11){
            str.append('0');
        }
        
        return str.reverse().toString();
    } 
    
    public static String getFractBinary(double fracNum){ 
        StringBuilder str = new StringBuilder();
        fracNum= fracNum*2;
        while( str.length()<52){
            int whole = (int)fracNum;
            double decimalPart;
            
            if(whole==0){
                decimalPart= fracNum;
            }
            else{
                decimalPart = fracNum % whole;
            }
            
            fracNum=decimalPart*2.0;
            str.append(whole);
        }
        
        return str.toString();
       
    }
    
    public static int getNormExp(String bothBinary, int dec){
        int counter=0;
        dec--;
        for(int i = 0; i<bothBinary.length(); i++){
            if(bothBinary.charAt(i)=='1'){
                counter= i;
                break;
            }
            else{
                counter= i;
            }
        }
        int exp = dec-counter;
        
        return exp;
    }
    
    public static String getFract(int exp, int dec, String both){
        StringBuilder str = new StringBuilder();
        
        for(int i=dec-exp; i<both.length();i++){
            str.append(both.charAt(i));
        }
        while(str.length()<53){
            str.append('0');
        }
        return str.toString().substring(0,52);
    }
    
    public static int getDenormExp(double d){
        int i= 1; 
        double res=d*Math.pow(2, i);
        while (res <1){
            i++;
            res= d*Math.pow(2, i);
        }
        return -i;
    }
    

    
    
}
