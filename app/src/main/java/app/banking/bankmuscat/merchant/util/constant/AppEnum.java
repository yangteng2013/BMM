package app.banking.bankmuscat.merchant.util.constant;


public class AppEnum {



    public enum OtpMode{
        noType(-1),
        registerUser(0),
        forgetPin(1),
        ChangeNumber(2),
        ChangeEmail(3);


        private final int _status;

        private OtpMode(int status){
            _status = status;
        }

        public static int ToInt(OtpMode value){
            switch( value){

                case noType:return -1;
                case registerUser:return 0;
                case forgetPin: return 1;
                case ChangeNumber:return 2;
                case ChangeEmail:return 3;

            }
            return -1;
        }

        public static OtpMode FromInt(int value){
            switch( value){
                case -1: return noType;
                case 0: return registerUser;
                case 1: return forgetPin;
                case 2: return ChangeNumber;
                case 3: return ChangeEmail;

            }
            return noType;
        }
    }


}
