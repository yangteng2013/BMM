package app.banking.bankmuscat.merchant.util.constant;

/**
 * Created by amit.randhawa on 8/22/2016.
 */
public enum ErrorAndPopupCodes {

    //Error Messages
        Network_Error("Network conncetion lost. Please check wifi or data connectivity."),
        No_Response("Something went wrong. Please try again."),
         Delete_Social_Handle("You are trying to delink your social Account from Wallet")   ,
        Invalid_Pin("PIN cannot be sequential / repetitive. Set another PIN"),

    //Pop Up Messages

    Error_loading_wallet("Something went wrong while loading the wallet. Please swipe down to load again."),
    Invalid_Mobile_Number("Invalid mobile number. Please enter your 8 digit mobile number."),
    Invalid_Name("Invalid full name."),
    Invalid_Nid("Invalid mobile NID."),
    Invalid_Dob("Invalid Date of Birth."),
    Invalid_Email("Invalid Email address."),
    Invalid_PinInput("Invalid PIN. Please enter your wallet PIN."),
    Create_New_Chipin_Group("Create a new group to do chip in "),
    SVA_Created_Successfully("Stored Value Account Created."),
    Enable_Default("Do you want to make this card as default?"),

    //Toast Messages
    Valid_Merchant_Id("Please enter the Merchant Id"),
    Valid_Amount("Please enter the amount");




    private String tag;
    private ErrorAndPopupCodes(String tag)
    {
        this.tag = tag;
    }
    public String getTag() {
        return tag;
    }


}
