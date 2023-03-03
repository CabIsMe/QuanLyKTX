package root.quanlyktx.model;



import java.util.Random;


public class OTPCode {
    private static final int length=6;

    public static char[] generateOTP() {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for(int i = 0; i< 6 ; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }
}

